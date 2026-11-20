# Asset and Animation Catalog

> Static-source inventory for the checked-in modified Blitz3D project. File presence does not prove that every asset is used in the original 2006 build.

## 1. Asset pipeline

The project uses path tables and initialization routines rather than loading every asset ad hoc:
- `artdefinitions()` assigns menu texture paths and animation atlas metadata.
- `loadactorgraphics()` loads actor and turret textures, including flipped variants.
- `loadbackgrounds()` creates five background layers for each configured background.
- `loadparticles()` maps rectangular atlas regions to particle types and creates render layers.
- `loadexplosions()`, `loadshotflashes()`, `loadbonus()`, `loadthings()` and menu/UI loaders complete the resource setup.

Asset paths are relative to the game working directory (e.g. `gfx/...`, `music/...`, `sound/...`). A Godot port should centralize these paths in Resources or a manifest and validate missing files at startup.

## 2. Actor and character art

Root `gfx/` contains the visible gameplay art families:
- Player/hamster: `hamsteranim.png`, `hamsteranim2.png`, `hamsterdead.png`, `hamstergun.png`, `hamstergun2.png`, `hamsterminigun1.png`.. `hamsterminigun4.png`.
- Basic and advanced enemies: `basicflyer.png`, `advancedflyer.png`, `divefighter.png`, `hangglider.png`, `hangglider4.png`, `kamikaze.png`, `zipjet.png`, `sauceranim.png`, `largesauceranim.png`, `largesaucergreenanim.png`, `lasersauceranim.png`.
- Heavy/ground/large enemies: `hovercraft.png`, `heavyhovercraft.png`, `alientub.png`, `pogo.png`, `bigbird.png`, `energysphere.png`, `energyshield.png`.
- Other actor parts: `stamper_ship.png`, `stamper_foot.png`, `stamper_rod.png`.

The complete numeric actor-graphics mapping is held in `artdefinitions()` and actor type setup in `EntryPoint.bb`; filenames alone should not be treated as an authoritative enemy-ID mapping.

## 3. Boss art

The repository has a dedicated `gfx/bosses/` directory. The animation definitions include boss atlas IDs beginning at $19 (decimal 25). Several boss components are separate actors and may have their own animation or sprite. The enemy/boss catalog documents the current source-derived boss relationships; a future asset manifest should bind every boss actor archetype to its exact texture and animation ID.

## 4. Weapons, effects and pickups

Important root assets:
- Weapons/projectiles: `weapons.png`, `missileturret.png`, `singleturret.png`, `tripleturret.png`, `tubgun.png`, `hamstergun*.png`, `clusterbomb.png`.
- Pickups: `bonus.png`, `bonushalo.png`.
- Effects: `effects.png`, `explosions.png`, `yellowflash.png`, `blueflash.jpg`, `greenflash.jpg`, `purpleflash.jpg`, `redflash.jpg`, `yellowflash.jpg`, `shield.png`, `shield.jpg`.
- Environment objects: `bkgobjects.png`.

`loadparticles()` defines atlas UV rectangles in 0.25 increments for multiple particle types. It creates dedicated layers for effects, weapons, explosions and background objects. The same texture atlas is therefore reused with different UV windows and render ordering.

## 5. Animation metadata

The animation table is populated in `artdefinitions()`. Each animation entry supplies:
- atlas image path;
- frame duration (Field4);
- frame count (Field5);
- animation mode/flag (Field6);
- frame width and height (Fields7/8).

`doactoranimations()` increments the frame timer by the global frame factor. When the timer exceeds the configured duration, it advances the frame and wraps to frame 1. Field23 can act as a finite loop counter; when positive it is decremented on wrap. `updateactor()` selects the correct texture frame and flipped texture according to actor heading and game view.

Examples confirmed in source:
| Atlas | Frame duration | Frames | Declared dimensions |
|---|---:|---:|---:|
| hamsteranim.png | 3 | 8 | 128×128 |
| hamsteranim2.png | 3 | 8 | 128×128 |
| basicflyer.png | 10 | 2 | 128×128 |
| advancedflyer.png | 10 | 2 | 128×128 |
| sauceranim.png | 5 | 4 | 128×128 |
| lasersauceranim.png | 5 | 4 | 128×128 |
| largesaucergreenanim.png | 5 | 4 | 256×256 |
| divefighter.png | 5 | 1 | 128×128 |
| hovercraft.png | 5 | 4 | 128×128 |
| heavyhovercraft.png | 5 | 4 | 256×256 |
| pogo.png | 2 | 6 | 128×128 |
| energyshield.png | 10 | 3 | 128×128 |
| bigbird.png | 10 | 2 | 256×256 |

This is a sample of explicitly readable entries, not a claim that the table is exhaustive.

## 6. Backgrounds and camera

`loadbackgrounds()` creates a background actor and loads up to five layers per configured background. `dobackgrounds()` updates each layer's texture, scale, position and texture offset. Layer scrolling uses per-layer velocity fields multiplied by the frame factor.

The camera is fixed around (0,0,-1800) with a zoom field that eases toward a minimum value of 4.5. `docamerashake()` decays shake intensity, clamps it to 0..6 and applies alternating/random X/Y offsets. Background and camera motion are separate systems.

## 7. Explosions and sprite particles

`doexplosions()` advances each active explosion through a 4-column sprite sheet. The frame index is derived from a 4×4 grid (four columns, row computed by ceiling(frame/4)). Explosion animation advances by 0.4 × frame factor and is deactivated after frame value 16. It scales associated sprite layers over time.

`updatesprites()` updates sprite position, velocity, rotation, alpha and lifetime. The renderer transforms each particle into camera-facing geometry and writes four vertex positions/colors. This is a custom billboard particle renderer, not Godot's GPUParticles.

## 8. UI and promotional art

The root assets also include title/menu/map images, instructions, logo, upgrade cards, lives icons and HUD textures:
- `titlepic1.png`, `titlepic2.png`, `mainpic.jpg`, `jaggedlogo.jpg`, `instructions.jpg`, `mappic.jpg`;
- `mapbutton*.png`, `mappointer.png`;
- `p1lives.png`, `p2lives.png`, `hudbombs.png`, `hudshield.png`;
- `upgrade*.jpg` families;
- `gfx/menu/` contains mouse, borders, corners, menu texture and related interface art.

## 9. Godot asset migration recommendations

1. Generate a machine-readable asset manifest (path, category, dimensions, source symbol, usage status).
2. Preserve original atlases initially; use SpriteFrames/AtlasTexture rather than slicing/recompressing assets prematurely.
3. Keep actor archetype data separate from art resources.
4. Store animation speed and loop mode as named fields in Resources.
5. Use CanvasItem/Node2D for the 2D game; implement camera-facing particles only where they reproduce an existing effect.
6. Track unused assets and duplicate variants, but do not delete them until original-executable comparison confirms they are unused.

## 10. Remaining verification

- Complete numeric mapping of all actor, turret, boss and projectile graphics.
- Exact inventory and dimensions of every file in `gfx/background`, `gfx/bosses` and `gfx/menu`.
- Determine whether PSD/DB and promotional files are runtime dependencies.
- Compare texture filtering, blend modes, transparency and orientation against the original executable.
