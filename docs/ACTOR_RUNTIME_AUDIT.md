# Actor runtime lifecycle audit

**Scope:** actor allocation, archetype instantiation, parent/child composition, player/enemy/boss actor setup, per-frame update order, combat, death and cleanup.

**Status:** static analysis of the checked-in Gusto-modified Blitz3D source. This is not a dynamic trace of the original Mighty Rodent executable. No Blitz3D source files were modified.

## Source revisions

| Source | Blob SHA |
|---|---|
| `Types/actortype.bb` | source declaration inspected |
| `Functions/makeactor.bb` | `d6fc51374b11f96b802c83efb3fbab13f940aaa9` |
| `Functions/doactors.bb` | `7195ad99613df63074b4430798627f0e96481316` |
| `Functions/doactoranimations.bb` | `9e25ab2489eb5b7cf494e8f4336f9688dd64405f` |
| `Functions/doactorcollisions.bb` | `ab180de7d750db1db20128b7f47567a194970847` |
| `Functions/doactordeathcheck.bb` | `345071e63c0eb27fd8a85864771f7541b6c5aa14` |
| `Functions/doactordeath.bb` | `fa6bb393ec46a9a2dc7d04fd8afac18c32afc4d0` |
| `Functions/doactormovement.bb` | `9ade549746be8f0376fdcd3c0be60031e57cb34a` |
| `Functions/doactorweapons.bb` | `4365948dbde907f142a06c00125ec259e2f46984` |
| `Functions/updateactor.bb` | `f3ef60d85846db9388cc2a1dea4c3b0509b72d06` |
| `Functions/makeenemy.bb` | `bb2a22d5bcd52bdc6f18575988b1639dce4bfc4e` |
| `Functions/makeboss.bb` | `bdf40e1c9e5b3a336a7b0bb0451e08c3cdff041d` |
| `Functions/doenemiesend.bb` | `03c349a271d115377da9ffab1ebd5c171be728c8` |
| `Functions/dobosses.bb` | `f6f3369d04a28b3a691b89ce88f9f7b7746bbb9b` |

For the field-by-field dictionary, see [TYPE_FIELD_MAP.md](TYPE_FIELD_MAP.md). For the full actor archetype and mount values, see [ENTITY_DEFINITIONS_AUDIT.md](ENTITY_DEFINITIONS_AUDIT.md) and [entity_definitions.extracted.json](data/entity_definitions.extracted.json).

## 1. Actor pool and allocation

- Runtime actor capacity is 20 slots, indexed 1–20.
- `makeactor()` scans the pool for an inactive slot (`Field41 = 0`).
- If the pool is full, normal allocation returns 0.
- A forced-allocation argument allows fallback to slot 1, potentially overwriting a live actor. This is a destructive fallback, not a safe eviction policy.
- Enemy runtime records use the same slot index as their actor record: `makeenemy()` receives the actor slot returned by `makeactor()` and initializes `enemies(slot)`.
- Actor slot allocation and enemy slot allocation are therefore coupled for enemy actors.

Godot should replace the fallback with an explicit policy: fail spawn, queue spawn, or use a separately managed pool. Never silently overwrite an active actor.

## 2. Archetype instantiation and component hierarchy

`makeactor(x, y, heading, archetype_id, force)` performs these operations:

1. Allocates an actor slot and marks it active.
2. Initializes root/child relationship state and visual/animation state.
3. If the archetype declares a child archetype, recursively creates that child actor.
4. Stores the child slot in the parent actor and the parent slot in the child actor.
5. Copies faction, graphic/animation, dimensions, health, movement and color configuration.
6. Copies death-emitted projectile kinds/angles into per-actor arrays.
7. Copies and resolves up to ten weapon mounts into runtime mount arrays.
8. Creates/positions the actor body and turret entities, selects textures and applies scale.
9. Initializes actor age.

The relationship is directional:
- Parent actor `Field2` points to its child slot.
- Child actor `Field3` points to its parent slot.
- Root actors have `Field4 = 1`; child actors are created with `Field4 = 0`.

When the parent death sequence completes, `doactordeath()` also deactivates the child slot. This is a lightweight component hierarchy implemented through actor-pool indices, not a general-purpose scene tree.

## 3. Player, enemy and boss actor creation

### Player

`loadplayers()` pre-creates two player actor slots and their crosshair/shield visuals. These actors begin hidden/suppressed. `addplayer()` activates the selected player, initializes lives, shield charges, bombs, control mode and position, then configures the player's weapon mounts dynamically.

Player actors are not created through `makeenemy()`; their runtime player records refer to the pre-created actor slots.

### Enemy

`makeenemy(enemy_id, x, y, heading, variant, force)` creates archetype `enemy_id + 2`, then initializes the matching `enemy(slot)` AI record.

Additional behavior:
- Randomized enemy AI target coordinates and heading are initialized.
- Enemy state scratch fields 1–100 are cleared.
- Variant flags can change decal/color treatment.
- In two-player mode, actor maximum health is doubled.
- Enemy weapon mount Field76 multipliers are scaled by the selected profile skill or special mode:
  - skill selector 1: 1.75
  - skill selector 2: 1.45
  - skill selector 3: 1.15
  - skill selector 4: 0.85
  - special mode 1: 1.3
  - special mode 2: 2.0

The source multiplies the mount interval multiplier, so these values affect weapon cadence through the effective interval; they are not direct health or movement multipliers.

### Boss

`makeboss()` creates a boss controller and then creates each enabled boss component as an ordinary actor using the component archetype ID. Boss component actors have their actor render-order flag cleared and are positioned relative to the boss controller.

In two-player mode, boss component health is doubled. `dobosses()` aggregates health from active component actors and only marks the boss controller defeated after no enabled component remains active.

## 4. Per-frame actor update order

`doactors()` iterates slots 1–20. For each active actor with update suppression disabled, the order is:

1. `doactoranimations()`
2. `doactorweapons()`
3. `doactorcollisions()`
4. `doactordeathcheck()`
5. `doactormovement()`
6. `doactorai()` — delegates to player or enemy AI
7. `updateactor()`
8. `doactorendai()` — delegates to player or enemy end-of-update logic, if still active

This ordering is behaviorally significant. For example, weapons are processed before actor collision/death checks and before the movement phase. The reconstruction should initially preserve this sequence until parity tests justify changing it.

If actor Field19 is nonzero, the actor body and all ten turret entities are hidden and moved far outside the visible world. If Field41 is zero, the same cleanup/hide behavior is applied.

## 5. Weapon mounts and actor firing

Each actor has ten possible mount slots. The archetype mount table is copied into runtime actor arrays during `makeactor()`.

For each active mount, `doactorweapons()`:

- Reads the projectile kind from actor Field61[slot].
- Gets the base interval from the projectile kind's Field70.
- Computes effective interval as base interval × actor Field76[slot].
- Optionally rotates the turret toward a target using actor Field67, Field63 and Field69.
- Accumulates elapsed firing time in actor Field75[slot].
- Fires only when actor-level firing is enabled or the mount-specific permission is enabled.
- Calculates the projectile origin from actor position, mount offsets, mount angle and optional turret scale.
- Calls `makeweaponshot()` with the actor slot and mount slot for source attribution and muzzle-flash positioning.

The player setup path writes these mount arrays dynamically and should not be confused with the static enemy/boss archetype mount definitions.

## 6. Collision and damage

Actor-vs-actor contact collision:

- Runs only after the actor age exceeds 180 time units.
- Iterates the 20 actor slots.
- Requires the other actor to be active, old enough and not suppressed.
- Checks the directional faction-collision matrix.
- Uses rotated rectangle collision.
- Applies half of each actor's contact-damage value to the other actor.
- Writes the opposing actor slot into Field79 for damage attribution.
- Triggers hit-flash state.

Projectile collision uses the same actor health and attribution model; see [PROJECTILE_RUNTIME_AUDIT.md](PROJECTILE_RUNTIME_AUDIT.md).

## 7. Death and cleanup sequence

`doactordeathcheck()` first advances any active death sequence. For actors with normal death handling enabled:

1. Low-health smoke particles may be emitted while current health is below 35% of maximum.
2. When current health reaches zero and no death sequence is active, the death timer is initialized.
3. `doactordeath()` advances the timer, creates random explosions and waits for the configured death duration.
4. At completion, the actor and its child actor are deactivated.
5. Configured death-emitted projectiles are spawned from the actor's Field17/Field18 arrays.
6. Faction counters are updated.
7. `actordeath()` applies reward, kill-count, combo and cash-bonus logic using Field79 attribution.

This sequence is not equivalent to immediately deleting an actor at zero health. The death animation/effects and reward attribution are gameplay state.

## 8. Off-screen enemy recycling

For regular enemy archetypes, `doenemiesend()` recycles enemies that travel beyond approximately +600 or -600 on the X axis.

- The replacement is spawned at +500 or -500, with a randomized Y coordinate based on the enemy spawn-range definition.
- Health and maximum health are copied from the outgoing actor.
- Most per-mount firing multipliers are copied to the replacement.
- The outgoing actor and its child are deactivated.
- Current-enemy counters are decremented before the replacement contributes to active counts through its own creation path.

This is a respawn/recycling mechanism, not a teleport of the same actor instance.

## 9. Unresolved actor fields and risks

The following fields remain unresolved or only partially understood: Field0, Field27–Field29, Field33, Field38–Field40, Field56–Field57, Field78 and Field80. Some are copied from actor archetype definitions but their complete downstream read/write paths have not been proven.

Other risks:
- Forced pool allocation can overwrite slot 1.
- Parent/child actor indices can become stale if cleanup paths diverge.
- Boss components share the general actor pool with enemies and player actors.
- Actor update order affects firing, collision, movement and death timing.
- Faction collision checks are directional; do not reduce them to a symmetric relation without verifying the matrix.
- Field79 attribution must be preserved through every damage path.

## 10. Godot implementation boundary

Recommended runtime model:

- `ActorDefinition` Resource: graphics, animation, collision, health, faction, movement, contact damage and mount definitions.
- `ActorInstance`: runtime transform, velocity, health, age, animation state, death state, parent/child references and attribution.
- `ActorComponent` or explicit child actor reference: boss/actor components with independent health.
- `ActorSystem`: allocation, update ordering and cleanup.
- `ActorWeaponController`: mount aiming, cadence and projectile creation.
- `DamageEvent`: source actor, target actor, amount, damage type and attribution.
- `ActorPool`: bounded allocation with explicit exhaustion behavior.

Keep legacy actor fields available in a mapping layer until all uncertain fields and original-game behavior are resolved.
