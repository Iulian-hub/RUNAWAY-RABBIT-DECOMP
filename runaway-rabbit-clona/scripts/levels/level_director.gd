extends Node
class_name LevelDirector
## Event sequencer matching Functions/dolevels.bb.

signal event_started(level_event: LevelEventData, event_index: int)
signal level_completed

const SOURCE_TICKS_PER_SECOND := 60.0

var definition: LevelDefinition
var level_time: float = 0.0
var bosses_defeated: int = 0
var active_enemy_count: int = 0
var current_event_index: int = 0
var running: bool = false
var _completion_pending: bool = false

func start_level(level_definition: LevelDefinition) -> void:
	if level_definition == null:
		push_error("LevelDirector.start_level received a null LevelDefinition.")
		return
	definition = level_definition
	level_time = 0.0
	bosses_defeated = 0
	active_enemy_count = 0
	# startlevel.bb sets currentevent=1; dolevels.bb evaluates currentevent+1.
	current_event_index = 1
	_completion_pending = false
	running = true

func _process(delta: float) -> void:
	if not running or definition == null:
		return

	if current_event_index < definition.events.size():
		var next_event := definition.events[current_event_index]
		if next_event != null:
			if next_event.trigger_time > 0.0 and level_time < next_event.trigger_time:
				# fl\\Field1 is frame-scaled in the Blitz3D source, not seconds.
				var source_delta := delta * SOURCE_TICKS_PER_SECOND
				level_time = minf(
					level_time + source_delta * definition.level_time_multiplier,
					next_event.trigger_time
				)
			if next_event.trigger_time > 0.0 and next_event.trigger_time <= level_time and bosses_defeated >= next_event.required_bosses_defeated:
				current_event_index += 1
				event_started.emit(next_event, current_event_index)
				if next_event.completes_level:
					_completion_pending = true
	_try_finish_level()

func notify_boss_defeated() -> void:
	bosses_defeated += 1

func set_active_enemy_count(value: int) -> void:
	active_enemy_count = maxi(0, value)
	_try_finish_level()

func _try_finish_level() -> void:
	if _completion_pending and active_enemy_count == 0:
		_completion_pending = false
		running = false
		level_completed.emit()

func stop_level() -> void:
	running = false
	_completion_pending = false
