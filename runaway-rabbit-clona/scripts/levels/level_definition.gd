extends Resource
class_name LevelDefinition
## One playable campaign level, assembled from an original source template.

@export_range(1, 29, 1) var level_number: int = 1
@export_range(1, 15, 1) var source_template_id: int = 1
@export_range(0, 10, 1) var initial_background_id: int = 0
@export_range(0.1, 5.0, 0.1) var level_time_multiplier: float = 1.5
@export var events: Array[LevelEventData] = []
@export var source_template_event_count: int = 0
@export var source_file: String = "Functions/EntryPoint.bb"
@export var initial_dynamic_spawn_groups: Array[Dictionary] = []
@export var initial_aggregate_enemy_limit: int = 0

func get_last_trigger_time() -> float:
	var result := 0.0
	for level_event in events:
		if level_event != null:
			result = maxf(result, level_event.trigger_time)
	return result
