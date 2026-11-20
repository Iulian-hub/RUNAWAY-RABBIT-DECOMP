# Physics, Collision and Damage Audit

> Scope: static-source audit of the Gusto-modified Blitz3D project. This document records behavior directly visible in the checked-in source. It does not claim frame-by-frame equivalence with the original 2006 executable.

## 1. Executive summary

The game uses a custom 2D gameplay model rendered through Blitz3D entities in a 3D scene. Actor positions and movement are stored as X/Y values; rotation is represented as an angle; collision dimensions are axis-aligned half-extents before rotation. Collision tests are implemented in project code rather than delegated to a physics engine.

The principal routines are:

- `fastcollisionxy()`: strict rectangular overlap test.
- `anglecollisionxy()`: rotated-rectangle approximation using subdivision into smaller boxes.
- `doactorcollisions()`: actor-vs-actor contact damage.
- `doweaponcollision()`: projectile-vs-actor and projectile-vs-projectile interaction.
- `doactordeathcheck()`, `doactordeath()`, `actordeath()`: health depletion, death delay, faction accounting, score and drops.
- `doplayerhits()`: player shield/life/death/re-entry behavior.

## 2. Coordinate and movement model

### 2.1 Actor movement

`doactormovement()` transforms the local forward vector (0,1,0) using the actor entity rotation, multiplies it by the actor's current speed (actor Field46), and translates the entity by velocity × frame factor (global `fl\Field1`). It then reads entity X/Y back into actor Fields48/49.

If speed is not positive, the routine still updates entity rotation but does not translate the actor.

### 2.2 Player movement

`doplayermovement()` is a separate horizontal-follow controller. It compares the player entity X coordinate against the stored player X coordinate, derives a target horizontal speed, clamps normal movement to ±5, and integrates using `fl\Field1`. During the special movement state (Field33 > 0), the speed clamp is wider (±50) and the controller uses a stronger response. The player X coordinate is clamped to [-400, 400] during ordinary play.

This is not a conventional acceleration/velocity simulation; it is a damped follow controller.

### 2.3 Frame factor

Many motion, timers, animation counters and damage effects are multiplied by `fl\Field1`. Reimplementation should preserve the original time-step semantics first, then consider a fixed-step Godot physics loop with a compatibility accumulator. Replacing every timer with arbitrary delta-time calculations risks changing firing cadence and collision timing.

## 3. Collision primitives

### 3.1 Fast rectangle collision

`fastcollisionxy(x1,y1,hx1,hy1,x2,y2,hx2,hy2)` calculates the sum of each pair of half-extents and returns true only when:

- x1 is strictly greater than x2 - (hx1 + hx2),
- x1 is strictly less than x2 + (hx1 + hx2),
- and the same strict inequalities hold for Y.

Consequences:
- Edge-touching is not a collision because comparisons are strict.
- The inputs behave as half-width/half-height values, not full dimensions.
- The routine is integer-coordinate based; callers cast positions and extents to integers.

### 3.2 Rotated rectangle collision

`anglecollisionxy()` first estimates a coarse overlap using the larger dimension of each rectangle. It then subdivides elongated rectangles into up to 100 smaller square-ish samples, computes sample centers using transformed normals and rotation angles, and tests every sample pair with `fastcollisionxy()`.

This is an approximation, not an exact separating-axis test. Its precision depends on aspect ratio, integer casts, and the 100-sample cap. The routine uses shared global arrays `e1box` and `e2box` and a shared math entity, so it is not re-entrant.

### 3.3 Collision geometry source

Actor collision extents are stored in actor Fields53/54 and are initialized from actor-type definition Fields47/48 in `makeactor()`. Actor rotation is Field50. Projectile collision extents are stored in projectile Fields33/34 and projectile rotation in Field18; the collision call converts projectile angle with `-Field18 + 90`.

## 4. Actor-to-actor contact

`doactorcollisions()` only considers collisions when the source actor age (Field77) is greater than 180. It scans actor slots 1..20 and filters the other actor by:

- active slot (Field41 = 1),
- age > 180,
- not suppressed (Field19 = 0),
- enabled faction-pair entry in `factioncollisions(sourceFaction, otherFaction)`.

On overlap:
- source actor health is reduced by half of the other actor's contact-damage field (Field31 / 2);
- the source actor's last-attacker/attribution field (Field79) is set to the other actor slot;
- the other actor health is reduced by half of the source actor's Field31;
- both actors receive a hit-flash value in Field55 when below 100.

The faction matrix is explicitly initialized in `setcollisions()` for the pairs (2,1), (5,1), (2,3), and (6,1). The matrix is directional as written; do not assume symmetry without inspecting every assignment.

## 5. Projectile collision and damage

### 5.1 Faction filtering

`doweaponcollision(projectile_slot)` scans active actors and checks the projectile's Field64[1..10] faction mask against the target actor's faction (Field30). It also checks projectile-vs-projectile faction compatibility using projectile Field65 and the same mask.

### 5.2 Projectile versus actor

The collision test uses the projectile's current position, dimensions, and transformed angle against the actor's rotated rectangle.

On a hit, the actor's Field79 is set to the projectile owner/source field (projectile Field36). Damage is based on projectile Field21, but the formula varies by projectile flags:

- Ordinary non-persistent projectile (Field35 = 0): subtracts Field21 directly.
- Persistent/duration-based projectile branch: damage may be divided by a size/time expression involving actor dimensions, projectile dimensions, frame factor, and projectile Field19.
- Another branch applies damage as Field21 × frame factor when projectile Field25 is set.

The projectile can spawn impact particles or explosions, and ordinary shots are disabled after impact. Some projectile types create a reflected/secondary shot using actor Field16.

### 5.3 Projectile versus projectile

A projectile can collide with other active projectiles when its faction mask accepts the other projectile's Field65. Damage is applied to the other projectile's Field67 health. Both projectiles can be disabled when their respective hit/health state reaches zero. Impact effects are spawned using the same particle/explosion helpers.

The complete branch logic is long and type-dependent; the Godot implementation should use an explicit projectile interaction policy rather than one generic collision callback.

## 6. Damage, hit flash and actor death

### 6.1 Actor health

Actor current health is Field59; maximum health is Field58. `updateactor()` applies regeneration when Field60 is positive:

`health += (max_health / 6000) × regeneration_factor × frame_factor`

Health is clamped to maximum. `doactordeathcheck()` triggers the death timer when health is <= 0 and the actor is not already in its death sequence.

### 6.2 Hit flash

Field55 is used as a temporary hit-flash intensity. `updateactor()` reduces it over time using Field8 as the fade duration, then interpolates entity RGB from the target tint (Fields9..11) toward the base tint (Fields12..14). Child entities and turret entities receive the same color update.

### 6.3 Death sequence

`doactordeath()` advances Field34 while it is positive. During this interval, random explosions may be emitted. Once Field34 exceeds Field35:

1. Actor slot is marked inactive (Field41 = 0).
2. A linked child actor (Field2), if present, is also deactivated.
3. A final explosion is created.
4. Configured death weapons in Fields18/17[1..10] may be emitted.
5. Faction live/dead counters are updated.
6. Turrets are hidden in the main game view.
7. `actordeath()` handles enemy count, score, combo attribution and bonus drops.

### 6.4 Enemy score and drops

`actordeath()` decrements global enemy count and per-type enemy count, reads the base score from the enemy definition, and applies a ×10 score multiplier for the special enemy flag. Score attribution uses actor Field79 to identify player 1 or player 2. It updates player kill count and score, creates floating score text, and manages combo state.

Enemy cash drops and ordinary bonus drops depend on enemy drop fields (Fields12..14) and kill attribution. This ties projectile attribution to progression and reward logic; the attribution field must not be omitted in the new architecture.

## 7. Player damage, shields, lives and re-entry

`doplayerhits()` manages a timed player-hit/invulnerability state. It changes actor health to a very high temporary value during the hit window, animates the player, then restores health. When health falls below 1:

- If the player has shields/lives protection (player Field9 > 0), one protection unit is consumed, camera shake and white flash are triggered, a shield sound is played, and a 120-unit recovery timer begins.
- Otherwise, a death sequence begins: player actor is disabled/suppressed, weapons and turrets are hidden, explosions/dust are created, and the death sound is played.
- After the player death animation reaches its threshold, a remaining life is consumed and `addplayer()` recreates the player actor at the start position. If no lives remain, the player enters the inactive/game-over path.

The player actor is temporarily assigned faction 10 during the death animation. This is a deliberate collision-state transition, not just a visual effect.

## 8. Collision matrix and actor lifecycle risks

- Actor slots: 20 (1..20).
- Projectile slots: 100 (1..100).
- Actor and projectile pools are manually managed; there is no broad-phase physics engine.
- Actor collision is gated by age > 180, so newly spawned entities do not immediately collide.
- Actor Field19 suppresses normal updates and hides the actor/turrets.
- Forced actor allocation can reuse slot 1 when the pool is full (documented in gameplay audit).
- Integer conversion in collision routines can create boundary behavior different from floating-point Godot collision shapes.
- Rotated collision is approximate and can be expensive: each collision may perform nested loops over as many as 100 × 100 sample cells.
- Shared scratch arrays/entity state make collision routines order-dependent and non-thread-safe.

## 9. Godot 4 reconstruction recommendations

1. Keep gameplay coordinates in a dedicated 2D world coordinate system; render through Node2D/CanvasItem rather than a 3D camera unless a deliberate visual effect requires 3D.
2. Represent actors with typed data: position, heading, velocity/speed, half-extents, faction, health, contact damage, attribution, invulnerability and death state.
3. Implement a compatibility collision layer first:
   - strict AABB overlap matching `fastcollisionxy()`;
   - a testable rotated-rectangle approximation matching `anglecollisionxy()`;
   - later compare against SAT/Area2D only after parity tests.
4. Separate collision detection from damage resolution. Return collision events containing attacker, target, damage, faction and source projectile.
5. Preserve per-projectile damage modes explicitly (instant, duration-scaled, frame-scaled, persistent beam, projectile interception).
6. Use explicit actor lifecycle states (ACTIVE, HIT, DYING, INACTIVE, RESPAWNING) rather than overloading numeric flags.
7. Make collision masks symmetric only if verified against the original behavior; the source matrix is directional.
8. Add deterministic test cases for edge-touching, rotated rectangles, high aspect ratios, collision age gating, player shield loss, death attribution and projectile interception.

## 10. Open questions / verification backlog

- Exact semantic names for all actor type fields remain partly provisional.
- The full `doweaponcollision()` behavior should be indexed by all 40 projectile definitions before implementing every projectile family.
- Player hit timer/animation fields need a dedicated typed mapping.
- Collision matrix may be modified outside `setcollisions()`; audit all writes before finalizing faction rules.
- Static source inspection cannot establish exact visual collision feel or timing parity; compare the original executable in controlled scenarios.
