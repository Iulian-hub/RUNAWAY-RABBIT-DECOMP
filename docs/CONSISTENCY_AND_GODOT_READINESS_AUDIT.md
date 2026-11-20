# Cross-Document Consistency and Godot Readiness Audit

**Repository:** `Iulian-hub/RUNAWAY-RABBIT-DECOMP`  
**Branch:** `main`  
**Audit date:** 2026-11-20  
**Scope:** consistency review of the current documentation set, targeted source cross-checks, and assessment of readiness for a Godot 4 reconstruction.  
**Change policy:** documentation only; no Blitz3D source or game assets were modified.

## 1. Executive result

The documentation now covers the principal runtime subsystems well enough to begin a structured Godot architecture design. It is **not yet sufficient for a faithful full-game implementation** without a short data-extraction and behavior-verification phase.

The audit identified one concrete field-map ambiguity and several apparent count/terminology differences that are not actual source contradictions once the data structures are distinguished. The field-map ambiguity has been corrected in `TYPE_FIELD_MAP.md`.

## 2. Repository inventory cross-check

The GitHub directory listing confirms:

| Area | Verified inventory | Interpretation |
|---|---:|---|
| `Functions/` | 198 entries | 196 functional modules plus two `loadmenu` backup copies |
| `Types/` | 34 entries | 34 declared type files |
| `gfx/` root | 123 entries | Includes subdirectories and a Windows `thumbs.db` |
| `gfx/background/` | 20 entries | Five environment sets, four assets per set |
| `gfx/bosses/` | 13 entries | 12 boss images plus `thumbs.db` |
| `gfx/fonts/` | 3 entries | Two font atlases plus `thumbs.db` |
| `gfx/menu/` | 8 entries | Seven menu assets plus `thumbs.db` |
| `music/` | 3 entries | main, boss and menu OGG files |
| `sound/` | 23 entries | OGG sound effects |

The earlier figure of 163 graphics is consistent with a recursive count of image assets, not the number of entries in the `gfx/` root directory. The 198 function-directory entries likewise do not mean 198 active modules: two are backup files.

The current documentation set contains **17 principal Markdown audit/planning documents**, including this consistency report, plus two machine-readable extraction files under `docs/data/`. The earlier counts of 11 and 13 were stale.

## 3. Apparent differences resolved

### 3.1 Enemy definitions, enemy IDs and actor archetypes are different counts

The source allocates:
- 30 `enemytypetype` definitions (`enemytypes(1..30)`);
- 20 runtime enemy records (`enemies(1..20)`);
- 20 runtime actor slots (`actors(1..20)`);
- 100 actor archetype definitions (`actortypes(1..100)`).

These are separate capacities and must not be presented as interchangeable catalog sizes. The enemy catalog's ID tables describe configured/used enemy definitions; the allocation size is only capacity.

### 3.2 Five boss definitions versus three runtime boss slots

The source allocates ten boss-definition records (`bosstypes(1..10)`) but only three simultaneous runtime boss controllers (`boss(1..3)`). The inspected campaign configures five boss types (IDs 1–5). These figures describe definition capacity, runtime concurrency and campaign content respectively; they do not conflict.

A boss controller can contain up to ten actor parts. The actor parts are ordinary actor-pool entries, not extra boss-controller slots.

### 3.3 Level event background-enemy fields

The original field map described Fields16–19 too broadly. The source call chain is:

`dolevels() → makebackgroundenemy(Field18, Field19, Field15, Float(Field16), Float(Field17))`

Inside `makebackgroundenemy()`:
- Field18 and Field19 supply spawn X and Y;
- Field15 supplies the background actor's type/variant value;
- Field16 is copied to the selected actor archetype's Fields45/46;
- Field17 is copied to the spawned actor's Field47.

The map in `TYPE_FIELD_MAP.md` has been corrected accordingly. Fields20 and 21 remain separate unresolved event data until all uses are traced.

### 3.4 Profile serialization order

The load and save routines use the same positional order:
`Field4, Field2, Field3, Field1, Field5..Field16`, after the profile name string. The profile field order is unusual but symmetric between reader and writer. Field15 is normalized to 1 when loaded as 0. This is not evidence of a versioned or self-describing format; the binary stream still lacks a visible schema version/checksum/migration layer.

### 3.5 Main and boss music IDs

`loadsounds()` assigns the main track to `musics(1)` and the boss track to `musics(3)`. The menu track is loaded separately during `startgame()`. The documentation should preserve that distinction: menu music is not a third assignment in the same `loadsounds()` block.

## 4. Source-level consistency findings

### 4.1 Main-loop phase ordering

The exact high-level sequence in `gameloop()` is:

1. backgrounds and mouse;
2. HUD and demo crosshair;
3. actors, bonuses, GUI, flashes and animation loops;
4. camera and shot flashes;
5. weapon shots and sound-volume management;
6. explosions;
7. level events and dynamic enemy control;
8. background enemies and level-begin handling;
9. boss updates and demo timer;
10. input force, menu, loop/timing update, render and flip.

This confirms the existing gameplay audit's key dependency: actor updates occur before projectile updates, and level progression/dynamic spawning occur before the boss controller update. A new engine may reorganize rendering, but should initially preserve the simulation ordering as explicit phases.

### 4.2 Damage attribution is a gameplay dependency

Actor Field79 is not merely diagnostic. Projectile collision writes the source actor index there; actor contact collision also writes an attacker index. `actordeath()` then uses that value to award player score, kill count, combo progression and cash/bonus drops. A port that applies damage without carrying source attribution will silently break progression and rewards.

### 4.3 Boss death is component-driven

`makeboss()` creates actor parts from the boss definition. `dobosses()` sums health from surviving parts, resolves configured part-destruction dependencies, pays per-part rewards, and marks a boss defeated only when no active parts remain. This validates the recommendation to implement bosses as controllers over independently damageable parts.

### 4.4 Player Field11 is displayed as currency

The HUD formats player Field11 with a dollar sign, and enemy/boss reward paths increment it. The safest semantic label is **cash/score balance** (the shop currency), not a generic arcade score. The player kill count is tracked separately in Field7. Update future Godot data structures to keep wallet balance, kill count and any separate high-score metric distinct.

## 5. Known documentation gaps and risks

| Priority | Gap | Why it matters | Required follow-up |
|---|---|---|---|
| Critical | Full semantic map of all 81 actor fields; five projectile fields remain unresolved | Gameplay code will otherwise reproduce hidden coupling | Finish actor read/write cross-reference; resolve projectile fields 16, 58, 59, 68 and 70 |
| High | Complete semantic actor archetype and weapon-mount catalogue | The first machine extraction captures 34 explicit archetypes, but player templates and actor-field semantics still need expansion | Expand the JSON with resolved named fields and player templates |
| High | Projectile definition-to-runtime cross-reference | The first JSON extraction contains all 40 kind slots; 19 have explicit assignments and runtime lifecycle is documented, but five fields and kinds 39–40 remain unresolved | Trace remaining fields and determine whether kinds 39–40 are reachable |
| High | Complete player runtime field map | Profile persistence and most player setup/upgrade mappings are documented; several of the 34 runtime player fields remain unresolved | Trace all playertype reads/writes, death/re-entry and special-mode paths |
| High | Exact menu/event catalogue | Necessary to rebuild navigation and shop/profile flows | Enumerate menu IDs, item IDs and event strings |
| High | Complete sound call-site map | Needed to reproduce sound behavior and prevent missing cues | Map every sound variable to callers |
| High | Full background-layer and asset-use map | Prevents incorrect parallax, ordering and missing art | Export background definitions and texture metadata |
| High | Complete collision faction matrix | Directional rules affect damage and friendly fire | Trace all writes to `factioncollisions` |
| High | Pool exhaustion and actor reuse behavior | Existing fixed pools can overwrite active entries | Define explicit safe allocation policies |
| Medium | Exact time-unit conversion and pause behavior | Timings and fire cadence may drift | Trace `fl.Field1`, frame limiter and pause paths |
| Medium | Original executable parity | Modified source is not proof of 2006 baseline behavior | Record controlled reference tests |

## 6. Corrections and cautions for implementation

1. Do not use numeric legacy fields as Godot property names. Convert them to typed, named resources after tracing the field's full read/write set.
2. Keep actor archetype, enemy definition, runtime actor instance, boss definition and boss runtime controller as separate concepts.
3. Keep wallet/cash, kill count, combo, lives, shields, bombs and permanent upgrades as separate state properties.
4. Keep local two-player support distinct from network multiplayer. The inspected code demonstrates local player 1/player 2 paths, not a network protocol.
5. Keep Gusto-only additions in a separate compatibility layer or feature flag until original-game comparison establishes whether they belong in the target.
6. Preserve the original campaign generator as a deterministic data-generation step only if its exact random behavior and seed requirements are understood. Otherwise, export the generated campaign and document the intentional replacement.
7. Never copy the legacy binary save format into the new project as-is. Use a versioned schema, validation and atomic writes.
8. Avoid introducing Godot physics behavior until collision parity tests establish whether Area2D/SAT can replace the original approximations.

## 7. Recommended Godot 4 architecture boundary

The following is a proposed clean architecture boundary, not a claim that these scripts already exist:

```text
Game
├── GameState
├── GameLoop
│   ├── WorldUpdate
│   ├── CollisionSystem
│   ├── DamageResolver
│   ├── ProjectileSystem
│   ├── SpawnDirector
│   └── ProgressionController
├── Actors
│   ├── Actor (runtime)
│   ├── ActorDefinition (Resource)
│   ├── PlayerController
│   ├── EnemyController
│   └── BossController
├── Combat
│   ├── WeaponDefinition (Resource)
│   ├── ProjectileDefinition (Resource)
│   └── DamageEvent
├── Campaign
│   ├── LevelDefinition (Resource)
│   └── LevelEvent (Resource)
├── Presentation
│   ├── AnimationLibrary
│   ├── BackgroundController
│   ├── ParticleController
│   └── CameraController
├── UI
│   ├── MenuRouter
│   └── HUD
├── Audio
│   └── AudioManager
└── Persistence
    ├── ProfileSave
    └── SettingsSave
```

Suggested implementation order:
1. Data schema and asset manifest (no gameplay yet).
2. Actor runtime and movement.
3. Collision compatibility layer and damage attribution.
4. Projectile definitions, mounts and firing cadence.
5. Enemy spawning and AI.
6. Boss controller and component dependencies.
7. Campaign event runner and procedural generation.
8. Player progression, shop and save system.
9. UI, HUD, audio and local co-op.
10. Baseline comparison and balancing.

## 8. Readiness verdict

**Ready for:** designing the Godot project structure, defining typed data schemas, and implementing isolated technical prototypes (movement, collision, projectile pool and sprite animation).

**Not ready for:** claiming a faithful full-game recreation, finalizing every gameplay constant, or treating Gusto's modified behavior as the original Mighty Rodent baseline.

The next most valuable audit deliverable is the complete 81-field actor semantic map and a fully resolved player runtime field map. The actor/projectile extraction and profile/upgrade audit are now published, so repeating those overviews would add little value. A separate original-executable test log is still required for behavioral equivalence.
