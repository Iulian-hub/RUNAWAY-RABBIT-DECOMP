# Actor archetype and projectile definition extraction

**Status:** static source extraction, first structured pass. No Blitz3D source files were modified.

## Source and reproducibility

- Repository: `Iulian-hub/RUNAWAY-RABBIT-DECOMP`
- Branch: `main`
- Source: [Functions/EntryPoint.bb](../Functions/EntryPoint.bb)
- Source blob SHA: `28d80ac3e696a3a97eb40313664c445fd3235449`
- Machine-readable extraction: [entity_definitions.extracted.json](data/entity_definitions.extracted.json)
- Extraction preserves Blitz3D literal values (including hexadecimal literals such as `$0B`) and records source line numbers in the JSON.

## Findings

### Actor archetype capacity versus populated definitions

The program allocates `actortypes(1..100)`, but this is a capacity, not evidence of 100 populated archetypes.

- Player templates: IDs **1–2**, initialized through a loop using `actortypes(local0)`; these are represented separately in the JSON rather than expanded into two fabricated static records.
- Explicitly configured archetype IDs: **3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44** (34 definitions).
- Gaps inside the explicit range: **23–30**.
- No explicit archetype assignments were found for IDs **45–100** in this source file.
- Enemy creation uses `makeactor(..., enemy_id + 2, ...)`; therefore enemy ID 1 maps to archetype 3. Boss construction passes component archetype IDs directly to `makeactor()`.

The resulting distinction is important: **100 allocated slots ≠ 100 configured archetypes**.

### Actor summary

The source initializes `actorgraphicpath(1..100)` to `gfx/blank.bmp`. No per-ID actor graphic path assignments were found in `Functions/EntryPoint.bb` or `Functions/artdefinitions.bb` during this pass. Therefore graphic IDs are recorded, but their intended image paths remain unresolved; animation may instead be provided through the animation-loop field.

| Archetype ID | Graphic ID (Field17) | Actor image path | Animation ID (Field16) | Health (Field25) | Faction (Field49) | Mounts observed |
|---:|---:|---|---:|---:|---:|---|
| 3 | $03 | Unresolved (blank default) | $00 | 3.0 | $02 | 1: shot $0B, turret $00, rate 1.0, offset(15.0, -10.0) |
| 4 | $04 | Unresolved (blank default) | $00 | 5.0 | $02 | 1: shot $0B, turret $00, rate 0.5, offset(0.0, -10.0) |
| 5 | $05 | Unresolved (blank default) | $00 | 8.0 | $02 | 1: shot $0C, turret $00, rate 2.0, offset(0.0, -15.0) |
| 6 | $06 | Unresolved (blank default) | $00 | 28.0 | $02 | 1: shot $0F, turret $00, rate 4.0, offset(0.0, -15.0) |
| 7 | $07 | Unresolved (blank default) | $00 | 12.0 | $02 | 1: shot $0F, turret $00, rate 0.9, offset(20.0, -10.0) |
| 8 | $08 | Unresolved (blank default) | $00 | 8.0 | $02 | — |
| 9 | $09 | Unresolved (blank default) | $00 | 9.0 | $02 | 1: shot $0E, turret $04, rate 1.0, offset(-7.0, 10.0) |
| 10 | $0A | Unresolved (blank default) | $00 | 20.0 | $02 | 1: shot $0F, turret $00, rate 0.7, offset(45.0, 15.0) |
| 11 | $0B | Unresolved (blank default) | $00 | 70.0 | $02 | 1: shot $0B, turret $00, rate 0.15, offset(50.0, -30.0)<br>2: shot $0B, turret $00, rate 0.15, offset(-60.0, -30.0) |
| 12 | $0C | Unresolved (blank default) | $00 | 16.0 | $05 | — |
| 13 | $0D | Unresolved (blank default) | $00 | 16.0 | $02 | — |
| 14 | $0E | Unresolved (blank default) | $00 | 25.0 | $02 | 1: shot $0D, turret $02, rate —, offset(-30.0, -30.0) |
| 15 | $0F | Unresolved (blank default) | $00 | 100.0 | $02 | 1: shot $0D, turret $00, rate 0.75, offset(80.0, -50.0)<br>2: shot $0D, turret $00, rate 0.75, offset(-80.0, -50.0) |
| 16 | $10 | Unresolved (blank default) | $00 | 60.0 | $02 | 1: shot $0A, turret $02, rate 0.75, offset(0.0, -42.0) |
| 17 | $11 | Unresolved (blank default) | $00 | 300.0 | $02 | — |
| 18 | $12 | Unresolved (blank default) | $00 | 150.0 | $02 | 1: shot $0D, turret $02, rate 0.5, offset(0.0, -30.0) |
| 19 | $13 | Unresolved (blank default) | $00 | 12.0 | $02 | — |
| 20 | $14 | Unresolved (blank default) | $00 | 22.0 | $02 | — |
| 21 | $15 | Unresolved (blank default) | $00 | 2000.0 | $00 | — |
| 22 | $16 | Unresolved (blank default) | $00 | 2000.0 | $06 | — |
| 31 | $19 | Unresolved (blank default) | $00 | 900.0 | $02 | 1: shot $0E, turret $05, rate 0.65, offset(0.0, 45.0)<br>2: shot $0E, turret $05, rate 0.65, offset(0.0, -75.0) |
| 32 | $00 | Unresolved (blank default) | $02 | 450.0 | $02 | 1: shot $0D, turret $07, rate 0.7, offset(0.0, -15.0) |
| 33 | $1E | Unresolved (blank default) | $00 | 1300.0 | $02 | 1: shot $0E, turret $05, rate 1.5, offset(60.0, -20.0)<br>2: shot $0E, turret $05, rate 1.5, offset(-60.0, -20.0) |
| 34 | $00 | Unresolved (blank default) | $05 | 600.0 | $02 | 1: shot $0D, turret $07, rate 1.0, offset(-10.0, -20.0) |
| 35 | $00 | Unresolved (blank default) | $06 | 600.0 | $02 | 1: shot $0D, turret $07, rate 1.0, offset(10.0, -25.0) |
| 36 | $00 | Unresolved (blank default) | $07 | 2000.0 | $06 | — |
| 37 | $17 | Unresolved (blank default) | $00 | 1350.0 | $02 | 1: shot $0E, turret $06, rate 0.5, offset(60.0, 0.0)<br>2: shot $0E, turret $02, rate 0.5, offset(60.0, 0.0)<br>3: shot $0E, turret $02, rate 0.5, offset(60.0, 0.0) |
| 38 | $18 | Unresolved (blank default) | $00 | 1000.0 | $02 | 1: shot $0A, turret $02, rate 0.7, offset(0.0, 0.0) |
| 39 | $04 | Unresolved (blank default) | $00 | 500000.0 | $0A | — |
| 40 | $00 | Unresolved (blank default) | $14 | 150000000.0 | $03 | — |
| 41 | $1A | Unresolved (blank default) | $00 | 7000.0 | $02 | 1: shot $0E, turret $05, rate 0.3, offset(0.0, 100.0)<br>2: shot $0E, turret $05, rate 0.4, offset(100.0, 50.0)<br>3: shot $0E, turret $05, rate 0.4, offset(-100.0, 50.0)<br>4: shot $0E, turret $05, rate 0.5, offset(50.0, -50.0)<br>5: shot $0E, turret $05, rate 0.5, offset(-50.0, -50.0) |
| 42 | $1B | Unresolved (blank default) | $00 | 8000.0 | $02 | 1: shot $0E, turret $05, rate 0.1, offset(0.0, 30.0)<br>2: shot $0E, turret $05, rate 0.35, offset(100.0, -90.0)<br>3: shot $0D, turret $07, rate 0.9, offset(50.0, -10.0)<br>4: shot $0D, turret $07, rate 1.2, offset(10.0, -120.0)<br>5: shot $0E, turret $05, rate 0.4, offset(-100.0, -70.0) |
| 43 | $1C | Unresolved (blank default) | $00 | 6000.0 | $02 | 1: shot $0D, turret $07, rate 0.35, offset(0.0, 100.0)<br>2: shot $0D, turret $07, rate 1.1, offset(20.0, 60.0)<br>3: shot $0E, turret $05, rate 0.35, offset(-100.0, 60.0)<br>4: shot $0E, turret $05, rate 0.4, offset(20.0, -60.0)<br>5: shot $0E, turret $05, rate 0.4, offset(-100.0, -60.0) |
| 44 | $1D | Unresolved (blank default) | $00 | 5000.0 | $02 | 1: shot $0E, turret $05, rate 0.3, offset(0.0, 0.0)<br>2: shot $0E, turret $05, rate 0.35, offset(0.0, 50.0)<br>3: shot $0D, turret $07, rate 1.0, offset(-20.0, -20.0)<br>4: shot $0E, turret $05, rate 0.4, offset(-60.0, -90.0)<br>5: shot $0E, turret $05, rate 0.4, offset(-50.0, -50.0) |

Mount fields are retained in the JSON without renaming. Based on `makeactor()` and `doactorweapons()`, the key source fields are:

- `Field27[i]`: mount enabled/defined flag (the mount loop tests it).
- `Field40[i]`: projectile kind copied into runtime actor `Field61[i]).
- `Field29[i]`: turret graphic selector, copied into runtime `Field62[i]`.
- `Field37[i]`: firing interval multiplier, copied into runtime `Field76[i]`.
- `Field36[i]` and `Field38[i]`: mount X/Y offsets, copied into runtime `Field71[i]` / `Field72[i]`.
- `Field39[i]`: mount base-angle offset, copied into runtime `Field73[i]`.
- `Field34[i]`: turret scale, copied into runtime `Field66[i]`.
- `Field35[i]`: initial turret angle, copied into runtime `Field65[i]`.
- `Field33[i]`: turret rotation speed, copied into runtime `Field69[i]`.
- `Field31[i]`: automatic aiming flag, copied into runtime `Field67[i]`.
- `Field32[i]`: mount firing permission, copied into runtime `Field68[i]`.
- `Field28[i]`, `Field30[i]`: further render/target configuration copied into runtime `Field64[i]` / `Field63[i]`; their exact domain meaning remains provisional.

The player weapon setup is a separate runtime path: `doplayerweaponsetup()` writes the player actor's mount slots dynamically. It should not be mistaken for the static enemy/boss archetype table.

#### Cross-reference: which projectile kinds are actually selected

The player setup code maps upgrade families to projectile kinds:

| Player weapon family | Runtime mount | Projectile kind source |
|---|---:|---|
| Cannon | 1–5 | `cannontype()` selects kinds 1, 2 or 3 depending on upgrade level |
| Missile | 6 | Kind 4 |
| Auto-turret | 7 | Kind 6 |
| W-Laser | 8 | Kind 5 |
| B-Laser | 9 | `beamlasertype()` selects 7, 8 or 9 |

The static enemy/boss archetype mount table uses projectile kinds **10–15**. Separately, archetypes **19** and **20** define death-emitted projectile kinds **16** and **17** in `actortypes.Field15[1..5]`; `makeactor()` transfers those to runtime actor `Field18[]`, and `doactordeath()` fires them when the actor's death sequence completes.

This accounts for explicit kinds 1–17 across player weapons, enemy/boss mounts and death-emitted shots. Kinds 39–40 have explicit parameter assignments but are not referenced by the actor mount or death-emission tables traced in this pass. Kinds 18–38 have neither explicit per-kind assignments nor references in those tables. This is a static cross-reference, not proof that 39–40 or 18–38 can never be reached through any other code path.

### Projectile kind coverage

The program allocates **40** projectile-kind records and initializes common defaults for IDs 1–40:

| Field | Default literal |
|---:|---:|
| Field0 | `60.0` |
| Field1 | `$FF` |
| Field2 | `$00` |
| Field3 | `$00` |
| Field4 | `$FF` |
| Field5 | `$FF` |
| Field6 | `$FF` |

Only **19** IDs have explicit per-kind assignments in `EntryPoint.bb`: 1–17, 39 and 40. IDs **18–38** have no explicit per-kind assignments in this file and therefore retain the common defaults unless modified elsewhere at runtime. `loadweaponshots()` is an empty stub, so it does not supply the missing definitions.

| Kind ID | Explicit assignments? | Field14 | Field19 | Field21 | Field25 | Field31 / Field32 | Field41 | Field42 | Field70 |
|---:|:---:|---:|---:|---:|---:|---:|---:|---:|---:|
| 1 | Da | $16 | 20.0 | 1.3 | — | 10.0 / 25.0 | $02 | — | 8.0 |
| 2 | Da | $15 | 23.0 | 1.5 | — | 12.0 / 28.0 | $03 | — | 7.0 |
| 3 | Da | $17 | 26.0 | 1.7 | — | 14.0 / 32.0 | $01 | — | 6.0 |
| 4 | Da | $23 | 1.0 | 17.0 | — | 25.0 / 15.0 | $05 | $01 | 120.0 |
| 5 | Da | $22 | 25.0 | 2.0 | — | 12.0 / 21.0 | $0A | — | 20.0 |
| 6 | Da | $21 | 4.0 | 1.5 | — | 15.0 / 15.0 | $0B | — | 90.0 |
| 7 | Da | $1A | 5.0 | 2.0 | $01 | 10.0 / 500.0 | $08 | $00 | 60.0 |
| 8 | Da | $19 | 5.0 | 3.0 | $01 | 15.0 / 500.0 | $08 | $00 | 60.0 |
| 9 | Da | $1B | 5.0 | 4.0 | $01 | 20.0 / 500.0 | $08 | $00 | 60.0 |
| 10 | Da | $18 | 5.0 | 1.0 | $01 | 15.0 / 500.0 | $09 | $00 | 150.0 |
| 11 | Da | $1F | 2.0 | 1.0 | $00 | 15.0 / 15.0 | $07 | $00 | 90.0 |
| 12 | Da | $1D | 2.0 | 1.0 | $00 | 12.0 / 12.0 | $07 | $01 | 120.0 |
| 13 | Da | $1E | 1.0 | 1.0 | — | 15.0 / 20.0 | $05 | $01 | 90.0 |
| 14 | Da | $14 | 4.0 | 1.0 | — | 26.0 / 15.0 | $04 | — | 120.0 |
| 15 | Da | $14 | 1.0 | 1.0 | — | 26.0 / 15.0 | $04 | — | 30.0 |
| 16 | Da | $1F | 10.0 | 1.0 | $00 | 15.0 / 15.0 | $07 | $00 | 60.0 |
| 17 | Da | $14 | 10.0 | 1.0 | $00 | 26.0 / 15.0 | $07 | $00 | 60.0 |
| 18 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 19 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 20 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 21 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 22 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 23 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 24 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 25 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 26 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 27 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 28 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 29 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 30 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 31 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 32 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 33 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 34 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 35 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 36 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 37 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 38 | Nu | Unresolved (blank default) | — | — | — | — / — | — | — | — |
| 39 | Da | $06 | 4.0 | 1.0 | $00 | 30.0 / 30.0 | $00 | $00 | 60.0 |
| 40 | Da | $07 | 1.0 | 1.0 | $00 | 30.0 / 30.0 | $07 | $00 | 60.0 |

These columns are raw source fields, not invented names. Existing call-site tracing gives several useful anchors: Field14 selects the weapon texture layer; Field19 is copied into the projectile instance and participates in lifetime/collision behavior; Field21 is damage; Field25 changes collision/boundary handling; Field41 selects an associated weapon sound; Field42 activates homing/target search; Field70 is the firing interval copied into an actor mount.

### Runtime implications

- `makeactor()` copies archetype mount configuration into the active actor's parallel arrays.
- `doactorweapons()` increments each mount timer by the frame factor, multiplies the projectile's base interval by the mount multiplier, and calls `makeweaponshot()` when firing gates pass.
- `makeweaponshot()` copies the selected kind's parameters into an active projectile record.
- Active projectile pool capacity is 100. The allocator scans for a free slot and falls back to slot 1 when none is found; this is an overwrite risk, not a safe queue policy.
- The same `typeweaponshot` structure is used for both definitions (`weaponshotkinds`) and active instances (`weaponshots`). Godot should split these into distinct typed resources/data classes.

## Godot data-model recommendation

Use two separate immutable data resources:

1. `ActorArchetype`: identity, visual/animation references, faction, health, collision dimensions, movement parameters and an array of `WeaponMountDefinition`.
2. `ProjectileDefinition`: visual layer, damage, lifetime, velocity/acceleration, collision dimensions, faction mask, homing parameters, effects and sound.

A `WeaponMountDefinition` should refer to a `ProjectileDefinition` by stable ID, not by array position or by a decompiler field number. Keep a source-field mapping alongside the normalized schema until original-game parity checks are complete.

## Open questions / next audit tasks

1. Determine whether projectile kinds 18–38 are intentionally unused, reserved, or referenced indirectly by code paths not covered by this first extraction.
2. Trace every projectile field from definition through creation, update, collision and cleanup before finalizing the normalized Godot schema.
3. Expand the player archetype templates 1–2 into explicit records, including all loop-derived defaults.
4. Resolve actor graphic IDs, animation loops and boss-component dependencies against the original Mighty Rodent executable.
5. Compare the Gusto-modified source with the original executable before treating these values as the Mighty Rodent baseline.

