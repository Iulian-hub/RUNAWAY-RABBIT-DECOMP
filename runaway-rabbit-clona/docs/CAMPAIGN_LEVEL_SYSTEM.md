# Campaign level system reconstruction

## Scope and source contract

This document tracks the Godot port of the original Blitz3D level pipeline. The source of truth is the decompiled Gusto-modified codebase, not the temporary Godot spawn prototype.

The original pipeline has three distinct stages:
1. `EntryPoint.bb` initializes 15 campaign templates and auxiliary level slots.
2. `makemorelevels.bb` remaps those templates to campaign seed levels and procedurally fills the gaps.
3. `startlevel.bb` and `dolevels.bb` initialize and execute the event stream.

The verified source-template mapping is: 1→1, 2→2, 3→3, 4→4, 5→5, 6→7, 7→9, 8→11, 9→13, 10→16, 11→19, 12→21, 13→24, 14→27, 15→29.

The original `levelevent` fields are mapped in `docs/LEVEL_DATA_CATALOG.md`. The Godot port uses named properties rather than anonymous `Field0`…`Field30` members.

## Godot runtime model added

- `scripts/levels/level_event.gd`: typed Resource for trigger conditions, background/music changes, scripted spawns, dynamic spawn groups, boss spawns, background actors, announcements and completion.
- `scripts/levels/level_definition.gd`: typed Resource representing one generated campaign level and its ordered event list.
- `scripts/levels/level_director.gd`: event sequencer. It advances the level clock, checks the next event only, gates events on bosses defeated, and delays victory until the active enemy count reaches zero. This mirrors the important control flow in `dolevels.bb`.

These models are the foundation, not a claim that the 29 level datasets or enemy archetypes have already been ported. The event resources will be populated from the original `EntryPoint.bb` data after the source-template extractor and enemy catalog are completed.

## Difficulty selection removed

The Godot profile creation screen no longer asks for EASY/NORMAL/HARD/EXPERT. New profiles use the original default starting upgrade values corresponding to the former NORMAL preset. Existing saved upgrade values are preserved; the obsolete difficulty field is discarded during profile normalization.

The temporary per-run spawn-rate difficulty ramp is also removed. Difficulty progression will come from authored level events, dynamic spawn groups, enemy archetypes and boss encounters, rather than an independent timer multiplier.

## Remaining implementation sequence

1. Export all 15 source templates from `EntryPoint.bb` into named event data and verify event counts/timing against `LEVEL_DATA_CATALOG.md`.
2. Port the exact procedural campaign-generation rules, including boss-template handling and deterministic seed support.
3. Port enemy archetypes and their movement/weapon/health configuration from `enemytypes`, `enemytype`, and actor definitions.
4. Connect event execution to scripted spawns, dynamic spawn groups, boss creation, music, backgrounds and announcements.
5. Replace the current prototype spawn loop with the LevelDirector and validate levels 1, 3, 7, 13, 21 and 29 first.
6. Connect successful level completion to `ProfileManager.complete_level()`.

## Validation status

Static source mapping has been reviewed. Godot runtime validation has not yet been performed for the newly added Resource and director scripts.