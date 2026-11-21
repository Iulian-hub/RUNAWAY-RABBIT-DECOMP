extends RefCounted
class_name LevelTemplateLibrary
## Loads the 15 source templates extracted from the original EntryPoint.bb.
## Fields not yet semantically decoded remain intact in LevelEventData.source_fields.

const DATA_PATH := "res://data/source_level_templates.json"
const CAMPAIGN_TEMPLATE_MAP := {
	1: 1, 2: 2, 3: 3, 4: 4, 5: 5,
	6: 0, 7: 6, 8: 0, 9: 7, 10: 0,
	11: 8, 12: 0, 13: 9, 14: 0, 15: 0,
	16: 10, 17: 0, 18: 0, 19: 11, 20: 0,
	21: 12, 22: 0, 23: 0, 24: 13, 25: 0,
	26: 0, 27: 14, 28: 0, 29: 15
}
static var _document: Dictionary = {}

static func get_template(source_template_id: int, campaign_level: int = 0) -> LevelDefinition:
	var template_data := _get_template_data(source_template_id)
	if template_data.is_empty():
		push_error("Unknown source level template: %d" % source_template_id)
		return null

	var definition := LevelDefinition.new()
	definition.source_template_id = source_template_id
	definition.level_number = campaign_level if campaign_level > 0 else source_template_id
	definition.level_time_multiplier = 2.2 if definition.level_number == 1 else 1.5
	definition.source_template_event_count = int(template_data.get("event_count", 0))
	definition.source_file = str(template_data.get("source_file", "Functions/EntryPoint.bb"))

	var raw_events: Array = template_data.get("events", [])
	for raw_value in raw_events:
		if not raw_value is Dictionary:
			continue
		var raw_event: Dictionary = raw_value
		var fields: Dictionary = raw_event.get("fields", {})
		var event := LevelEventData.new()
		event.trigger_time = float(fields.get("Field0", 0.0))
		event.required_bosses_defeated = int(fields.get("Field1", 0))
		event.background_id = int(fields.get("Field2", 0))
		event.music_id = int(fields.get("Field4", 0))
		event.announcement = str(fields.get("Field25", ""))
		event.completes_level = int(fields.get("Field26", 0)) == 1
		event.menu_transition_id = int(fields.get("Field28", 0))
		event.source_fields = fields.duplicate(true)
		event.source_line_start = int(raw_event.get("source_line_start", 0))
		event.source_line_end = int(raw_event.get("source_line_end", event.source_line_start))
		event.scripted_spawns = _build_scripted_spawns(fields)
		event.boss_spawn = _build_boss_spawn(fields)
		event.background_actor_spawns = _build_background_spawns(fields)
		event.dynamic_spawn_groups = _build_dynamic_spawn_groups(fields)
		definition.events.append(event)

	if not definition.events.is_empty():
		definition.initial_background_id = definition.events[0].background_id
	return definition

static func get_campaign_level(campaign_level: int) -> LevelDefinition:
	var source_id := int(CAMPAIGN_TEMPLATE_MAP.get(campaign_level, 0))
	if source_id == 0:
		return null
	return get_template(source_id, campaign_level)

static func get_available_source_templates() -> Array[int]:
	var result: Array[int] = []
	for template_id in range(1, 16):
		if not _get_template_data(template_id).is_empty():
			result.append(template_id)
	return result

static func _get_template_data(template_id: int) -> Dictionary:
	if _document.is_empty():
		var file := FileAccess.open(DATA_PATH, FileAccess.READ)
		if file == null:
			push_error("Cannot open extracted source templates: " + DATA_PATH)
			return {}
		var parsed: Variant = JSON.parse_string(file.get_as_text())
		if not parsed is Dictionary:
			push_error("Invalid extracted source template JSON.")
			return {}
		_document = parsed
	var templates: Array = _document.get("templates", [])
	for value in templates:
		if value is Dictionary and int(value.get("source_template_id", 0)) == template_id:
			return value
	return {}

static func _field_array(fields: Dictionary, field_name: String, max_index: int) -> Dictionary:
	var value: Variant = fields.get(field_name, {})
	return value if value is Dictionary else {}

static func _build_scripted_spawns(fields: Dictionary) -> Array[Dictionary]:
	var result: Array[Dictionary] = []
	var types := _field_array(fields, "Field5", 5)
	var xs := _field_array(fields, "Field6", 5)
	var ys := _field_array(fields, "Field7", 5)
	var angles := _field_array(fields, "Field8", 5)
	var health_multipliers := _field_array(fields, "Field9", 5)
	var speed_multipliers := _field_array(fields, "Field10", 5)
	var flags := _field_array(fields, "Field11", 5)
	for index in range(1, 6):
		var enemy_type := int(types.get(str(index), 0))
		if enemy_type <= 0:
			continue
		result.append({
			"enemy_type": enemy_type,
			"x": float(xs.get(str(index), 0.0)),
			"y": float(ys.get(str(index), 0.0)),
			"angle": float(angles.get(str(index), 0.0)),
			"health_multiplier": float(health_multipliers.get(str(index), 0.0)),
			"fire_rate_multiplier": float(speed_multipliers.get(str(index), 0.0)),
			"flags": int(flags.get(str(index), 0))
		})
	return result

static func _build_boss_spawn(fields: Dictionary) -> Dictionary:
	var types := _field_array(fields, "Field12", 1)
	var boss_type := int(types.get("1", 0))
	if boss_type <= 0:
		return {}
	return {
		"boss_type": boss_type,
		"x": float(_field_array(fields, "Field13", 1).get("1", 0.0)),
		"y": float(_field_array(fields, "Field14", 1).get("1", 0.0))
	}

static func _build_background_spawns(fields: Dictionary) -> Array[Dictionary]:
	var result: Array[Dictionary] = []
	var types := _field_array(fields, "Field15", 6)
	var xs := _field_array(fields, "Field16", 6)
	var ys := _field_array(fields, "Field17", 6)
	var depths := _field_array(fields, "Field18", 6)
	var scales := _field_array(fields, "Field19", 6)
	for index in range(1, 7):
		var actor_type := int(types.get(str(index), 0))
		if actor_type <= 0:
			continue
		result.append({
			"actor_type": actor_type,
			"parameter_a": float(xs.get(str(index), 0.0)),
			"parameter_b": float(ys.get(str(index), 0.0)),
			"position_x": float(depths.get(str(index), 0.0)),
			"position_y": float(scales.get(str(index), 0.0)),
		})
	return result

static func _build_dynamic_spawn_groups(fields: Dictionary) -> Array[Dictionary]:
	var result: Array[Dictionary] = []
	var tracked_types := _field_array(fields, "Field22", 5)
	var intervals := _field_array(fields, "Field23", 5)
	var limits := _field_array(fields, "Field24", 5)
	var group_modes := _field_array(fields, "Field20", 6)
	for index in range(1, 6):
		var enemy_type := int(tracked_types.get(str(index), 0))
		if enemy_type <= 0:
			continue
		result.append({
			"enemy_type": enemy_type,
			"source_field_23": float(intervals.get(str(index), 0.0)),
			"source_field_24": int(limits.get(str(index), 0)),
			"source_field_20": int(group_modes.get(str(index), 0)),
			"source_field_21": float(fields.get("Field21", 0.0))
		})
	return result
