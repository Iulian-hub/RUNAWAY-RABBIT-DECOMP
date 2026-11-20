# Multiplayer and Input Audit

> Static-source findings from the modified Blitz3D source. Input values are configurable globals; exact defaults and original-game parity must be checked against the initialization code and original executable.

## 1. Player model

The source allocates two `playertype` records and initializes two player actors in `loadplayers()`. Each player record stores an actor slot (Field1), movement coordinates (Fields4/5), input mode (Field3), lives (Field10), score/cash-like score total (Field11), shields (Field9), bombs (Field21), crosshair entity (Field2), shield visual (Field18), combo state (Fields23/24) and additional timers/upgrade state.

The type has 34 fields (Field0..Field33). Several meanings are confirmed by call sites; other fields remain provisional and should not be renamed until a full field-reference index is complete.

## 2. Player creation and respawn

`loadplayers()` creates both player records, loads a crosshair sprite and shield sprite for each, then creates one actor per player. The player actor is initially suppressed until activated.

`addplayer()` activates the selected player, restores the actor, assigns the input mode, start position, shield count and lives, resets transient timers/combo state, assigns faction 1, and calls `doplayerweaponsetup()`.

On player death, `doplayerhits()` can consume a life and call `addplayer()` again at the entry position. The player actor temporarily uses faction 10 during its death animation.

## 3. Two-player support

The code has explicit player 1 and player 2 branches in:
- `doplayers()`: routes actor AI updates to the correct player record.
- `doplayerweapons()`: reads controls according to the player's configured input mode.
- `doplayerhits()`: shield/life/death and re-entry.
- `dohud()`: separate HUD widgets for lives, score, shields, bombs and beam charge.
- `actordeath()`: attributes kills, score and combo state to the responsible player.

Player 1 and player 2 use distinct HUD colors and screen positions. The code supports both player records simultaneously, but this static audit does not assert network multiplayer; it is local/shared-device multiplayer.

## 4. Input modes and controls

### 4.1 Movement

`calckeyforce()` computes a normalized direction from configurable key globals:
- right minus left;
- down minus up;
- secondary WASD contribution when `nokeys = 0`;
- joystick axis fallback when no keyboard direction is active.

The result is clamped to -1..1 per axis, smoothed through `keyforcex/keyforcey`, integrated using the frame factor and capped at ±2. This is used by the menu cursor and movement helper path.

`movekeyboard()` clamps a supplied cursor coordinate to the current graphics width/height. `domouse()` reads mouse X/Y speed.

### 4.2 Player weapon controls

`doplayerweapons()` exposes three explicit control modes via player Field3:

| Player Field3 | Fire | Bomb |
|---|---|---|
| 0 | Space, Z, or left mouse | X, Enter, or right mouse |
| 1 | Left mouse | Right mouse |
| 2 | Space, Z, or joystick button 1 | X, Enter, or joystick button 2 |

Bomb use is gated by available bombs and by bomb/game-over/win/get-ready/re-entry timers. The global `canfire` latch prevents repeated activation while a control remains held. The Gusto addition `forcefireon` overrides the primary weapon firing state.

### 4.3 Menu controls

`domenu()` handles mouse hover/click and accepts Z, Enter, Space and joystick button 1 as activation inputs. It also moves the mouse cursor from keyboard/joystick force when mouse movement is idle. `nokeys` can disable keyboard/joystick menu contributions.

### 4.4 Configurable key bindings

The code refers to named key globals (e.g. `leftkey`, `rightkey`, `upkey`, `downkey`, `wkey`, `akey`, `skey`, `dkey`, `spacekey`, `zkey`, `xkey`, `enterkey`, `esckey`). The source initializes at least `esckey` and `upkey` in the initialization routine, while the complete defaults are defined across global setup and menu configuration. Treat bindings as data, not hardcoded behavior, in the Godot port.

## 5. Crosshair and aiming

Each player has a crosshair sprite. Player weapon setup and aiming use the crosshair's entity position to calculate a target angle. Turret angle is clamped to an arc (roughly 80°..280°) in `doplayerweapons()`. The source contains Gusto-specific crosshair visibility and color toggles; these are modifications, not automatically part of the original baseline.

## 6. HUD data

For each active player, `dohud()` displays:
- lives count;
- score;
- shield units (up to four visual segments);
- bombs (up to four visual segments);
- beam-laser charge/availability bar.

The HUD hides the player's widgets when that player is inactive. A global completion percentage is computed from level time / longest time and forced to 100% during the win timer. Debug mode shows FPS text.

## 7. Godot 4 reconstruction recommendations

1. Use one PlayerController scene/script with per-player InputMap actions or per-device input profiles.
2. Store player state separately from actor visual nodes; avoid duplicating the full player script for player 2.
3. Implement local co-op as two independent input contexts and two player-state objects.
4. Keep crosshair aim and keyboard movement as separate input paths.
5. Preserve the original three input modes as profiles until the intended device assignments are verified.
6. Add tests for simultaneous fire, bomb gating, held-button edge behavior, player 2 activation/deactivation, respawn, and score attribution.
7. Treat network multiplayer as out of scope unless separately requested; no networking protocol is present in the audited routines.

## 8. Open questions

- Complete player Field0..Field33 map and PlayerSetup Field0..Field9 mapping.
- Exact default key codes and whether the in-game options screen can remap every binding.
- Exact joystick axis/button mapping across supported hardware.
- Whether player 2 can be activated in all game modes or only selected menu paths.
- Verify which crosshair, color and auto-fire features are Gusto-only.
