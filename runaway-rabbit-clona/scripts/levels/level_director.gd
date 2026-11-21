extends Node
class_name LevelDirector
## Event sequencer matching Functions/dolevels.bb and startlevel.bb.

signal level_begun
signal event_started(level_event: LevelEventData, event_index: int)
signal level_completed

const SOURCE_TICKS_PER_SECOND := 60.0
const NORMAL_GET_READY_TICKS := 180.0
const MAX_SOURCE_FRAME_FACTOR := 3.0

var definition: LevelDefinition
var level_time: float = 0.0
var bosses_defeated: int = 0
var active_enemy_count: int = 0
var current_event_index: int = 0
var running: bool = false
var has_begun: bool = false
var _get_ready_remaining: float = 0.0
var _completion_pending: bool = false

func start_level(level_definition: LevelDefinition) -> void:
	if level_definition == null:
		push_error("LevelDirector.start_level received a null LevelDefinition.")
		return
	definition = level_definition
	level_time = 0.0
	bosses_defeated = 0
	active_enemy_count = 0
	current_event_index = 1
	has_begun = false
	_get_ready_remaining = NORMAL_GET_READY_TICKS
	_completion_pending = false
	running = true

func _process(delta: float) -> void:
	if not running or definition == null:
		return
	if not has_begun:
		_get_ready_remaining = maxf(0.0, _get_ready_remaining - minf(delta * SOURCE_TICKS_PER_SECOND, MAX_SOURCE_FRAME_FACTOR))
		if _get_ready_remaining <= 0.0:
			has_begun = true
			level_begun.emit()
		return

	if current_event_index < definition.events.size():
		var next_event := definition.events[current_event_index]
		if next_event != null:
			if next_event.trigger_time > 0.0 and level_time < next_event.trigger_time:
				# fl\\Field1 is frame-scaled in the Blitz3D source, not seconds.
				var source_delta := minf(delta * SOURCE_TICKS_PER_SECOND, MAX_SOURCE_FRAME_FACTOR)
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
