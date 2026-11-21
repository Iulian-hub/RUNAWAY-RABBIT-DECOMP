extends Node
## Persistent 10-slot profile system ported from the original Blitz3D profiles.dat.
const PROFILE_PATH: String = "user://profiles.cfg"
const PROFILE_COUNT: int = 10
const MAX_LEVEL: int = 29

var slots: Array[Dictionary] = []
var active_slot: int = 0

func _ready() -> void:
	load_profiles()

func load_profiles() -> void:
	slots.clear()
	var config := ConfigFile.new()
	var load_result := config.load(PROFILE_PATH)
	active_slot = 0
	if load_result == OK:
		active_slot = clampi(int(config.get_value("meta", "active_slot", 0)), 0, PROFILE_COUNT)
	for slot_number in range(1, PROFILE_COUNT + 1):
		var raw: Variant = config.get_value("profiles", str(slot_number), {}) if load_result == OK else {}
		slots.append(_normalize_profile(raw))
	if active_slot > 0 and slots[active_slot - 1].is_empty():
		active_slot = 0

func save_profiles() -> Error:
	var config := ConfigFile.new()
	config.set_value("meta", "format_version", 1)
	config.set_value("meta", "active_slot", active_slot)
	for index in range(PROFILE_COUNT):
		config.set_value("profiles", str(index + 1), slots[index])
	return config.save(PROFILE_PATH)

func _normalize_profile(raw: Variant) -> Dictionary:
	if not raw is Dictionary or raw.is_empty():
		return {}
	var profile: Dictionary = raw.duplicate(true)
	profile["name"] = str(profile.get("name", "PLAYER"))
	profile["difficulty"] = clampi(int(profile.get("difficulty", 2)), 1, 4)
	profile["current_level"] = clampi(int(profile.get("current_level", 1)), 1, MAX_LEVEL)
	profile["sound_enabled"] = bool(profile.get("sound_enabled", true))
	profile["music_enabled"] = bool(profile.get("music_enabled", true))
	profile["hints_enabled"] = bool(profile.get("hints_enabled", true))
	profile["cash"] = maxi(0, int(profile.get("cash", 0)))
	profile["players"] = clampi(int(profile.get("players", 1)), 1, 2)
	profile["best_score"] = maxi(0, int(profile.get("best_score", 0)))
	profile["total_score"] = maxi(0, int(profile.get("total_score", 0)))
	var upgrades: Dictionary = profile.get("upgrades", {})
	var defaults := _default_upgrades(profile["difficulty"])
	for key in defaults:
		upgrades[key] = maxi(0, int(upgrades.get(key, defaults[key])))
	profile["upgrades"] = upgrades
	return profile

func _default_upgrades(difficulty: int) -> Dictionary:
	var starting_values := {
		1: {"weapon_primary": 2, "weapon_secondary": 4, "weapon_special": 3},
		2: {"weapon_primary": 2, "weapon_secondary": 3, "weapon_special": 2},
		3: {"weapon_primary": 2, "weapon_secondary": 2, "weapon_special": 1},
		4: {"weapon_primary": 2, "weapon_secondary": 1, "weapon_special": 1}
	}
	var selected: Dictionary = starting_values.get(difficulty, starting_values[2]).duplicate(true)
	selected.merge({
		"upgrade_1": 1,
		"upgrade_2": 1,
		"upgrade_3": 0,
		"upgrade_4": 0,
		"upgrade_5": 0
	}, true)
	return selected

func create_profile(profile_name: String, difficulty: int) -> int:
	var clean_name := profile_name.strip_edges()
	if clean_name.is_empty():
		return 0
	clean_name = clean_name.substr(0, 16)
	for index in range(PROFILE_COUNT):
		if slots[index].is_empty():
			slots[index] = {
				"name": clean_name,
				"difficulty": clampi(difficulty, 1, 4),
				"current_level": 1,
				"sound_enabled": true,
				"music_enabled": true,
				"hints_enabled": true,
				"cash": 0,
				"players": 1,
				"best_score": 0,
				"total_score": 0,
				"upgrades": _default_upgrades(difficulty)
			}
			active_slot = index + 1
			save_profiles()
			return active_slot
	return 0

func select_profile(slot_number: int) -> bool:
	if slot_number < 1 or slot_number > PROFILE_COUNT or slots[slot_number - 1].is_empty():
		return false
	active_slot = slot_number
	save_profiles()
	return true

func delete_profile(slot_number: int) -> bool:
	if slot_number < 1 or slot_number > PROFILE_COUNT or slots[slot_number - 1].is_empty():
		return false
	slots[slot_number - 1] = {}
	if active_slot == slot_number:
		active_slot = 0
	save_profiles()
	return true

func has_active_profile() -> bool:
	return active_slot >= 1 and active_slot <= PROFILE_COUNT and not slots[active_slot - 1].is_empty()

func get_active_profile() -> Dictionary:
	if not has_active_profile():
		return {}
	return slots[active_slot - 1]

func get_profile(slot_number: int) -> Dictionary:
	if slot_number < 1 or slot_number > PROFILE_COUNT:
		return {}
	return slots[slot_number - 1]

func set_active_preference(key: String, value: bool) -> void:
	if not has_active_profile():
		return
	slots[active_slot - 1][key] = value
	save_profiles()

func set_active_players(value: int) -> void:
	if not has_active_profile():
		return
	slots[active_slot - 1]["players"] = clampi(value, 1, 2)
	save_profiles()

func get_unlocked_level() -> int:
	if not has_active_profile():
		return 1
	return clampi(int(get_active_profile().get("current_level", 1)), 1, MAX_LEVEL)

func record_level_started(level_number: int) -> void:
	if not has_active_profile():
		return
	var profile := slots[active_slot - 1]
	profile["last_played_level"] = clampi(level_number, 1, MAX_LEVEL)
	save_profiles()

func record_run(score: int) -> void:
	if not has_active_profile():
		return
	var profile := slots[active_slot - 1]
	var safe_score := maxi(0, score)
	profile["best_score"] = maxi(int(profile.get("best_score", 0)), safe_score)
	profile["total_score"] = int(profile.get("total_score", 0)) + safe_score
	save_profiles()

func complete_level(level_number: int, score: int) -> void:
	if not has_active_profile():
		return
	var profile := slots[active_slot - 1]
	var safe_level := clampi(level_number, 1, MAX_LEVEL)
	profile["cash"] = int(profile.get("cash", 0)) + maxi(0, score)
	if safe_level == int(profile.get("current_level", 1)) and safe_level < MAX_LEVEL:
		profile["current_level"] = safe_level + 1
	record_run(score)
