# Documentation index

This directory contains the source audit, reconstruction plan and machine-readable extracts for the Mighty Rodent / Gusto-modified Runaway Robbit reconstruction.

**Policy:** documentation and extracted data only. The original Blitz3D source files are not modified by these audit commits.

## Project orientation

- [Architecture](ARCHITECTURE.md) — source program structure and subsystem boundaries.
- [Rebuild plan](REBUILD_PLAN.md) — staged migration strategy.
- [Code audit](CODE_AUDIT.md) — initial source inventory and audit summary.
- [Consistency and Godot readiness](CONSISTENCY_AND_GODOT_READINESS_AUDIT.md) — cross-document validation, open risks and readiness verdict.

## Runtime systems

- [Gameplay deep audit](DEEP_AUDIT_GAMEPLAY.md) — game loop, actor/projectile flow and runtime dependencies.
- [Physics and collision](PHYSICS_AND_COLLISION.md) — movement, collision geometry, faction filtering and damage.
- [Multiplayer and input](MULTIPLAYER_AND_INPUT.md) — local co-op, input modes and crosshair control.
- [UI, audio and menus](UI_AUDIO_AND_MENUS.md) — menu framework, HUD, audio routing and event dispatch.
- [Configuration and settings](CONFIGURATION_AND_SETTINGS.md) — startup, build flags, resolution, timing and settings.
- [Asset and animation catalog](ASSET_AND_ANIMATION_CATALOG.md) — visual resources, animation and backgrounds.

## Game content and progression

- [Content and progression deep audit](DEEP_AUDIT_CONTENT_AND_PROGRESSION.md) — weapons, bonuses, profiles, score and campaign progression.
- [Enemy and boss catalog](ENEMY_AND_BOSS_CATALOG.md) — configured enemy archetypes and boss composition.
- [Level data catalog](LEVEL_DATA_CATALOG.md) — level templates, event fields and campaign generation.

## Field-level and structured extraction

- [Type field map](TYPE_FIELD_MAP.md) — legacy type field cross-reference.
- [Actor runtime audit](ACTOR_RUNTIME_AUDIT.md) — actor pool, component hierarchy, update order, combat and death lifecycle.
- [Entity definitions audit](ENTITY_DEFINITIONS_AUDIT.md) — actor archetypes, weapon mounts and projectile-kind definitions.
- [Projectile runtime audit](PROJECTILE_RUNTIME_AUDIT.md) — all 72 typeweaponshot fields, creation, update, collision, homing and pooling.
- [Profile, player and upgrade audit](PROFILE_PLAYER_UPGRADE_AUDIT.md) — profile persistence, all 34 player runtime fields, upgrade prices and weapon progression.

### Machine-readable data

- [Actor archetypes and projectile kinds](data/entity_definitions.extracted.json) — raw extracted source literals, IDs and source line references.
- [Profiles, player fields and upgrades](data/profile_player_upgrades.extracted.json) — normalized profile/player mapping, shop data and weapon progression.

## Reading cautions

1. These documents describe the checked-in Gusto-modified source, not a verified byte-for-byte or behaviorally identical Mighty Rodent baseline.
2. A field name marked provisional or unresolved must not be treated as an established game mechanic.
3. Allocated array capacity is not the same as the number of populated or campaign-used definitions.
4. Machine-readable extracts preserve source literals where indicated; they are not runtime dumps.
5. Behavioral parity still requires controlled tests against the original Mighty Rodent executable.
