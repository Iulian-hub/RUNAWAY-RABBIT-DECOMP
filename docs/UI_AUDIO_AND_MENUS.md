# UI, Audio and Menu System Audit

> This document describes the checked-in modified source. The Gusto additions are called out separately where visible. Menu numbering is internal and should not be treated as a player-facing label without inspecting the corresponding menu data.

## 1. Main application flow

`startgame()` initializes the game presentation in stages:
1. Sets the game mode and initial level/event.
2. Creates camera and GUI infrastructure.
3. Loads fullscreen presentation, menus and GUI.
4. Loads menu music and shows the logo/title presentation.
5. Loads gameplay resources via `loadthings()`.
6. Starts the menu background and enters `gameloop()`.

The main loop updates backgrounds, mouse, HUD, actors, bonuses, GUI, flashes, camera, projectiles, sound volume, explosions, level events, enemy control, background enemies, boss logic and demo timers. It then updates input/menu state, frame speed factor, renders the world and flips the display.

## 2. Menu architecture

The source stores up to 50 menu records (indices 1..50) and up to 100 interactive items per menu. `loadmenu()` creates menu panel sprites, text objects, button sprites and decorative geometry from the menu data arrays.

`domenu()`:
- shows/hides the menu cursor depending on current menu;
- maps physical mouse coordinates to a 640×480 logical interface;
- supports keyboard/joystick cursor movement when mouse movement is idle;
- tests hover state for text, text/button and sprite controls;
- triggers the menu event string on activation;
- maintains radio-button selection state;
- plays a hover sound when the active item changes.

`menuevent()` dispatches string-based actions for navigation, starting modes/levels, shop upgrades, scores, profile flows, demo/full-version prompts and other menu operations.

## 3. Menu and GUI rendering

The interface is not a conventional retained UI toolkit. It creates 2D GUI objects as entities/meshes and positions them through helper routines:
- `gui_createsprite()`, `gui_createtext()`;
- `gui_positionobject()`, `gui_scaleobject()`;
- `gui_setstring()`, `gui_setfontheight()`;
- `gui_showgroup()`, `gui_hidegroup()`;
- `gui_objectorder()`, `gui_mouseover()`.

`dongui()` updates score fly-ups and alpha-fades transient GUI quads. The menu and in-game HUD share the GUI helper layer but have separate update routines.

## 4. HUD

`dohud()` updates completion percentage, optional FPS/debug text, demo timer and per-player HUD elements. Each player has separate lives, score, shields, bombs and beam-laser charge widgets. Shield/bomb counts are rendered by scaling texture regions, and beam charge is represented by a vertical fill bar whose color changes when ready.

## 5. Audio resources

`loadsounds()` loads three music resources:
- `music/main.ogg` — gameplay/main track, volume 0.35, looped;
- `music/boss.ogg` — boss track, volume 0.5, looped;
- `music/menu.ogg` — loaded in `startgame()`, volume 0.45, looped.

Sound effects are loaded by symbolic variables. The visible mapping includes:
- `cashbonus.ogg` — purchase/cash bonus;
- `speed.ogg` — speed-related effect;
- `cantafford.ogg` — insufficient funds;
- `machinegun.ogg`, `littlebang.ogg`, `autogun.ogg`, `plaser1.ogg`, `plaser3.ogg`, `plaser4.ogg`, `missile.ogg`, `bomb2.ogg`, `beamphasershort.ogg`, `beam.ogg` — weapon effects;
- `explosion.ogg`, `explosion2.ogg`, `explosionlong.ogg` — explosion effects;
- `shields.ogg`, `gameover2.ogg`, `bonus.ogg`, `combo.ogg`, `impact2.ogg`, `boing1.ogg`, `rumblehit.ogg` — gameplay events.

The source applies per-sound volume multipliers at load time. The exact perceptual loudness depends on the audio engine and must be tuned by listening tests in the rebuilt game.

## 6. Music selection and sound channel management

`startmusic(track_id)` avoids restarting the current track when the requested track is already playing. Otherwise it stops the previous music channel and starts the requested track if music is enabled.

`dosoundvolumes()` manages a pool of game sound records and distributes a configured volume across active channels in each group. The code also has sound/music enable flags and override helpers. A Godot port should use AudioStreamPlayers grouped into buses (Music, SFX, UI) while preserving per-event volume levels and loop semantics.

## 7. Settings and persistence

`loadres()` reads one integer from `res.dat`; valid values are 1..8. Invalid or missing values trigger `guessres()` and a save. `saveres()` writes the selected resolution mode back as a single integer.

The source also contains separate profile persistence and score persistence (documented in the content/progression audit). Audio enable flags, resolution and gameplay preferences should be represented as a versioned settings object in the rebuild rather than scattered globals.

## 8. Gusto-specific UI changes

The checked-in `loadmenu.bb` includes `inject_options()`, which expands menu 6, moves the DONE/Back control and injects three controls:
- Auto-fire toggle;
- Crosshair visibility toggle;
- Crosshair color cycling.

These additions are specific to the modified source and must be kept separate from the baseline reconstruction until compared with the original Mighty Rodent executable.

## 9. Godot 4 recommendations

1. Rebuild menus as Control scenes with explicit screen states and typed signals; do not reproduce the entity-mesh GUI implementation.
2. Use a central UI state/router for menu transitions, with named states instead of numeric menu IDs.
3. Keep HUD as a separate scene driven by player/game state.
4. Use AudioServer buses and named AudioStream resources; keep music transitions centralized.
5. Keep menu event identifiers as enums or typed actions, not free-form strings.
6. Separate demo/shop/profile flows from gameplay scene logic.
7. Persist display/audio/input options in a versioned settings file.

## 10. Verification backlog

- Complete mapping of all menu indices to labels and all menu event strings.
- Full mapping of every sound variable to the call sites that play it.
- Verify whether `music/main.ogg` or another track is the default for each level and mode.
- Enumerate all options and distinguish original options from Gusto additions.
- Compare menu timings, audio levels, cursor behavior and HUD layout against the original executable.
