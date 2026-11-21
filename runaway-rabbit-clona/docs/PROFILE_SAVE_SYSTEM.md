# Profile and save system

## Original Blitz3D behavior verified

The original source uses ten profile slots (profiles(1..10)) and stores the selected slot in currentprofile. Functions/loadprofiles.bb and Functions/saveprofiles.bb serialize one profiles.dat file. Functions/newprofile.bb initializes a profile, changeprofile.bb switches the active slot, and deleteprofile.bb clears one slot.

The original profile fields used by the game are:

| Original field | Meaning confirmed from source |
|---|---|
| Field0 | Profile/player name |
| Field1 | Current campaign level / next level |
| Field2 | Sound enabled |
| Field3 | Gameplay hints enabled |
| Field4 | Music enabled |
| Field5 | Difficulty selected at profile creation |
| Field6 | Cash |
| Field7–11 | Five upgrade levels |
| Field12, Field13, Field16 | Three additional weapon/loadout upgrade levels |
| Field14 | Player count (1 or 2) |
| Field15 | Additional player setup value copied into playersetup.Field9; exact UI meaning not established |

The original code only awards cash and advances Field1 after a successful campaign level completion (menuevent.bb, statstomap). It adds the run score to cash, and advances the level only when the completed level matches the profile's current level.

## Godot implementation

- Autoload: ProfileManager at scripts/autoload/profile_manager.gd.
- Persistent file: user://profiles.cfg (per-user application data, not inside the project folder).
- Ten independent slots; each stores its own name, difficulty, campaign level, last played level, cash, upgrade values, player count, hints, sound/music preferences, best score, and cumulative score.
- Profile selection/creation/deletion UI: ui/profile_select.tscn.
- First launch opens profile selection; later launches go to the main menu with the last active profile.
- Main-menu Options saves music, sound, hints, and player count to the active profile.
- Level selection reads the active profile's unlocked campaign level.
- Gameplay records the last played level and stores best/total score when the player dies.
- ProfileManager.complete_level(level_number, score) implements the original successful-completion rule: add cash, advance the campaign only if this is the current unlocked level, and update score totals.

## Current prototype limitation

The current Godot gameplay prototype does not yet have a level-victory condition or a campaign-completion event. Therefore complete_level() is ready for the later level-flow implementation, but is not called by the current prototype yet. A failed run records score but does not grant cash or unlock the next level, matching the original distinction between a run and a successful level completion.

The legacy binary profiles.dat is not imported automatically. Godot now writes a versioned, human-readable ConfigFile at user://profiles.cfg; this avoids coupling the new implementation to Blitz3D's binary serialization.
