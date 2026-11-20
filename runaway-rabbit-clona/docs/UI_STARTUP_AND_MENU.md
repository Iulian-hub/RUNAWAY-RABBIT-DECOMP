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
