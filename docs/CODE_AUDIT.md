# Audit tehnic – Runaway Robbit / Mighty Rodent reconstruction

**Repository:** `Iulian-hub/RUNAWAY-RABBIT-DECOMP`  
**Branch audited:** `main`  
**Commit audited:** `88692e4ad619a87001b9a3b9974faf2bff97958e`  
**Audit date:** 2026-11-20  
**Purpose:** understand the decompiled Blitz3D codebase as a behavioral/technical reference for a clean-room-style rebuild.

## 1. Executive summary

The project is not a monolithic source file. It is a modular Blitz3D application with 196 included function modules and 34 user-defined types. Its runtime is nevertheless strongly coupled through approximately 250 global declarations and shared fixed-size arrays.

The original rendering approach is a 2D game implemented through Blitz3D's 3D scene graph. Sprites, camera transforms, entity ordering, texture transforms and a custom vertex-based sprite/particle renderer are central to the implementation. A modern 2D engine can reproduce the game without retaining this 2D-in-3D architecture.

The code is useful as a behavioral reference, but it is not a good foundation for incremental modernization: decompiler-generated names (`FieldNN`, `localNN`), implicit global dependencies, large initialization/configuration routines and fixed-capacity object pools make safe changes difficult.

**Recommendation:** preserve this repository as a read-only reference. Rebuild in Godot 4 with typed GDScript, using the existing source to extract rules and the installed original game to validate behavior. Do not translate the decompiled code line-for-line.

## 2. Scope and confidence

### Inventory verified from the Git tree and Main.bb

| Item | Count |
|---|---:|
| BlitzBasic source files (`.bb`) | 232 |
| Function modules included from `Functions/` | 196 |
| Type definitions included from `Types/` | 34 |
| Other files under `Functions/` | 2 backup copies of loadmenu |
| Global declarations in `Globals.bb` | 250 |
| Graphics assets | 163 |
| Music files | 3 |
| Sound-effect files | 23 |

All 196 `.bb` files under `Functions/` are included exactly once by `Main.bb`. All 34 files under `Types/` are also included. The repository contains a 410 KB `Functions/EntryPoint.bb`, which mixes startup, configuration and large blocks of game data.

### Source review performed

Detailed inspection focused on:
- startup and main loop: `EntryPoint.bb`, `startgame.bb`, `loadthings.bb`, `gameloop.bb`, `doload.bb`;
- actors and players: `makeactor.bb`, `doactors.bb`, `doactorai.bb`, `doactorendai.bb`, `doactormovement.bb`, `updateactor.bb`, `doplayermovement.bb`, `doplayerweapons.bb`, `doplayerhits.bb`, `updateplayer.bb`;
- enemies, bosses and levels: `controlenemies.bb`, `doenemiesend.bb`, `makeenemy.bb`, `makeboss.bb`, `dobosses.bb`, `dobossai.bb`, `dolevels.bb`, `startlevel.bb`, `makemorelevels.bb`;
- weapons and collisions: `makeweaponshot.bb`, `doweaponshots.bb`, `doweaponcollision.bb`, `doactorcollisions.bb`, `findtarget.bb`, `firebomb.bb`;
- resources, UI and persistence: `loadactors.bb`, `loadplayers.bb`, `loadanimloops.bb`, `loadbackgrounds.bb`, `menuevent.bb`, `loadprofiles.bb`, `saveprofiles.bb`, `loadres.bb`, `loadscores.bb`;
- all 34 type declarations and the main include list.

This is a static source audit, not a full dynamic test or a line-by-line review of every function. The original executable was not launched by the audit tooling. Any field mapping marked provisional must be verified against additional reads/writes and in-game behavior.

## 3. Runtime architecture

### Startup sequence

1. `EntryPoint()` creates profile/high-score records, reads settings, chooses the build configuration and resolution, initializes graphics/audio/timers, allocates arrays and initializes input.
2. `startgame()` initializes the camera and GUI, loads menus, starts menu music and loads gameplay resources.
3. `loadthings()` calls `makemorelevels()`, then loads bonuses, sounds, animation loops, actors, projectiles, explosions, particles, shot flashes, backgrounds and players.
4. `gameloop()` runs continuously until the process is terminated.

### Main loop order

The active gameplay branch in `gameloop()` updates:
- backgrounds, mouse, HUD and demo crosshair;
- actors, bonuses and GUI;
- flashes, animation state and camera;
- shot flashes, weapon shots, sound volumes and explosions;
- level progression, scripted enemy spawning, background enemies and bosses;
- demo timers.

After the gameplay branch, it updates input force, menu logic and frame timing, then calls `RenderWorld` and `Flip`.

The order is behaviorally significant. For example, projectiles are updated after actors in the current frame. A rebuild should explicitly define update phases and preserve this ordering until regression testing proves a change is safe.

### Actor update pipeline

`doactors()` iterates over a fixed pool of 20 actor slots. Active, visible actors pass through:
1. `doactoranimations()`
2. `doactorweapons()`
3. `doactorcollisions()`
4. `doactordeathcheck()`
5. `doactormovement()`
6. `doactorai()`
7. `updateactor()`
8. `doactorendai()`

The AI split is easy to misread:
- `doactorai()` calls `doplayers()` and `doenemies()`;
- `doenemies()` is currently an empty stub;
- `doactorendai()` calls `doenemiesend()`, which contains substantial enemy-specific behavior;
- `controlenemies()` separately handles timed/scripted enemy spawning.

Therefore, the empty `doenemies()` function is not evidence that enemy AI is absent.

## 4. Main technical findings

### High priority – fixed-pool exhaustion behavior

**Projectile pool:** `makeweaponshot()` searches the 100-slot `weaponshots` array for an inactive slot. If no free slot is found, it assigns slot 1 anyway. If slot 1 is still active, the new shot can overwrite an existing projectile's state. This is a confirmed unsafe overflow policy.

**Actor pool:** `makeactor()` searches the 20-slot actor array. If no free slot exists, it returns 0 unless its final argument is 1; in that case it falls back to slot 1. `makeenemy()` forwards its final argument to `makeactor()`, and enemy respawn logic in `doenemiesend()` calls `makeenemy(..., 1)`. This creates a conditional risk of replacing an active actor when the pool is exhausted. Confirm intended semantics of this flag before changing the original.

**Recommendation:** in the rebuild, use managed collections or explicit pools. Pool exhaustion must return a failure/result, log diagnostics and never silently overwrite an active player, enemy or projectile.

### High priority – implicit global coupling

`Globals.bb` declares 250 globals. Gameplay functions read and mutate shared state such as current level/event, player count, bomb timer, score, sound state, frame delta, actor arrays and weapon arrays without explicit dependencies.

**Impact:** a function's behavior cannot be understood from its parameters alone; changes can create distant side effects.

**Recommendation:** group state into explicit objects/services: `GameState`, `LevelDirector`, `PlayerState`, `AudioManager`, `ActorRegistry`, `ProjectileManager` and `Settings`. Keep scene nodes responsible for presentation, not global game rules.

### High priority – decompiler-generated data model

The 34 type files expose generic names such as `Field0` through `Field80`. Array fields make the structures even harder to interpret. `actortype` has 81 fields, `typeweaponshot` has 72, `actortypetype` has 51 and `menu` has 61.

**Impact:** incorrect field assumptions can silently change unrelated systems.

**Recommendation:** maintain a field dictionary with evidence and confidence. Do not mass-rename fields in the legacy source. In the rebuild, create new named data models based on confirmed behavior.

### Medium priority – initialization/configuration concentration

`EntryPoint.bb` is approximately 410 KB. It contains startup setup, build configuration, arrays, player/actor defaults, weapon-shot definitions and other static game configuration.

**Impact:** data changes and startup changes are interleaved; review and testing are difficult.

**Recommendation:** move static definitions to data resources (Godot Resources or JSON) and keep startup code focused on initialization.

### Medium priority – persistence format has no visible schema version

`loadprofiles()` and `saveprofiles()` serialize positional fields directly into `profiles.dat`. The loader validates the active profile index and applies one fallback to Field15, but does not visibly validate file length, schema version or every field. Scores use a similar positional format.

**Impact:** truncated, corrupted or older files can cause invalid reads or incompatible state after updates.

**Recommendation:** use versioned save data, validation, defaults for missing values, migration functions and write-to-temporary-then-replace semantics. Preserve legacy save import only if compatibility is desired.

### Medium priority – stale modding documentation / debug-key mismatch

`MODDING_NOTES.txt` says debug hotkeys are enabled with the `dbug` command-line argument and describes Ctrl-based shortcuts. In the reviewed `loopstuff.bb`, the cash/level shortcuts are gated by left/right Shift (`$2A` / `$36`), then C (`$2E`) or L (`$26`). That function does not check `debugmode` before applying the shortcuts.

**Impact:** the notes do not match the current implementation, and the shortcuts appear available without the documented debug-mode gate.

**Recommendation:** confirm intended behavior, then either update the notes or gate the shortcuts behind an explicit debug setting. Do not silently change the legacy game during the reconstruction audit.

### Medium priority – dead code / no-op modules

`doanimloops()` returns before an empty loop, so the loop is unreachable. `doenemies()`, `doparticles()` and `loadweaponshots()` are also empty in the reviewed source. Some no-op functions are intentional because work is performed elsewhere (enemy behavior is in `doenemiesend()`; sprite particles are updated by `updatesprites()`).

**Recommendation:** classify each no-op as intentional delegation, obsolete code or incomplete functionality before removing it. Avoid treating names alone as evidence.

### Medium priority – legacy 2D-in-3D rendering

`loadcamera()`, `init2din3d()`, `loadactorgraphics()`, `updateactor()` and `updatesprites()` show that the game uses Blitz3D camera/entity/texture operations to present a 2D playfield. `updatesprites()` manually updates vertices for sprite/particle quads.

**Impact:** rendering, coordinate conversion, depth ordering, effects and collision geometry are coupled to the old engine.

**Recommendation:** rebuild with Godot's native 2D nodes, CanvasItem rendering, AnimationPlayer/Sprite2D and Area2D/CollisionShape2D. Reproduce ordering, transforms, blending and effect timing deliberately.

## 5. What is already well structured

- Function responsibilities are separated into modules rather than one giant gameplay loop.
- Actor creation is data-driven through `actortypes` and `makeactor()`.
- Weapon-shot definitions are separated from active projectile instances.
- Level progression is event-based and data-driven through `levelevent`.
- Bosses are represented separately from ordinary actors and can consist of multiple actor parts.
- Player input modes, turret aiming and weapon firing are separate routines.
- The asset folders are separated by graphics, music and sound.

These are good conceptual boundaries to preserve in the new architecture.

## 6. Testing limitations

No Blitz3D compilation or runtime test was performed as part of this static audit. Binary assets were counted from the Git tree but not decoded or checked for licensing/redistribution rights. The original game is available to the project owner and should be used as the behavioral oracle for frame-by-frame comparisons.

## 7. Immediate next actions

1. Complete the field map for actor, player, level-event, actor-type and weapon-shot structures.
2. Inventory every actor type, enemy type, weapon and level event from `EntryPoint.bb`.
3. Record original-game behavior for movement, turret angle, firing cadence, damage, powerups, level transitions and boss phases.
4. Design the Godot data model and first playable vertical slice.
5. Add regression tests before porting later systems.
