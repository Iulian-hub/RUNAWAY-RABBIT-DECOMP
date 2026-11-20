# Startup, Splash Screen and Main Menu

## Purpose

This document describes the first Godot implementation of the startup presentation and menu shell. It distinguishes source-derived behavior from the current reconstruction choices.

## Source behavior inspected

The checked-in Blitz3D source performs the following sequence in `startgame()`:

1. Sets the initial game mode, level and event.
2. Initializes the camera and GUI system.
3. Loads the fullscreen presentation system and shows `gfx/jaggedlogo.jpg`.
4. Loads menu and HUD resources.
5. Loads and loops `music/menu.ogg`.
6. Fades away from the logo presentation and shows `gfx/mainpic.jpg`.
7. Loads gameplay resources.
8. Enters the menu background and changes to menu index 2.
9. If no profile is selected, it opens menu index 5 (name/profile creation).

The original menu data is defined in `Functions/EntryPoint.bb`. Menu index 2 is the main menu. It includes Play Game, Exit Game, Change Player, Instructions, High Scores and Options. The modified source also contains marketing entries for the Runaway Rabbit distribution; these are not carried into the Mighty Rodent menu.

The source's menu system is based on custom GUI entities and string event identifiers. The Godot implementation uses `Control` nodes, reusable scenes, signals and named methods instead.

## Godot implementation

- `ui/boot.tscn` is now the configured application main scene.
- `scripts/ui/boot.gd` routes Splash -> Main Menu -> existing gameplay prototype.
- `ui/splash_screen.tscn` and `scripts/ui/splash_screen.gd` implement a timed, skippable title presentation with fade-in/fade-out.
- `ui/main_menu.tscn` and `scripts/ui/main_menu.gd` implement the menu panel and its pages, using source `gfx/mainpic.jpg`, `gfx/instructions.jpg`, `gfx/menu/menuborder.png` and `gfx/menu/mouse.png`.
- Menu navigation uses typed signals rather than string dispatch.
- Music and sound preference toggles are persisted to `user://settings.cfg`.

## Current menu behavior

- Start Game: enters the current gameplay prototype.
- Instructions: shows the current prototype controls.
- High Scores: displays an explicit placeholder until the original score format is ported.
- Options: persists music and sound preference flags. Audio playback integration is not implemented yet.
- Exit Game: closes the application.

## Imported source artwork and fidelity

The following source assets are copied without modification into `assets/original_ui/`: `jaggedlogo.jpg`, `mainpic.jpg`, `instructions.jpg`, `titlepic1.png`, `titlepic2.png`, the menu texture set (`menutex.bmp`, `menuborder.png`, `menuborderspecial.png`, `menucorner.png`, `mouse.png`) and both bitmap font sheets. The Godot splash now displays `jaggedlogo.jpg`; the menu uses `mainpic.jpg` as its background, `menuborder.png` for its panel frame, `mouse.png` as the cursor and `instructions.jpg` on the instructions page.

These files are the artwork actually referenced by the checked-in Blitz3D source. Their use is source-faithful, but the repository is a Gusto-modified Runaway Rabbit build, so exact parity with the original Mighty Rodent executable still needs visual comparison. The imported assets are not recolored or redrawn.

The original profile selection flow is not implemented yet. It should be added before treating Start Game as the final game flow. The current gameplay prototype is launched directly so the UI can be exercised end-to-end.

## Next UI milestones

1. Import and identify the correct Mighty Rodent logo/title artwork.
2. Reconstruct the title presentation timing and transition after comparing it with the original executable.
3. Implement player/profile selection and profile creation.
4. Implement Options with actual audio and display settings.
5. Replace the high-score placeholder with the original score persistence model.
6. Add pause menu and in-game HUD as separate scenes.
