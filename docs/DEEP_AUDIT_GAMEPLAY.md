# Deep gameplay audit – Runaway Robbit / Mighty Rodent reference

**Repository:** `Iulian-hub/RUNAWAY-RABBIT-DECOMP`  
**Branch:** `main`  
**Audit date:** 2026-11-20  
**Scope:** deeper static trace of actor lifecycle, player control, enemy spawning/AI, projectile lifecycle, bosses and level-event data.  
**Method:** cross-reference type declarations with initialization, reads/writes and call sites. This is not a dynamic execution trace; ambiguous fields remain explicitly unresolved.

## 1. Executive findings

The game is a data-driven arcade shooter implemented using Blitz3D's 3D entity system as a 2D presentation layer. Its main gameplay concepts are already separated into modules, but runtime state is tightly coupled through fixed-size arrays, shared global variables and numeric field indices.

The most important architectural discovery is that enemy behavior is not implemented in `doenemies()`. That function is an empty stub. The actor update pipeline calls `doactorendai()` after movement and rendering; this calls `doenemiesend()`, which contains the substantive per-enemy behavior. Separately, `controlenemies()` handles event-driven wave spawning.

The rebuild should preserve the observed update order and gameplay rules first, but replace the fixed pools and numeric field access with typed entities/resources and explicit lifecycle states.

## 2. Runtime update order

### 2.1 Main loop

`gameloop()` executes the following gameplay updates in order:

1. Backgrounds and mouse.
2. HUD and demo crosshair.
3. Actors.
4. Bonuses and GUI.
5. White flash, blackout, animation loops and camera.
6. Shot flashes and weapon shots.
7. Sound-volume limiting and explosions.
8. Level progression.
9. Dynamic enemy spawning via `controlenemies()`.
10. Background enemies, bosses and demo timers.
11. Input-force calculation, menu update and frame timing.
12. `RenderWorld(1.0)` and `Flip(0)`.

This order is part of the behavior. In particular, actors update before weapon shots, while level progression occurs before dynamic wave spawning and boss updates occur after both. A port should initially preserve these phases rather than reorganize them for stylistic reasons.

### 2.2 Actor update pipeline

`doactors()` scans 20 actor slots. An active actor (`Field41 = 1`) which is not suppressed (`Field19 = 0`) runs:

1. `doactoranimations()`
2. `doactorweapons()`
3. `doactorcollisions()`
4. `doactordeathcheck()`
5. `doactormovement()`
6. `doactorai()`
7. `updateactor()`
8. `doactorendai()`, if the actor remains active

The sequence has consequences: weapons and collisions are processed before movement; the actor's end-AI can change or deactivate it after the visual update. Inactive or suppressed actors are hidden and moved to very large sentinel coordinates.

**Rebuild implication:** use an explicit actor state (active, disabled, dying, despawning) and scene visibility/lifecycle. Do not reproduce the sentinel-coordinate hiding trick.

## 3. Actor composition and lifecycle

### 3.1 Creation

`makeactor(x, y, angle, actor_type, force)`:

- Searches the fixed pool of 20 actor slots.
- Initializes an actor from the selected `actortypes` definition.
- Copies visual, animation, faction, collision, health, movement and weapon-mount configuration.
- May recursively create a child actor when the actor definition references another actor type.
- Stores parent/child relationships as actor-array indices.
- Creates/positions the body entity and up to 10 turret entities.
- Sets an actor lifetime/age field to the global `basetimealive`.

The parent/child relationship is represented by `actortype.Field2` (child actor index) and `Field3` (parent actor index), based on assignments in `makeactor()` and checks in `doenemiesend()` / `actordeath()`.

### 3.2 Pool allocation risk

The allocator remembers the last free slot encountered. If no slot is free and `force = 1`, it falls back to slot 1. This can overwrite an already active actor. The caller must therefore be understood before reproducing this behavior; it is not a safe general-purpose allocation policy.

**Severity: High.** The new implementation should use dynamic scene instantiation or a managed pool whose exhaustion policy is explicit. Never silently replace a live player, boss part or enemy.

### 3.3 Actor state confirmed from call sites

| Legacy field | Supported interpretation | Evidence |
|---|---|---|
| `Field2` | Child actor slot | Assigned during recursive actor creation |
| `Field3` | Parent actor slot | Written to child and checked during cleanup |
| `Field19` | Actor update suppression / inactive presentation flag | Gates actor updates; player code sets it |
| `Field30` | Faction ID | Used in faction collision filtering and counters |
| `Field32` | Weapon firing enabled | Read by actor weapon update |
| `Field41` | Actor slot active | Allocation and main actor iteration |
| `Field42` | Body entity handle | Position, texture, visibility and rotation |
| `Field43` | Actor archetype/type ID | Dispatches enemy-specific behavior |
| `Field44`, `Field45` | X/Y velocity components | Movement and projectile attachment |
| `Field46` | Current movement speed | Converted to velocity using heading |
| `Field47` | Base/target movement speed | Copied from definition and adjusted by AI |
| `Field48`, `Field49` | World X/Y position | Movement, collision and targeting |
| `Field50` | Actor heading/rotation | Movement direction and sprite orientation |
| `Field53`, `Field54` | Collision dimensions/half-extents | Rotated collision and effect sizing |
| `Field58` | Maximum/starting health | Initialized from actor definition |
| `Field59` | Current health | Damage, regeneration and boss health |
| `Field61[1..10]` | Weapon kind in each mount | Copied from actor definition; passed to shot creation |
| `Field65[1..10]` | Current turret angle | Aiming and turret rotation |
| `Field70[1..10]` | Turret entity handles | Rendering, hiding and positioning |
| `Field71/72[1..10]` | Turret mount X/Y offsets | Weapon muzzle calculation |
| `Field74[1..10]` | Effective shot interval | Loaded from weapon kind |
| `Field75[1..10]` | Fire timer/elapsed interval | Accumulated and reset when firing |
| `Field76[1..10]` | Per-mount fire-rate multiplier | Multiplies effective shot interval |
| `Field77` | Actor age/lifetime timer | Incremented by `updateactor()` |
| `Field79` | Last damaging actor / kill attribution | Used to award score |

Fields not listed remain unresolved or provisional. In particular, do not assume every field with a similar numeric type has an obvious meaning.

## 4. Player control and damage

### 4.1 Player-to-actor relationship

There are two player records. Each player points to an actor slot and has a crosshair entity. `doplayers()` resolves the actor slot to player 1 or player 2, then updates the crosshair, movement, turret position, weapons, hit/death state and player presentation.

### 4.2 Movement

`doplayermovement()` moves the hamster horizontally toward the crosshair's X coordinate. It uses a proportional correction, a dead zone of approximately 50 coordinate units, a normal speed cap of 5 units per frame-time unit and a special/death-related movement branch with a larger cap (50). The position is clamped to approximately -400…400.

The exact behavior depends on `fl.Field1`, so the value's time unit and frame normalization must be preserved when porting.

### 4.3 Turret aiming and fire input

`doplayerweapons()` calculates the turret angle from the turret mount to the crosshair and clamps the firing arc. The observed limits are approximately 80°–181° on one side and 180°–280° on the other. The function supports keyboard/mouse and joystick control modes, primary fire, secondary bomb input and a global auto-fire toggle added in the modified build.

`doactorweapons()` processes up to 10 weapon mounts per actor. It:
- reads the weapon-kind definition;
- calculates the muzzle position from mount offsets and actor/turret rotation;
- supports automatic target acquisition for configured mounts;
- advances a per-mount fire timer;
- compares the timer against effective firing interval × mount multiplier;
- creates a projectile through `makeweaponshot()`.

### 4.4 Damage, shields and lives

`doplayerhits()` manages player hit animation/state, shield protection, lives and respawn. When actor health reaches zero, the player either consumes a shield charge and receives a temporary protection interval, or enters a death sequence. The death sequence hides turret entities, applies effects and eventually respawns the player if lives remain.

For the rebuild, separate:
- health and damage;
- shield charges and temporary invulnerability;
- remaining lives and respawn;
- hit feedback and visual animation;
- score attribution.

Do not keep all of these in one player update function.

## 5. Enemy spawning and AI

### 5.1 Dynamic spawning

`controlenemies()` reads the current level event and checks:
- global concurrent enemy limit;
- per-enemy-type concurrent limit;
- spawn interval / random roll;
- allowed spawn sides;
- vertical spawn range.

It then chooses a side and calls `makeenemy()`. The enemy definition supplies side permissions and default spawn bounds. The level event can override or select the active enemy types and their limits.

### 5.2 Enemy construction

`makeenemy(enemy_type, x, y, angle, spawn_variant, flag)` calls `makeactor()`, initializes a parallel `enemytype` record, seeds random target coordinates and movement data, applies player-count scaling and difficulty/special-mode multipliers, then increments enemy counters.

Observed difficulty multipliers for the enemy's weapon/movement-related per-mount values are:
- profile difficulty 1: 1.75
- difficulty 2: 1.45
- difficulty 3: 1.15
- difficulty 4: 0.85
- special mode 2: 2.0
- other special mode: 1.3

These multipliers are applied to actor `Field76[1..10]`; the exact player-facing interpretation should be validated in the running game before translating the values into labels such as “enemy fire rate”.

### 5.3 AI dispatch

`doactorai()` calls `doplayers()` and `doenemies()`. The latter is an empty function in this source. The substantive enemy-specific behavior is in `doenemiesend()`, called at the end of the actor pipeline.

`doenemiesend()` dispatches on actor archetype ID (`actortype.Field43`) and uses the associated `enemytype` record's scratch array for per-enemy state. It contains distinct behavior branches for multiple enemy IDs, including movement across the playfield, boundary wrap/re-entry, directional turret aiming, vertical bouncing/landing and timed/randomized movement. This is effectively a collection of hand-coded state machines.

**Rebuild implication:** define named enemy archetypes and explicit state enums/state data. Avoid a single giant switch over numeric archetype IDs and avoid a generic scratch array like `Field5[100]`.

### 5.4 Enemy type metadata

The following mappings are supported by `controlenemies()` and `actordeath()`:

| Type | Field | Supported meaning |
|---|---|---|
| `enemytypetype` | `Field0` | Default minimum spawn Y |
| `enemytypetype` | `Field1` | Default maximum spawn Y |
| `enemytypetype` | `Field2[1..3]` | Spawn-side allowed flags |
| `enemytypetype` | `Field3` | Unresolved metadata |
| `enemytypetype` | `enemytype.Field3` | Base score/reward for killing this enemy |
| `enemytype` | `Field0` | Spawn-range minimum Y override |
| `enemytype` | `Field1` | Spawn-range maximum Y override |
| `enemytype` | `Field2[1..3]` | Spawn-side allowed flags |
| `enemytype` | `Field3` | Base kill score/reward |
| `enemytype` | `Field4` | Initial movement-speed value copied to enemy runtime state |
| `enemytype` | `Field5[1..100]` | Per-enemy AI scratch/state values |
| `enemytype` | `Field6` | Spawn variant / special flag copied from `makeenemy()` |

The type name `enemytypetype` is a definition/configuration record; `enemytype` is the per-instance runtime record.

## 6. Projectile lifecycle and collision

### 6.1 Definition versus live projectile

`weaponshotkinds` stores projectile definitions. `makeweaponshot()` copies configuration from a weapon-shot kind into a live `weaponshots` slot, creates its sprite/particle, records its owner actor and weapon mount, initializes position/velocity/angle/damage/effects/target filters, and triggers muzzle flash and sound.

`doweaponshots()` iterates up to 100 live projectile slots. It advances position using `fl.Field1`, updates lifetime and visual state, handles trails, homing/target acquisition, gravity/acceleration-like effects, attachment/return behavior and finally positions/rotates the projectile sprite.

`doweaponcollision()` checks a projectile against active actors and other projectiles. It uses faction masks and rotated rectangle collision. Depending on projectile configuration it can apply damage immediately, persist for repeated damage, interact with other projectiles or apply special collision behavior.

### 6.2 Projectile pool risk

`makeweaponshot()` scans 100 slots for an inactive projectile. If all are occupied, it falls back to slot 1. This silently overwrites a potentially live projectile.

**Severity: High.** The new implementation must define a safe overflow policy (reject spawn, expand capacity, or evict only a specifically eligible cosmetic projectile). Gameplay-critical shots must not disappear unpredictably.

### 6.3 Collision architecture

Actor-to-actor collision uses faction filtering and rotated rectangle checks. Projectile-to-actor collision also uses rotated rectangle checks. Some projectile interactions iterate the active projectile pool and compare ownership/faction masks.

The new project should centralize damage resolution and collision filtering, but preserve the legacy collision geometry initially. Replacing rotated rectangle checks with Godot physics shapes may change hitboxes; compare visually and behaviorally before accepting that change.

### 6.4 Timing caution

Many projectile fields are advanced using `fl.Field1`; some damage paths scale damage by frame delta while others apply per-hit damage. Do not normalize all legacy damage values into “damage per second” without inspecting each projectile definition and collision branch.

## 7. Level-event data model

`dolevels()` advances the level timer, waits for any boss prerequisite, activates the next event, changes music, displays event text/menu changes, spawns scripted enemies/bosses/background enemies and handles completion.

`controlenemies()` separately creates ongoing waves subject to the current event's concurrency and spawn-interval limits.

Field meanings confirmed by call sites:

| `levelevent` field | Supported meaning |
|---|---|
| `Field0` | Event trigger time |
| `Field1` | Bosses-dead prerequisite before event activation |
| `Field2` | Background identifier used when preparing the level |
| `Field3` | Maximum concurrent enemy count for dynamic spawning |
| `Field4` | Music track identifier |
| `Field5[1..5]` | Scripted enemy type IDs |
| `Field6[1..5]` | Scripted enemy X positions |
| `Field7[1..5]` | Scripted enemy Y positions |
| `Field8[1..5]` | Scripted enemy headings |
| `Field9[1..5]` | Scripted enemy health multiplier |
| `Field10[1..5]` | Scripted enemy per-mount multiplier |
| `Field11[1..5]` | Additional flag passed to enemy creation |
| `Field12[1]` | Boss type ID (if positive) |
| `Field13[1]`, `Field14[1]` | Boss spawn X/Y |
| `Field22[1..5]` | Dynamic enemy type IDs |
| `Field23[1..5]` | Dynamic spawn interval/randomization parameter |
| `Field24[1..5]` | Per-type concurrent enemy cap |
| `Field25` | Event announcement / score-fly-up text |
| `Field26` | Level completion marker |
| `Field28` | Menu transition identifier |
| `Field15..20`, `Field27`, `Field29..30` | Partially unresolved background-enemy/event metadata; inspect `makebackgroundenemy()` and level data before mapping |

The game advances `currentevent` when its time threshold and boss prerequisite are met. `willwin` is then set by the event completion marker; actual level completion waits until `currentenemies < 1`.

**Rebuild implication:** represent each level as an ordered list of typed event resources. Keep scripted spawns, continuous wave rules, boss gates, music and UI announcements as distinct named properties.

## 8. Boss model

There are three boss runtime slots. A boss is composed of up to 10 actor parts created through `makeactor()`. `makeboss()` initializes the boss definition, positions its parts relative to the boss origin and computes aggregate health from the parts.

`dobosses()`:
- sums health across active parts;
- updates the boss health-bar percentage;
- moves the boss origin toward a changing target;
- positions attached parts relative to the origin;
- calls `dobossai()` for type-specific phase behavior;
- detects when all parts are destroyed and increments `bossesdead`.

`dobossai()` dispatches on boss type and contains distinct phase logic. Some boss parts can be repositioned independently; some boss types alter their own movement or spawn enemies.

**Rebuild implication:** use a `BossDefinition` with named parts and a boss controller/state machine. Keep part health, shared health, phase transitions and reward attribution explicit. Do not infer a boss's total health solely from a single body node.

## 9. Modified-build behavior (Gusto changes)

The inspected `loopstuff()` and `docrosshair()` contain explicit custom additions:
- F toggles auto-fire (`forcefireon`);
- V toggles crosshair visibility;
- B cycles player-one crosshair color;
- Shift+C adds profile cash and saves profiles;
- Shift+L advances profile level and saves profiles;
- game mode 3 uses forced crosshair coordinates;
- player 1/player 2 crosshair RGB values are configurable;
- Escape behavior was extended/rewritten with edge-trigger state.

These are modified-build features, not yet proven to belong to the 2006 baseline. Keep them in a separate compatibility/modification layer in the rebuild until compared with the original executable.

## 10. Risks and recommendations

| Priority | Finding | Rebuild treatment |
|---|---|---|
| High | Actor pool can overwrite slot 1 when exhausted and forced allocation is requested | Replace with safe allocation and explicit failure |
| High | Projectile pool overwrites slot 1 when full | Safe overflow policy; telemetry in debug builds |
| High | Enemy AI uses numeric archetype IDs and scratch array | Named archetypes and typed state |
| High | Actor/weapon/projectile/level state coupled through globals and numeric fields | Typed classes/resources and system boundaries |
| Medium | Update order controls collision and spawn timing | Preserve phase order in first playable port |
| Medium | Damage semantics vary by projectile branch | Document per weapon and regression-test |
| Medium | Parent/child actor relationships use array indices | Stable object references/ownership |
| Medium | Sentinel coordinates used to hide inactive entities | Explicit visibility and lifecycle |
| Medium | Gusto-specific controls mixed into gameplay loop | Isolate as optional modified-build settings |
| Medium | Event progression uses multiple clocks and gates | Named event state and deterministic scheduler |

## 11. Recommended next audit passes

1. Complete `typeweaponshot` and `actortypetype` field mapping by tracing all initialization records in `EntryPoint.bb` and all read/write sites.
2. Inspect `makebackgroundenemy()`, bonus/power-up and upgrade systems.
3. Trace profile/high-score binary serialization and define a versioned save format for the rebuild.
4. Extract every actor/enemy/boss/weapon definition into a human-readable catalog.
5. Run the original executable and record baseline observations: resolution, frame pacing, player movement, turret arc, fire cadence, damage, wave timings, boss phases, scoring and upgrade costs.
6. Add a per-feature parity checklist and record intentional deviations.

## 12. Source files reviewed for this pass

- `Main.bb`, `Globals.bb`, `Functions/EntryPoint.bb`
- `Functions/gameloop.bb`, `Functions/loopstuff.bb`, `Functions/loadthings.bb`
- `Functions/doactors.bb`, `Functions/doactorai.bb`, `Functions/doactorendai.bb`
- `Functions/makeactor.bb`, `Functions/doactormovement.bb`, `Functions/updateactor.bb`
- `Functions/doactorcollisions.bb`, `Functions/doactordeathcheck.bb`, `Functions/actordeath.bb`
- `Functions/doplayers.bb`, `Functions/doplayersend.bb`, `Functions/doplayermovement.bb`
- `Functions/doplayerweapons.bb`, `Functions/doplayerhits.bb`, `Functions/docrosshair.bb`
- `Functions/makeenemy.bb`, `Functions/controlenemies.bb`, `Functions/doenemiesend.bb`
- `Functions/makeweaponshot.bb`, `Functions/doweaponshots.bb`, `Functions/doweaponcollision.bb`
- `Functions/dolevels.bb`, `Functions/startlevel.bb`
- `Functions/makeboss.bb`, `Functions/dobosses.bb`, `Functions/dobossai.bb`
- `Types/actortype.bb`, `Types/actortypetype.bb`, `Types/enemytype.bb`, `Types/enemytypetype.bb`
- `Types/playertype.bb`, `Types/levelevent.bb`, `Types/typeweaponshot.bb`, `Types/bosstype.bb`

