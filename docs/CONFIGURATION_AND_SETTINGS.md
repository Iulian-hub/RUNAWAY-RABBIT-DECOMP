# Configuration and Persistent Settings Audit

> This document records settings and startup defaults visible in the modified Blitz3D source. Some profile fields are intentionally left as numeric fields until their complete read/write/use map is confirmed.

## 1. Startup and build configuration

`EntryPoint()` is the application bootstrap. It initializes profiles, high scores, build metadata, demo flags, graphics mode, keyboard scan codes, arrays, actors, projectiles, effects, menus and player state before calling the game entry flow.

Visible build defaults:
- Internal build index is 8.
- Debug display is enabled only when the command line equals `dbug`.
- Demo mode is enabled unless the selected build metadata or `forcefull` indicates full version.
- Demo time begins at 108000 units.
- Main game frame target is initialized to 60 FPS.
- Default number of players is 1.
- Sound and music are initially enabled.

Build metadata includes external website and purchase/affiliate link fields. These are legacy distribution-era values and should not be treated as required game services in a standalone reconstruction.

## 2. Display modes

`res.dat` stores one integer, `loadresmode`. Valid values are 1..8; invalid or missing values cause `guessres()` to choose a default based on reported video memory.

| Mode | Resolution | Window mode | Bit depth |
|---:|---:|---|---:|
| 1 | 640×480 | Windowed | 16 |
| 2 | 800×600 | Windowed | 16 |
| 3 | 1024×768 | Windowed | 16 |
| 4 | 800×600 | Fullscreen | 16 |
| 5 | 640×480 | Windowed | 32 |
| 6 | 800×600 | Windowed | 32 |
| 7 | 1024×768 | Windowed | 32 |
| 8 | 800×600 | Fullscreen | 16 |

The table is derived from the `Select loadresmode` block in `EntryPoint()`. `guessres()` selects mode 1 initially, then may select 2, 5 or 6 as video-memory thresholds are crossed. The selection is based on legacy video-memory heuristics and is not an appropriate modern hardware detector.

## 3. Frame timing

`framelimitinit(60.0)` configures the timing system. `setspeedfactor()` calculates the frame factor from elapsed milliseconds and the target interval. The factor is capped at 3.0. Many simulation systems multiply timers and movement by this factor.

The original timing system is not a fixed-step physics engine. In Godot, use a fixed simulation tick for deterministic gameplay, but retain a compatibility time factor during the initial parity phase.

## 4. Input defaults

The source initializes keyboard scan-code globals:
- Escape: 1
- Up: 0xC8
- Down: 0xD0
- Left: 0xCB
- Right: 0xCD
- Space: 0x39
- A: 0x1E, W: 0x11, S: 0x1F, D: 0x20
- Z: 0x2C, X: 0x2D
- Enter: 0x1C
- Left Shift: 0x2A, Right Shift: 0x36
- Control: 0xB8
- Comma: 0x33

These are scan-code-style values, not Godot key names. The port should use InputMap action names and optionally allow rebinding.

## 5. Audio settings

Global sound and music flags are initialized to enabled. When a profile is active, `loadprofiles()` restores sound from profile Field2 and music from profile Field4. With no active profile, both are enabled by default.

The source has a global sound volume and per-effect volume overrides. It does not expose a fully documented modern audio-settings structure; map every menu event and profile field before deciding which settings are user-configurable.

## 6. Profile persistence

`profiles.dat` stores:
1. Current profile index.
2. Ten profile records.
3. For each record: name string followed by 16 integers, written in a fixed order.

The serialized integer order is profile Fields4,2,3,1,5,6,7,8,9,10,11,12,13,14,15,16. Profile Field15 is normalized to 1 if it is 0 on load. Current profile is clamped to 0..10.

The file is a positional binary stream with no visible schema version, checksum or migration layer. The content/progression audit contains the field meanings currently established by usage. The new implementation should not reuse this binary format as its native save format.

## 7. Gusto-modified settings

The checked-in Globals.bb defines additional settings:
- `crosshairvisible` defaults to 1.
- Player 1 crosshair RGB defaults to (55,255,0).
- Player 2 crosshair RGB defaults to (255,55,0).
- Three edge-trigger variables prevent repeated toggle activation.

The options menu injection in `loadmenu()` adds Auto-fire, Crosshair Visibility and Crosshair Color controls. These are modifications in the Gusto source and should remain isolated from the original-game baseline until verified.

## 8. Godot 4 configuration model

Recommended separation:
- `GameSettings`: resolution/window mode, VSync, frame cap, audio bus volumes.
- `InputSettings`: per-player InputMap profiles and device assignment.
- `AccessibilitySettings`: crosshair visibility, color, UI scaling.
- `ProfileSave`: campaign progress, cash, upgrades and player preferences.
- `RuntimeConfig`: build/demo flags and debug-only options, not persisted as player settings.

Use JSON or ConfigFile with an explicit schema version, validate values on load, write atomically (temporary file then rename), and keep a backup of the last valid save.

## 9. Risks and unresolved questions

- Legacy resolution mode uses an index and video-memory heuristic; modern port should preserve selectable modes, not the heuristic.
- Profile save format has no explicit versioning or checksum.
- Profile fields 1..16 are not all semantically mapped with equal confidence.
- The full menu options inventory is not yet reconciled with the original executable.
- Gusto-specific crosshair and auto-fire settings must not be silently attributed to the original Mighty Rodent.
