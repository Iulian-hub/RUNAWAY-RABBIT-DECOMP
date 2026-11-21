extends Resource
class_name LevelEventData
## Named Godot representation of one original Blitz3D levelevent.

@export_group("Trigger")
@export_range(0.0, 100000.0, 1.0) var trigger_time: float = 0.0
@export_range(0, 10, 1) var required_bosses_defeated: int = 0
@export_range(0, 100, 1) var aggregate_enemy_limit: int = 0

@export_group("Scene")
@export_range(0, 10, 1) var background_id: int = 0
@export_range(0, 10, 1) var music_id: int = 0

@export_group("Spawns")
@export var scripted_spawns: Array[Dictionary] = []
@export var dynamic_spawn_groups: Array[Dictionary] = []
@export var boss_spawn: Dictionary = {}
@export var background_actor_spawns: Array[Dictionary] = []

@export_group("Original Blitz3D data")
@export var source_fields: Dictionary = {}
@export var source_line_start: int = 0
@export var source_line_end: int = 0

@export_group("Flow")
@export var announcement: String = ""
@export var completes_level: bool = false
@export_range(0, 50, 1) var menu_transition_id: int = 0
