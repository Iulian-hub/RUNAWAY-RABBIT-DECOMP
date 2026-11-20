# Background music and campaign level selection

## Source references
- The original menu soundtrack is `music/menu.ogg`; `startgame()` loads it, sets volume to 0.45 and loops it. The source calls `startmusic(2)` on the main menu and campaign map.
- `music/main.ogg` is the regular campaign soundtrack and `music/boss.ogg` is the boss soundtrack. The original `startlevel()` switches to music index 1 and index 3 for boss mode.
- The campaign map uses `gfx/mappic.jpg`, `gfx/mapbutton.png`, `gfx/mapbutton2.png`, grey variants, and `gfx/mappointer.png`.
- `EntryPoint.bb` defines 29 map node coordinates. Boss levels are 3, 7, 13, 21 and 29. The source locks levels above the profile's current unlocked level.

## Godot implementation
- The boot scene owns a persistent `AudioStreamPlayer`. The original menu OGG loops across the main menu and campaign map; the MUSIC option controls playback and persists through the existing settings file.
- The campaign selector uses the original map artwork and original marker textures. Node positions are transcribed from the source's 640x480 coordinate table and scaled to the map display.
- Progress is read from `user://settings.cfg` at `campaign/unlocked_level`, defaulting to level 1.
- Selecting a node opens the existing gameplay prototype and displays the selected sector. Enemy waves and stage data are still shared prototype placeholders; stage-specific scripting, victory progression and original profile persistence are not yet implemented.

## Fidelity note
All imported artwork and audio are unchanged assets from the Gusto-modified Runaway Rabbit source repository. Their use does not prove exact parity with the original Mighty Rodent executable. The regular campaign `music/main.ogg` is present in the source repository but was not imported in this pass; the menu loop therefore continues during the prototype gameplay. Boss music is imported for later runtime integration.

## Animated main-menu scene

The original `startgame()` displays `gfx/mainpic.jpg` only during the transition. It then calls `startmenubkg(38, 0)`; that function sets `currentlevel=38`, selects the background from level 38's first event, and the main game loop continues calling `dobackgrounds()`, `doactors()`, and the other world-update routines while the menu is active. Level 38's first event selects background set 2 (the forest set).

Godot now replaces the static `mainpic.jpg` menu backdrop with the original forest assets: `forestsky.jpg`, `forestbkg2.png`, `forestbkg1.png`, and `forestground.png`. Each layer uses a separate scrolling shader; relative speeds are based on the source's per-frame texture-scroll values (converted approximately to seconds at 60 FPS). Menu controls remain a foreground UI layer. This recreates the source's continuously moving parallax backdrop, not the full Blitz3D actor simulation of level 38; actor/enemy animation parity remains future work.


## Live menu enemy traffic and forest foreground sizing

The forest foreground is constrained to a 32-pixel strip at the bottom of the 1280x720 design viewport. This follows the original forest background definition's 42.5 height multiplied by its 0.75 vertical scale (approximately 31.9 px). The water/forest layers are no longer stretched across the full viewport: the island-and-water layer and two separate shallow wave layers are placed in lower screen bands, with their original independent horizontal scroll speeds.

The main menu now creates ambient enemy fly-bys using the original sprite sheets imported unchanged from `gfx/`: `basicflyer.png`, `advancedflyer.png`, `sauceranim.png`, `divefighter.png`, `kamikaze.png`, and `zipjet.png`. Each atlas is split into 128x128 frames, consistent with the original `artdefinitions()` animation metadata. Enemies spawn alternately from randomized left/right edges, move across the scene with a small vertical flight oscillation, and are removed after leaving the viewport. They are decorative menu actors and do not participate in gameplay collision or combat.


## Menu composition corrections

- Menu enemies are rendered at the background's normal canvas Z and are added before the menu controls. They therefore pass behind the header, panel, labels and buttons. Their scale is increased to 0.68–0.82; the ambient population is capped at four with a slower spawn interval to reduce clutter.
- Removed the oversized translucent header strip that created a hard horizontal seam.
- Replaced the incorrect full-texture `menuborder.png` StyleBoxTexture (which stretched the green carrot pattern across the panel) with a restrained translucent blue StyleBoxFlat and a subtle border.
- Adjusted the island/water bands to fill the lower part of the scene without the large blank gap above the ground. The wave texture now repeats horizontally instead of being stretched into a single oversized wave. The shader exposes a repeat-count parameter for that purpose.


## Additional water layer and straight-line enemy lanes

A fourth copy of the original scrolling water layer is inserted at y=550, above the lowest water band, preserving the same repeat scale and horizontal scroll speed.

Menu enemies now use four fixed horizontal lanes (y=175, 300, 425, 550). A lane can be occupied by only one active enemy, including while it is entering or leaving the screen. This prevents sprites from meeting or overlapping while moving in opposite directions. Vertical bobbing and rotation have been removed; each enemy follows a constant horizontal trajectory. Sprite scale is increased slightly to 0.76–0.88.
