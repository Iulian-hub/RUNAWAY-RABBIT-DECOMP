# Profile, player setup and upgrade progression audit

**Status:** static source audit of profile persistence, campaign setup, shop progression and player weapon configuration. No Blitz3D source files were modified.

## Source revisions

| Source | Blob SHA |
|---|---|
| `Types/profile.bb` | `fdc11773d8e0dff9cb02b8ab5a02126734f156d7` |
| `Types/playertype.bb` | `a09b30bfb55cfc1213328db8c5c0b4a3b34806de` |
| `Types/playersetuptype.bb` | `821f4220fed5d27c499ff0fa04e39c8990986806` |
| `Types/upgradetypes.bb` | `b0fe5142d160b8eff3c031f0df3b96d27d6c6109` |
| `Functions/loadprofiles.bb` | `9185a28529a30200cb101ee07b096f0f8312bc78` |
| `Functions/saveprofiles.bb` | `9726d87b03b21f744e731d9ab1cfd98629ba5c18` |
| `Functions/newprofile.bb` | `98f7d25d046558b0e440f5cd696b3eaff4a11315` |
| `Functions/deleteprofile.bb` | `fa4206792655c143624cbb6f7491f498d1cbc065` |
| `Functions/startlevel.bb` | `8a3f6407804bc8e472cbc3dc20b3c6e3c5641825` |
| `Functions/menuevent.bb` | `78ce662a9185202d9fe96f26ec7dab21f29d26bf` |
| `Functions/doplayerweaponsetup.bb` | `882758e6f65cf6bb898c3ac53c43daf87e988070` |
| `Functions/addplayer.bb` | `249f9ae6e9ec6fd512f4e1dda7bb98fc95067a00` |

Main initialization and upgrade tables are in `Functions/EntryPoint.bb` (source blob SHA `28d80ac3e696a3a97eb40313664c445fd3235449`).

## 1. Persistent profile structure

The game allocates **10 profile records**, each with one string field and 16 integer fields.

| Field | Meaning | Evidence / status |
|---:|---|---|
| 0 | Profile name | Used in profile selection and high-score records. |
| 1 | Campaign level unlocked / next level | Starts at 1; after a win, increments if it equals the completed level and is below 29. |
| 2 | Sound enabled | Loaded into global `soundon`; changed by sound option. |
| 3 | Hints enabled | Toggled by `hintbutton`. |
| 4 | Music enabled | Loaded into global `musicon`; changed by music option. |
| 5 | New-profile skill selection | Set from `tempskill`; used to choose starting upgrade levels. No later gameplay effect established in this pass. |
| 6 | Persistent money balance | Earned level money is added here after a campaign win; shop purchases subtract from it. |
| 7 | Chain Cannon upgrade level | Shop upgrade ID 4; levels 1–10. |
| 8 | Beam Laser upgrade level | Shop upgrade ID 5; levels 1–5. |
| 9 | Homing Missiles upgrade level | Shop upgrade ID 6; levels 0–5. |
| 10 | Auto Turret upgrade level | Shop upgrade ID 7; levels 0–5. |
| 11 | Wave Laser upgrade level | Shop upgrade ID 8; levels 0–5. |
| 12 | Shield Power upgrade level | Shop upgrade ID 2; levels 1–4 in new profiles. |
| 13 | Giga Bombs upgrade level | Shop upgrade ID 3; levels 1–3 in new profiles. |
| 14 | Number of players | Cycles between 1 and 2; used to select campaign player count. |
| 15 | Unresolved setup value | Normalized from 0 to 1 on load and copied into `playersetup.Field9`; no consumer found in the traced campaign setup. |
| 16 | Lives upgrade level | Shop upgrade ID 1; new profiles start at level 2. |

### Binary persistence format

`profiles.dat` is written in this order:

1. Current profile index (integer).
2. For each of 10 profiles:
   - name (string);
   - Field4, Field2, Field3, Field1;
   - Field5 through Field16 in ascending order.

The load function reads exactly the same sequence. It validates the current profile index to 0–10 and normalizes profile Field15 from 0 to 1.

There is no format magic, schema version, checksum or migration layer in the inspected reader/writer. A future Godot save format should be versioned and should not reuse this positional binary layout.

## 2. New-profile defaults

`newprofile()` sets the following values:

- Campaign level: 1.
- Sound, hints and music: enabled.
- Money: 0.
- Chain Cannon: level 1.
- Beam Laser: level 1.
- Homing Missiles, Auto Turret and Wave Laser: level 0.
- Number of players: 1.
- Field15: 1.
- Lives: level 2.

The selected skill value (1–4) controls initial Shield Power and Giga Bomb levels:

| Skill selector | Lives | Shield Power | Giga Bombs |
|---:|---:|---:|---:|
| 1 | 2 | 4 | 3 |
| 2 | 2 | 3 | 2 |
| 3 | 2 | 2 | 1 |
| 4 | 2 | 1 | 1 |

The code establishes these starting values, but this pass does not establish user-facing names for skill values 1–4 or prove that Field5 later affects difficulty scaling.

## 3. Shop upgrade catalog

The eight shop IDs are mapped to persistent profile fields as follows:

| Shop ID | Upgrade | Profile field | Maximum level |
|---:|---|---:|---:|
| 1 | Lives | 16 | 4 |
| 2 | Shield Power | 12 | 4 |
| 3 | Giga Bombs | 13 | 4 |
| 4 | Chain Cannon | 7 | 10 |
| 5 | Beam Laser | 8 | 5 |
| 6 | Homing Missiles | 9 | 5 |
| 7 | Auto Turret | 10 | 5 |
| 8 | Wave Laser | 11 | 5 |

Prices below are converted from the hexadecimal integer literals in EntryPoint.bb to decimal currency units:

| Upgrade | Purchase prices by level |
|---|---|
| Lives | 10,000 · 15,000 · 23,000 · 30,000 |
| Shield Power | 5,000 · 7,000 · 10,000 · 15,000 |
| Giga Bombs | 6,000 · 9,000 · 12,000 · 18,000 |
| Chain Cannon | 1,000 · 20,000 · 30,000 · 45,000 · 67,500 · 100,000 · 150,000 · 275,000 · 350,000 · 500,000 |
| Beam Laser | 40,000 · 60,000 · 90,000 · 135,000 · 200,000 |
| Homing Missiles | 60,000 · 90,000 · 135,000 · 200,000 · 300,000 |
| Auto Turret | 80,000 · 120,000 · 180,000 · 270,000 · 450,000 |
| Wave Laser | 100,000 · 150,000 · 325,000 · 450,000 · 600,000 |

A purchase is accepted when the profile balance is at least the next level's price. The level is incremented and the price deducted, then `profiles.dat` is saved.

## 4. Player setup record

`playersetup(1..2)` is a per-player staging record populated by `startlevel()` from the persistent profile.

| Field | Meaning |
|---:|---|
| 0 | Shield Power level |
| 1 | Lives level |
| 2 | Control mode / input preset (1 or 2 in normal initialization) |
| 3 | Giga Bomb count/level |
| 4 | Chain Cannon level |
| 5 | Homing Missiles level |
| 6 | Beam Laser level |
| 7 | Wave Laser level |
| 8 | Auto Turret level |
| 9 | Unresolved; receives profile Field15 but is not consumed by the traced normal player setup |

In normal campaign mode the same profile upgrades are copied into both player setup records. The number of players is controlled separately by profile Field14.

Special modes replace the normal profile-derived values with hard-coded setup values. Demo/intro setup (`startlevel(..., arg2=1)`) overrides the first player's setup again. Do not assume every game mode uses persistent profile upgrades.

## 5. Weapon slot reconstruction

The player actor is configured dynamically by `doplayerweaponsetup()`. This is distinct from static enemy/boss archetype mounts.

| Player actor mount | Weapon | Projectile kind |
|---:|---|---|
| 1–5 | Chain Cannon barrels | Kinds 1, 2 or 3, selected by cannon upgrade table |
| 6 | Homing Missiles | Kind 4 |
| 7 | Auto Turret | Kind 6 |
| 8 | Wave Laser | Kind 5 |
| 9 | Beam Laser | Kinds 7, 8 or 9 |
| 10 | Not configured by this function | Unresolved / unused in this setup path |

### Chain Cannon progression

The cannon upgrade level selects both barrel count and projectile kind:

| Cannon level | Barrels | Projectile kind per barrel |
|---:|---:|---|
| 1 | 1 | 1 |
| 2 | 2 | 1 |
| 3 | 3 | 1 |
| 4 | 2 | 2 |
| 5 | 3 | 2 |
| 6 | 4 | 2 |
| 7 | 3 | 3 |
| 8 | 5 | 2 |
| 9 | 4 | 3 |
| 10 | 5 | 3 |

Barrel angle offsets are defined for 1–5 barrels: centered at 0°, then symmetric spreads of ±4°, ±6°/0°, ±8°/±4°, and ±10°/±5°/0°.

### Other weapon progression

| Weapon | Upgrade levels | Projectile kind | Fire-interval multiplier by level |
|---|---:|---:|---|
| Homing Missiles | 1–5 | 4 | 1.0, 0.5, 0.33, 0.25, 0.2 |
| Auto Turret | 1–5 | 6 | 1.0, 0.666, 0.55, 0.45, 0.333 |
| Wave Laser | 1–5 | 5 | 1.0, 0.5, 0.33, 0.25, 0.2 |
| Beam Laser | 1–5 | 7, 7, 8, 8, 9 | 1.0, 0.75, 1.0, 0.85, 1.0 |

These are the multipliers written into actor mount Field76. The runtime firing interval is calculated from the projectile kind's Field70 multiplied by this value.

## 6. Player runtime record: confirmed subset

`playertype` has 34 fields. This pass confirms the following subset:

| Field | Meaning | Evidence |
|---:|---|---|
| 0 | Player active flag | Set to 1 in addplayer(). |
| 1 | Actor slot | Used to resolve the player actor. |
| 2 | Crosshair sprite/entity | Created in loadplayers(). |
| 3 | Control mode | Selects keyboard/mouse/joystick fire controls. |
| 4–5 | Player world X/Y | Drives shield and actor positioning. |
| 6 | Horizontal movement velocity | Derived from crosshair/player target offset and integrated into Field4. |
| 7 | Kill count | Incremented when attributed kills are awarded. |
| 8 | Death count for the current level | Reset at level start and incremented when player actor health reaches zero. |
| 9 | Remaining shield charges / shield level | Decremented when a hit consumes a shield; also selects the shield HUD/color case. |
| 10 | Lives count | Initialized from player setup and used by player life logic. |
| 11 | In-level cash/score accumulator | Receives kill rewards; at level completion is used for score submission and, on campaign victory, credited to persistent profile money. |
| 12 | Re-entry / invulnerability timer | Initialized to 120 and affects actor alpha in updateplayer(). |
| 13 | Weapon-setup transition timer | Drives temporary red actor tint and delayed weapon reconfiguration. |
| 14 | Player actor alpha | Applied to actor and turret entities; modulated during shield/invulnerability transitions. |
| 15 | Re-entry fade direction | Alternates between positive and negative values while the player actor fades during the 120-unit re-entry timer. |
| 16 | Death-sequence active flag | Selects the death animation/escape path in doplayerhits(), doplayersend() and doplayers().
| 17 | Death-sequence vertical velocity | Decreases over time and moves the dead player actor downward.
| 18 | Shield sprite/entity | Created in loadplayers(). |
| 19 | Shield alpha factor | Oscillates between configured bounds to animate shield visibility. |
| 20 | Shield alpha direction | Positive/negative direction used to oscillate Field19. |
| 21 | Bomb count | Initialized from player setup; consumed by firebomb(). |
| 22 | Death animation frame/variant index | Randomized at death and passed to the hamster-death animation texture. |
| 23–24 | Combo count and timer | Used to award combo bonus. |
| 25–26 | Combo target/position coordinates | Used for combo display positioning. |
| 27 | Beam Laser charge/cooldown timer | Set when the Beam Laser is fired; resets its mount firing timer while positive. |
| 28–32 | Temporary weapon upgrade counters | Added to the corresponding setup levels in order: Chain Cannon, Homing Missiles, Beam Laser, Wave Laser, Auto Turret. |
| 33 | Movement trail timer | Counts down while movement particles are emitted. |

Fields 6, 8, 15–17, 20, 22, 27 and 33 were resolved in the subsequent hit/death/movement/HUD trace. Fields not listed above remain unmapped until their full read/write call sites are traced. The source uses positional fields heavily; do not assume unlisted fields are empty or unimportant.

## 7. Defects and data-integrity risks

### Confirmed deletion-target bug

`deleteprofile()` clears most fields using `profiletodelete`, but clears Field14 through `profiles(currentprofile)` instead. If the deleted slot differs from the active profile, the wrong profile's player-count setting is reset.

The function also does not clear Field14 on the target record and does not clear target Fields15 or 16. This can leave stale values in a supposedly deleted slot. This is a source-level defect inferred directly from the assignments; it has not been patched.

### Profile slot UI versus storage capacity

The profile array and persistence file support 10 slots, but the inspected menu event handlers expose play/delete actions only for slots 1–6. The remaining four records may be reserved or inaccessible through this menu version.

### Save format fragility

The file is positional and unversioned. A future field insertion or type change would silently break compatibility unless a migration layer is added.

### Level progression boundary

The win handler increments Field1 only when it equals the completed level and is below 29. This is a hard-coded campaign progression ceiling and should be checked against the actual campaign data and the original Mighty Rodent executable.

## 8. Godot data model

Recommended normalized types:

- `PlayerProfile`: stable profile ID, name, unlocked level, currency, options, selected difficulty, player count, upgrade levels.
- `UpgradeDefinition`: stable upgrade ID, display name, max level, price curve, description and effect mapping.
- `PlayerLoadout`: resolved upgrade levels for one player at level start.
- `PlayerRuntimeState`: lives, bombs, shield state, in-level reward accumulator, combo state and input configuration.
- `WeaponMountDefinition`: mount slot, projectile definition ID, mount transform, fire interval multiplier and aiming policy.

Use named properties and a versioned JSON/ConfigFile save format. Preserve the legacy field mapping only in migration/compatibility code.

## 9. Remaining verification

1. Trace all 34 playertype fields and all 10 playersetup fields, including death/re-entry and special-mode paths.
2. Resolve profile Field15 / playersetup Field9.
3. Check whether profile slots 7–10 are intentionally hidden or simply missing menu entries.
4. Trace the four unused upgradetypes fields and any profile fields only read by secondary menus.
5. Compare upgrade prices, starting values and weapon behavior with the original Mighty Rodent executable.
