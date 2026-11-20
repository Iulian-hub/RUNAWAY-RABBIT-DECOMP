# Godot reconstruction prototype

This folder contains the Godot 4 project for the Mighty Rodent / Runaway Rabbit reconstruction.

## Current milestone

A first playable technical slice:
- horizontal player positioning follows the mouse, with A/D keyboard adjustment;
- mouse aiming and continuous primary fire;
- basic projectile lifecycle;
- enemies spawn from the right and travel across the playfield;
- projectile/enemy and enemy/player hit checks;
- hull, score, bomb counters, and a simple game-over state.

All visuals are temporary vector placeholders. No original game assets or final balance values have been integrated yet.

## Controls

- Mouse: aim and position the player horizontally
- A / D: horizontal movement adjustment
- Left mouse: fire
- Right mouse: bomb (clears current enemies)

## Fidelity status

This is a prototype, not a faithful port. The original source uses a frame-scaled movement model, actor and projectile pools, weapon archetype data, faction-aware collision, shields, lives, upgrades, and scripted level progression. Those systems will replace the placeholders in later milestones. Gusto-only controls are intentionally excluded from the baseline design.


## Startup and interface

The configured main scene is now `ui/boot.tscn`. Startup flow:

1. Splash/title presentation (skippable, otherwise advances automatically).
2. Main menu with Start Game, Instructions, High Scores, Options and Exit Game.
3. Existing gameplay prototype.

The menu's Music and Sound Effects toggles are currently saved as preferences only; actual audio playback integration is a later task. High Scores is an explicit placeholder, and profile selection is not implemented yet.

See [docs/UI_STARTUP_AND_MENU.md](docs/UI_STARTUP_AND_MENU.md) for the source comparison, architecture and remaining UI milestones.

## Project structure

- `ui/`: boot, splash and main-menu scenes
- `scenes/`: gameplay entities and gameplay scene
- `scripts/ui/`: startup routing and menu presentation
- `scripts/`: gameplay logic
- `project.godot`: project configuration and Input Map

## Structure

- `scenes/`: reusable Godot scenes
- `scripts/`: typed GDScript gameplay logic
- `project.godot`: project configuration and Input Map
