# Projectile runtime and field audit

**Scope:** the 72-field `typeweaponshot` structure, static kind definitions, projectile instantiation, per-frame update, collision, target acquisition and cleanup.

**Status:** static source analysis. This is not a dynamic trace of the original executable. No Blitz3D source files were modified.

## Source files and revisions

| File | Blob SHA |
|---|---|
| `Types/typeweaponshot.bb` | `d88cc82f119aa3388c2802339f705cd003fe39e8` |
| `Functions/makeweaponshot.bb` | `df0f893984894fcd7001817bb11ba2b56ff56c6c` |
| `Functions/doweaponshots.bb` | `b6fceea0471adbdbf0467cff2c5a7a3e2885bebd` |
| `Functions/doweaponcollision.bb` | `7460f6f19828f65b3827a776a8fc0d944dfba53e` |
| `Functions/findtarget.bb` | traced in this pass |
| `Functions/stopweapons.bb` | traced in this pass |

## 1. Structural facts

- `typeweaponshot` has 72 fields: `Field0`–`Field71`.
- `Field64` is an integer array with 10 entries.
- Blitz3D type suffix `#` denotes floating-point fields; `%` denotes integer fields.
- The same structure is instantiated twice: `weaponshotkinds(1..40)` are definitions, while `weaponshots(1..100)` are runtime slots.
- The runtime slot active flag is `Field71`; its particle handle is `Field13`.
- `makeweaponshot()` copies a selected set of definition fields into a free runtime slot, but also sets runtime-only state such as position, owner, mount, active flag and initial velocity.

## 2. Field map

Confidence labels:
- **confirmed** — direct use in the traced functions supports the name.
- **partial** — behavior is visible, but the exact domain label or units are not fully resolved.
- **unknown** — copied or declared, but not sufficiently traced to assign a reliable meaning.

| Field | Blitz type | Working interpretation | Confidence | Evidence / notes |
|---:|---|---|---|---|
| 0 | float | RGB transition duration / fade parameter | confirmed | Used as denominator in 255 / Field0 to animate RGB interpolation. |
| 1 | integer | Initial red component | confirmed | Interpolated toward Field4. |
| 2 | integer | Initial green component | confirmed | Interpolated toward Field5. |
| 3 | integer | Initial blue component | confirmed | Interpolated toward Field6. |
| 4 | integer | Target red component | confirmed | RGB interpolation target. |
| 5 | integer | Target green component | confirmed | RGB interpolation target. |
| 6 | integer | Target blue component | confirmed | RGB interpolation target. |
| 7 | integer | Beam / continuous-contact mode | confirmed | 0 uses ordinary projectile collision; 1 enters beam-specific collision handling. |
| 8 | float | Beam length parameter supplied by caller | partial | Copied from makeweaponshot arg6; used as beam length/segment extent. |
| 9 | float | Beam segment origin X | partial | Used as one endpoint when resolving beam collision. |
| 10 | float | Beam segment origin Y | partial | Used as one endpoint when resolving beam collision. |
| 11 | float | Elapsed time in current beam hit cycle | partial | Incremented each update; compared with Field12. |
| 12 | float | Beam hit-cycle threshold | partial | Compared against Field11 to gate repeat beam impact effects. |
| 13 | integer | Particle/sprite entity handle | confirmed | Created by makeparticle(); active update obtains Object.sprite from it. |
| 14 | integer | Projectile texture-layer ID | confirmed | Passed through getweaptexlayer() and used for impact particles. |
| 15 | integer | Projectile kind ID | confirmed | Set from makeweaponshot arg3; used by findtarget() to read faction mask. |
| 16 | float | Unresolved copied parameter | unknown | Copied from kind definition; no meaning established in the traced lifecycle functions. |
| 17 | float | Projectile height growth rate / gravity term | confirmed | Subtracted from vertical velocity each update. |
| 18 | float | Heading / rotation angle | confirmed | Used to orient sprite, steer homing and derive velocity direction. |
| 19 | float | Base speed magnitude | confirmed | Copied from definition; used for homing velocity and beam collision extension. Do not confuse with sprite lifetime Field19. |
| 20 | float | Maximum speed magnitude | confirmed | Caps velocity vector magnitude when positive. |
| 21 | float | Damage amount | confirmed | Applied to actor health or projectile interception health. |
| 22 | float | World position X | confirmed | Updated every frame and used for collision/rendering. |
| 23 | float | World position Y | confirmed | Updated every frame and used for collision/rendering. |
| 24 | float | Homing turn-delay timer | partial | While positive, steering/angle adjustment is gated; decremented toward zero. |
| 25 | integer | Special boundary / attached-shot mode flag | partial | Nonzero suppresses ordinary out-of-bounds deletion; also selects alternate damage handling and enables attached-mount branch when Field27 > 0. |
| 26 | integer | Attached actor slot | confirmed | Used to retrieve actor when Field25 and Field27 enable mount attachment. |
| 27 | integer | Attached weapon-mount index | confirmed | Selects actor turret/mount arrays for attached shot positioning. |
| 28 | float | Beam extent or attached-shot offset | partial | Initialized from caller arg6; used as beam segment length or attachment offset. |
| 29 | float | Velocity X | confirmed | Integrates position and is affected by acceleration, homing and speed cap. |
| 30 | float | Velocity Y | confirmed | Integrates position and is affected by gravity, homing and speed cap. |
| 31 | float | Visual width / primary particle size | partial | Passed to sprite renderer and impact particle creation. |
| 32 | float | Visual height / secondary particle size | partial | Passed to sprite renderer and used in collision/beam geometry. |
| 33 | float | Collision width | confirmed | Passed to anglecollisionxy(). |
| 34 | float | Collision height | confirmed | Passed to anglecollisionxy(). |
| 35 | integer | Persistent-hit / damage-over-time flag | partial | 0 consumes the shot on collision; nonzero enables repeated/timed damage paths. |
| 36 | integer | Source actor slot | confirmed | Used for source attribution and optional owner-velocity inheritance. |
| 37 | integer | Inherit source actor velocity flag | confirmed | When 1, source actor velocity is added to projectile displacement. |
| 38 | integer | Owner-relative delayed-launch mode | partial | When active with positive Field40, projectile follows a timed owner-relative launch sequence. |
| 39 | float | Owner-relative launch offset / speed parameter | partial | Used to offset projectile from source actor during delayed-launch sequence. |
| 40 | float | Delayed-launch countdown | confirmed | Decremented while Field38 mode is active; homing resumes after it expires. |
| 41 | integer | Weapon sound index | confirmed | Selects sndweapon(index) when projectile is created. |
| 42 | integer | Homing enabled flag | confirmed | Enables findtarget() and steering behavior. |
| 43 | integer | Target acquisition range | confirmed | Passed to findtarget() as range. |
| 44 | float | Homing turn rate | confirmed | Passed to reachangle() and steering acceleration. |
| 45 | integer | Current target slot | confirmed | Stores selected target returned by findtarget(). |
| 46 | integer | Random angular spread magnitude | confirmed | Used to randomize initial firing angle. |
| 47 | float | Scale growth rate | confirmed | Added to Field48 each frame. |
| 48 | float | Current projectile visual scale | confirmed | Updated by Field47 and passed to setsprite(). |
| 49 | float | Projectile width growth rate | confirmed | Added to Field31 each frame; Field31 is also used as visual width/color channel in rendering, so inspect per-kind values before normalizing. |
| 50 | float | Vertical acceleration | confirmed | Added to Field32 each frame. |
| 51 | float | Angular velocity | confirmed | Added to Field18 each frame. |
| 52 | float | Trail emission interval | confirmed | Compared with Field53 to decide when to emit a trail particle. |
| 53 | float | Trail emission timer | confirmed | Accumulates frame time and resets after trail emission. |
| 54 | float | Trail particle speed parameter | partial | Negated and passed as makeparticle() speed magnitude. |
| 55 | float | Trail particle size multiplier | confirmed | Scales randomized trail particle size. |
| 56 | float | Trail particle width and height | partial | Passed as both width and height to makeparticle(). |
| 57 | integer | Trail texture/effect identifier | confirmed | Passed as particle texture/effect selector. |
| 58 | float | Unresolved field | unknown | Declared float; no assignment found in makeweaponshot() or use in the traced update/collision paths. |
| 59 | float | Unresolved field | unknown | Declared float; no assignment found in makeweaponshot() or use in the traced update/collision paths. |
| 60 | float | Velocity multiplier / drag coefficient | partial | When positive, modifies speed and velocity by (Field60 - 1) per frame. |
| 61 | integer | Muzzle-flash color selector | confirmed | Values 1–5 select red, green, blue, yellow and purple flash. |
| 62 | integer | Impact particle-burst flag | confirmed | When 1, creates a particle burst on impact. |
| 63 | integer | Impact explosion size | confirmed | Positive value creates explosion on impact. |
| 64 | integer[10] | Target faction mask | confirmed | Ten-element mask; compared against actor faction or projectile faction. |
| 65 | integer | Projectile faction ID | confirmed | Used by findtarget() when considering projectiles as targets. |
| 66 | integer | Projectile interceptability / health-enabled flag | partial | A positive value makes a projectile eligible for projectile-vs-projectile collision. |
| 67 | integer | Current projectile interception health | confirmed | Reduced by projectile damage; projectile deactivated at zero or below. |
| 68 | integer | Unresolved copied parameter | unknown | Copied from kind definition; no meaning established in traced lifecycle functions. |
| 69 | float | RGB transition / hit-flash state | partial | Drives RGB interpolation; collision sets it to 255 to trigger a color transition. |
| 70 | float | Unresolved field | unknown | Declared float; not initialized by makeweaponshot() in the inspected code. |
| 71 | integer | Active projectile slot flag | confirmed | 1 means slot is active; 0 means free. |

Important caution: fields can have different roles in the definition record and the runtime record. In particular, `Field19` in a projectile record is a speed magnitude used by homing/beam logic; `Field19` in the associated `sprite` record is a lifetime timer. These are different objects with the same field index.

`makeparticle()` and `setsprite()` map projectile `Field31` and `Field32` into sprite width and height; `Field49` and `Field50` are width and height growth rates. `Field54` supplies trail-particle speed, `Field56` trail width/height, and `Field55` trail rotation speed.

## 3. Creation pipeline

`makeweaponshot(x, y, angle, kind_id, owner_actor, mount_id, extra)`:

1. Rejects an out-of-world spawn unless the kind's `Field25` permits it.
2. Scans 100 runtime slots for a free `Field71 = 0` slot. If none exists, it forcibly reuses slot 1.
3. Stores the kind ID and randomizes the firing angle using `Field46`.
4. Creates the particle/sprite using the kind's color, size, texture and lifetime-like particle parameters.
5. Copies projectile behavior parameters into the runtime record.
6. Stores owner actor, mount, source position and initial velocity.
7. Emits a muzzle flash if the source actor is valid and `Field61` selects a flash color.
8. Plays `sndweapon(Field41)` when sound is enabled and the sound index is positive.

A failed particle allocation returns before the slot is marked active. This path should be tested in the Godot implementation to avoid leaving partially initialized pooled objects.

## 4. Per-frame update order

For each active slot, `doweaponshots()` performs the following broad sequence:

1. Validate the sprite handle and its remaining lifetime; release the slot if invalid/expired.
2. Integrate position using velocity and the frame factor. If `Field37 = 1`, source actor velocity is inherited.
3. Run collision handling through `doweaponcollision()`.
4. Apply world-boundary deletion when `Field25 = 0`.
5. Update visual scale, sprite lifetime, angular velocity and visual dimensions.
6. Emit trail particles according to `Field52`/`Field53`.
7. Process owner-relative delayed launch when `Field38` is active.
8. Acquire/validate a homing target when `Field42 = 1`; steer using `Field43` and `Field44`.
9. Apply velocity multiplier/drag and maximum-speed cap.
10. Apply vertical acceleration and rotate the sprite toward its velocity.
11. Decrement homing delay.
12. If attached mode is active, recompute position and angle from the selected actor mount.
13. Update the rendered sprite transform, size and color.

This ordering matters. Collision is checked after initial position integration but before several later velocity/steering adjustments in the same update.

## 5. Collision and damage model

### Projectile versus actor

- Iterates actor slots 1–20.
- Candidate actor must be active, older than 180 time units, and not suppressed by its actor flag.
- The projectile's `Field64[1..10]` mask must include the candidate actor's faction (`actor.Field30`).
- Uses rotated rectangle collision via `anglecollisionxy()`.
- On hit, writes projectile owner actor (`Field36`) into target actor attribution field `Field79`.
- Ordinary projectiles subtract `Field21` damage and are deactivated.
- Persistent/beam mode has separate damage timing and scaling behavior.
- Optional impact burst (`Field62`) and explosion (`Field63`) are created.
- If the target actor has a configured death-trigger projectile, collision may spawn it.

### Projectile versus projectile

- Iterates the other 100 projectile slots.
- The target projectile must be active and have positive interception state (`Field66`).
- The attacker's faction mask (`Field64`) must include the target projectile faction (`Field65`).
- Damage reduces target projectile `Field67`; at zero or below, the target projectile is deactivated.
- The attacking projectile is consumed when it is not in persistent mode.

### Beam / persistent behavior

The collision function distinguishes ordinary shots from persistent shots using `Field7` and `Field35`. Beam mode evaluates the segment defined by the runtime position/anchor data and applies repeat damage according to the beam timers. The exact visual convention and all units still require comparison with the original executable.

## 6. Target acquisition

`findtarget()` searches up to 20 actor slots and optionally up to 100 projectile slots.

- Candidate actors must be active, older than 180 time units, and not suppressed.
- The projectile faction mask determines which actor factions are eligible.
- The nearest eligible candidate within the requested range is selected.
- When projectile targeting is enabled, active interceptable projectiles may also be considered.
- The current target handle is retained in `Field45`; it is revalidated during subsequent updates.

## 7. Pooling and cleanup risks

- Runtime capacity: 100 projectiles.
- When full, allocator reuses slot 1 rather than dropping the new projectile or selecting the oldest.
- `stopweapons()` clears active flags and sprite visibility/lifetime.
- Natural expiration clears the active flag and entity handle.
- Collision can deactivate a projectile and set the sprite lifetime to zero.
- A slot can be reused while the previous particle entity still exists until the sprite update/cleanup path processes it. This should be explicitly tested before choosing a Godot pool lifecycle.

## 8. Godot implementation consequences

Do not port the 72 positional fields as a single untyped script object. Split responsibilities:

- `ProjectileDefinition` (immutable): visual preset, damage, faction mask, speed, acceleration, homing, lifetime, trail, impact and interception settings.
- `ProjectileInstance` (runtime): position, velocity, angle, age, remaining lifetime, current target, owner, mount, current health, beam timer and pool state.
- `ProjectileVisual`: sprite/animation, color transition, scale, trail and impact presentation.
- `ProjectileCollisionProfile`: collision dimensions, faction filters, persistence and interception rules.

Preserve a source-field-to-property mapping in the project during the first parity phase. Do not convert unexplained fields 16, 58, 59, 68 and 70 into guessed features.

## 9. Unresolved questions

1. Resolve the copied but unexplained fields 16 and 68, and unused/uninitialized fields 58, 59 and 70.
2. Determine the exact meaning and units of beam parameters 8–12 and 28.
3. Confirm whether `Field35` represents continuous damage, persistence, or both in all configured kinds.
4. Verify whether `Field25` is a general special-boundary flag or specifically the attached-shot mode; current code uses it in more than one branch.
5. Determine how kind IDs 39–40 are reached, if at all.
6. Test pool exhaustion, sprite cleanup and pause/frame-factor behavior in the original executable.
7. Compare all observed behavior against Mighty Rodent rather than assuming the Gusto-modified source is the original baseline.

