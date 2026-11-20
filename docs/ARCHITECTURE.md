# Legacy architecture map

This document describes the current Blitz3D implementation. It is an interpretation of the decompiled source, not a claim that original developer names or comments have been recovered.

## 1. Repository layout

- `Main.bb`: include list, global arrays and call to `EntryPoint()`.
- `Globals.bb`: 250 global declarations.
- `Functions/`: 196 included BlitzBasic modules plus two backup copies of `loadmenu`.
- `Types/`: 34 type definitions.
- `gfx/`: 163 graphics assets.
- `music/`: 3 OGG tracks.
- `sound/`: 23 OGG sound effects.
- `MODDING_NOTES.txt`: existing notes for the modified Runaway Robbit build.

## 2. Subsystem map

| Subsystem | Main modules | Responsibility |
|---|---|---|
| Startup/build | `EntryPoint`, `startgame`, `loadthings`, `doload` | Build flags, graphics, arrays, resource initialization |
| Main loop/timing | `gameloop`, `loopstuff`, `setspeedfactor`, `framelimitinit` | Per-frame updates, input, render and frame delta |
| Camera/2D projection | `loadcamera`, `init2din3d`, `docamera`, `to3d` | Camera, projection and coordinate conversion |
| Actors | `makeactor`, `doactors`, `doactormovement`, `updateactor` | Actor lifecycle, movement and visual transforms |
| Player | `loadplayers`, `doplayers`, `doplayermovement`, `docrosshair`, `doplayerweapons`, `doplayerhits`, `updateplayer` | Player controls, aiming, weapons, shields, lives and score |
| Enemy spawning/AI | `controlenemies`, `makeenemy`, `doenemiesend`, `getenemiestype` | Scripted spawning, enemy behavior, respawn and counts |
| Bosses | `makeboss`, `dobosses`, `dobossai`, `setbossevent` | Multi-part boss creation, movement, health and phases |
| Weapons | `makeweaponshot`, `doweaponshots`, `doweaponcollision`, `firebomb`, `findtarget` | Projectile lifecycle, targeting, collision and bomb |
| Collision | `anglecollisionxy`, `fastcollisionxy`, `doactorcollisions` | Rotated rectangle and fast axis-aligned collision checks |
| Levels | `makemorelevels`, `startlevel`, `dolevels`, `copylevelevent` | Event progression, enemy waves, boss events and completion |
| Effects | `makeparticle`, `updatesprites`, `makeexplosion`, `doexplosions`, `makeshotflash` | Particles, explosions, flashes and sprite effects |
| UI/menus | `loadmenu`, `loadgui`, `domenu`, `menuevent`, `dospecialmenucode`, `dohud` | Menus, shop, profiles, HUD and dynamic text |
| Persistence | `loadprofiles`, `saveprofiles`, `loadscores`, `savescores`, `loadres`, `saveres` | Binary local profile, score and resolution files |
| Audio | `loadsounds`, `startmusic`, `dosoundvolumes`, `playsoundoveride` | Music, sound effects and volume limiting |

## 3. Startup and resource lifecycle

```text
Main.bb
  ├── Include Globals.bb
  ├── Include 196 Functions/*.bb modules
  ├── Include 34 Types/*.bb declarations
  ├── Declare shared arrays
  └── EntryPoint()
        ├── Create profile/high-score objects
        ├── Load scores and resolution
        ├── Initialize Graphics3D and timers
        ├── Allocate actor, weapon, player, level and effect arrays
        └── startgame()
              ├── Camera and GUI
              ├── Menus and menu music
              ├── loadthings()
              │     ├── Generate/prepare level data
              │     ├── Bonuses
              │     ├── Sounds
              │     ├── Animation textures
              │     ├── Actors
              │     ├── Explosions / particles / flashes
              │     ├── Backgrounds
              │     └── Players
              └── gameloop()
```

## 4. Main frame pipeline

The order below is taken from `gameloop.bb`:

1. Update background and mouse.
2. Update HUD and demo crosshair.
3. Update actors and bonuses.
4. Update GUI, white flash and blackout.
5. Update animation loop system and camera.
6. Update shot flashes and weapon shots.
7. Update sound volumes and explosions.
8. Advance levels and scripted enemy spawns.
9. Update background enemies and bosses.
10. Update demo timer.
11. Update keyboard force, menu and frame timing.
12. Render the 3D world and flip the display.

The loop is an unconditional `Repeat ... Forever`. The reviewed source exits through explicit process termination in input handling rather than a clean state-driven loop termination.

## 5. Actor lifecycle and composition

The active actor pool has 20 slots. Each slot has a body entity, decal entity and up to 10 turret entities. `makeactor()` initializes the actor from an actor-type definition, including its visual, faction, movement, collision size, health and weapon mount configuration.

Actor relationships are represented by indices:
- `actortype.Field2` is assigned the index of a child actor when the parent creates one.
- `actortype.Field3` is assigned the parent actor index on the child.
- This relationship is used by death/cleanup logic.

The actor update pipeline is documented in `CODE_AUDIT.md`. Actors are hidden and moved far outside the playfield when inactive/disabled in several routines. The rebuild should use explicit active state and node lifecycle instead of sentinel coordinates.

## 6. Player and weapon model

There are two player slots. A player record points to an actor slot and a crosshair entity. Player control mode selects mouse/keyboard/controller behavior. The player record also tracks position, movement, score, combo, shields, lives, bombs and weapon upgrade levels.

Weapons are configured as weapon-shot kinds, then copied into active weapon-shot instances when fired. Actors have up to 10 weapon/turret mounts. Mount data includes weapon kind, firing interval, mount offsets, turret rotation and target/faction filters.

The player's turret is aimed toward the crosshair. The angle is wrapped and constrained to two allowed angular sectors. The original implementation should be measured before changing the angular convention or coordinate system.

## 7. Enemy and boss model

Enemy spawning has two distinct paths:
- `controlenemies()` evaluates the current level event, concurrent-enemy limit, spawn intervals and allowed spawn sides.
- `makeenemy()` creates an actor from an enemy type and initializes AI state, health and speed scaling.

Enemy behavior is executed by `doenemiesend()`, not by the empty `doenemies()` stub. The end-AI function contains type-specific branches and custom state arrays.

Bosses are separate `bosstype` records. A boss can be composed of multiple actors/parts. `dobosses()` sums part health, updates the boss bar, moves the boss and calls `dobossai()` for type-specific phases. When all parts are destroyed, it advances the boss-death count.

## 8. Level event model

Level data is stored in a two-dimensional array of `levelevent` records. `dolevels()` advances the event index when its time condition and boss-kill condition are met. It can:
- change music;
- show event text;
- change menu/state;
- spawn up to five ordinary enemies;
- spawn a boss;
- spawn up to six background enemies;
- set the win condition.

`controlenemies()` also uses event-level enemy type, spawn cadence and concurrent limits to create ongoing waves. `makemorelevels()` copies and synthesizes level/event records. The generated schedule should be extracted into external data in the rebuild.

## 9. Rendering architecture: 2D over 3D

The game uses a Blitz3D camera and 3D entities as a 2D rendering surface:
- `loadcamera()` creates and positions the camera.
- `init2din3d()` creates a plane, uses camera picking and derives the 2D projection scale.
- Textures are rotated/scaled to correct sprite orientation.
- Entity order is used to control layering.
- `updatesprites()` transforms sprite corners and updates mesh vertex positions/colors.

This is an implementation detail, not a gameplay requirement. Godot 2D should use a fixed logical viewport and native 2D coordinates, with explicit z-index/layer rules.

## 10. Persistence

- `profiles.dat`: positional binary profile records.
- `scores.dat`: positional high-score records.
- `res.dat`: selected display-mode integer.

The format is not visibly versioned. A rebuilt game should use a versioned schema and validate every field before accepting saved data.
