# Mighty Rodent rebuild plan

## 1. Recommended technology

**Godot 4 + typed GDScript** is the recommended target.

Reasons:
- native 2D renderer and scene system;
- simple, readable language with optional static typing;
- integrated input actions, audio, animation, UI and collision tools;
- export pipeline for desktop platforms;
- resources/scenes make data-driven actors, weapons and levels practical.

C# with Godot remains a valid alternative if the project later needs stricter language-level abstractions. Python/Pygame is easier for a small prototype but would require more infrastructure for a full game. GameMaker is also suitable for 2D, but Godot offers a better combination of code-first organization and editor tooling.

## 2. Rebuild principles

- Treat the original executable as the gameplay reference and the decompiled repository as a source of implementation clues.
- Preserve mechanics before improving or redesigning them.
- Do not port decompiler artifacts such as `FieldNN`, `localNN`, giant global arrays or fixed slot numbers.
- Keep gameplay rules separate from scene presentation.
- Use typed properties, enums, constants and small functions.
- Put weapon, enemy and level definitions in data resources.
- Make update order and time units explicit.
- Add tests for deterministic calculations and regression tests for gameplay sequences.
- Keep a reference note for every intentional difference from the original.

## 3. Proposed directory structure

```text
MightyRodent/
├── project.godot
├── scenes/
│   ├── app/
│   ├── menus/
│   ├── gameplay/
│   ├── actors/
│   ├── player/
│   ├── enemies/
│   ├── bosses/
│   ├── weapons/
│   ├── effects/
│   └── ui/
├── scripts/
│   ├── core/
│   ├── actors/
│   ├── player/
│   ├── enemies/
│   ├── bosses/
│   ├── weapons/
│   ├── levels/
│   ├── effects/
│   ├── audio/
│   ├── input/
│   └── ui/
├── data/
│   ├── actors/
│   ├── enemies/
│   ├── weapons/
│   ├── bosses/
│   └── levels/
├── assets/
│   ├── sprites/
│   ├── backgrounds/
│   ├── audio/
│   ├── fonts/
│   └── ui/
├── tests/
└── docs/
```

## 4. Proposed runtime architecture

### Core
- `GameRoot`: owns the active game state and top-level scene transitions.
- `GameState`: current mode, level, event, score, pause and result state.
- `GameConfig`: resolution, audio, input and accessibility settings.
- `SaveService`: versioned profile/high-score persistence.

### Gameplay
- `LevelDirector`: reads level-event resources and schedules spawns.
- `ActorRegistry`: tracks active actors and safe creation/removal.
- `PlayerController`: translates input into movement/aim/fire intents.
- `EnemyController`: dispatches enemy behavior by enemy archetype/state machine.
- `BossController`: owns boss phases and multi-part health.
- `WeaponController`: handles mount configuration and fire cadence.
- `ProjectileManager`: creates, updates and removes projectiles.
- `DamageResolver`: centralizes damage, faction filtering and kill attribution.
- `EffectsManager`: particles, explosions, flashes and camera shake.
- `AudioManager`: music state, sound effects and volume rules.

### Presentation
Use Godot 2D nodes and scenes:
- `Node2D` for actors and gameplay containers;
- `Sprite2D` / `AnimatedSprite2D` for art;
- `Area2D` and `CollisionShape2D` for hit detection;
- `CanvasLayer` for HUD and menus;
- `AnimationPlayer` for authored visual transitions;
- `AudioStreamPlayer` / `AudioStreamPlayer2D` for audio.

Avoid using physics bodies for every bullet if simple geometric checks or batched projectile updates are more efficient. The original game has a small, bounded playfield; the implementation should be straightforward and deterministic.

## 5. Naming and coding conventions

- Use `snake_case` for variables and functions.
- Use `PascalCase` for classes and scene names.
- Use `UPPER_SNAKE_CASE` for constants.
- Use explicit types for public properties, function parameters and return values.
- Use enums for game modes, factions, weapon types and actor states.
- Avoid generic dictionaries for core state when a typed Resource/class is appropriate.
- Keep functions focused; split long event handlers into named operations.
- Comments explain intent, constraints and non-obvious formulas.
- Never rely on node paths or array indices as undocumented gameplay identity.

Example:

```gdscript
class_name PlayerState
extends Resource

@export var max_health: int = 100
@export var lives: int = 3
@export var bombs: int = 2
@export var move_speed: float = 280.0

var current_health: int = 100
var score: int = 0
var combo_count: int = 0
```

## 6. Data-driven definitions

Prefer typed Resources for reusable definitions:

- `ActorDefinition`: sprite, collision shape, faction, health, movement profile, weapon mounts.
- `WeaponDefinition`: projectile scene, damage, fire interval, speed, lifetime, target mask, special behavior.
- `EnemyDefinition`: actor definition, AI archetype, spawn sides, spawn bounds, difficulty scaling.
- `BossDefinition`: parts, movement profile, phases, health rules and rewards.
- `LevelDefinition`: ordered events, time thresholds, music, scripted spawns and completion conditions.

For level events, preserve the original concepts: trigger time, boss prerequisite, concurrent-enemy limit, scripted spawns, dynamic wave types, music change, announcement and completion marker.

## 7. Fidelity and regression strategy

Before porting each subsystem, capture a baseline from the original game:
- logical resolution and playfield boundaries;
- player movement response and clamp positions;
- crosshair coordinate conversion and turret angle limits;
- firing intervals and projectile trajectories;
- collision dimensions and damage values;
- enemy spawn sides, cadence and concurrent caps;
- boss phases and part health;
- bomb behavior, pickups, upgrade costs and score awards;
- level transition timing, music changes and win/lose conditions.

Use fixed test scenarios and record expected values. Where the original is random, either record a seed if possible or compare ranges/distributions and observable outcomes.

## 8. Implementation phases

1. **Reference capture:** screenshots/video and notes for menus, first level, weapons, enemy types, pickups, boss, death and completion.
2. **Technical prototype:** Godot project, logical viewport, camera, background, sprite ordering, input and audio.
3. **Player vertical slice:** hamster, crosshair, turret, movement, primary fire, bomb and damage.
4. **Combat core:** projectile manager, collision/damage resolver, basic enemies, death effects and score.
5. **Level director:** event resources, spawn rules, waves, background enemies and boss phases.
6. **Meta systems:** menus, profile, upgrades, high scores, settings and save migration.
7. **Parity pass:** second player, special modes, controller, timing, visual effects and edge cases.
8. **Packaging:** desktop exports, clean install test, save migration and rights review for assets.

## 9. Definition of done

A subsystem is considered ported only when:
- behavior is described in documentation;
- its data model has explicit names and types;
- it has no hidden dependency on unrelated global state;
- tests cover its core calculations and boundary conditions;
- its behavior has been compared against the original;
- intentional differences are documented;
- the subsystem can be changed without editing unrelated systems.
