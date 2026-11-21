extends Control

signal profile_chosen
signal back_requested

@onready var _slot_grid: GridContainer = $Panel/Content/Body/SlotGrid
@onready var _name_input: LineEdit = $Panel/Content/Body/CreatePanel/ProfileName
@onready var _selected_label: Label = $Panel/Content/SelectedLabel
@onready var _use_button: Button = $Panel/Content/Body/CreatePanel/UseProfileButton
@onready var _delete_button: Button = $Panel/Content/Body/CreatePanel/DeleteProfileButton
@onready var _confirm_delete: ConfirmationDialog = $ConfirmDelete

var _selected_slot: int = 0

func _ready() -> void:
	$Panel/Content/Body/CreatePanel/CreateProfileButton.pressed.connect(_create_profile)
	_use_button.pressed.connect(_use_selected_profile)
	_delete_button.pressed.connect(func() -> void: _confirm_delete.popup_centered())
	$Panel/Content/BackButton.pressed.connect(func() -> void: back_requested.emit())
	_confirm_delete.confirmed.connect(_delete_selected_profile)
	for index in range(_slot_grid.get_child_count()):
		var slot_number := index + 1
		var button := _slot_grid.get_child(index) as Button
		button.pressed.connect(_on_slot_pressed.bind(slot_number))
	_refresh_slots()

func _on_slot_pressed(slot_number: int) -> void:
	for child in _slot_grid.get_children():
		(child as Button).button_pressed = false
	var profile := ProfileManager.get_profile(slot_number)
	if profile.is_empty():
		_selected_slot = 0
		_selected_label.text = "EMPTY SLOT — CREATE A PROFILE"
		_use_button.disabled = true
		_delete_button.disabled = true
		return
	_selected_slot = slot_number
	_selected_label.text = "SELECTED: %s  ·  LEVEL %02d" % [profile["name"], profile["current_level"]]
	_use_button.disabled = false
	_delete_button.disabled = false
	for index in range(_slot_grid.get_child_count()):
		var button := _slot_grid.get_child(index) as Button
		button.button_pressed = index + 1 == slot_number

func _refresh_slots() -> void:
	for index in range(_slot_grid.get_child_count()):
		var slot_number := index + 1
		var button := _slot_grid.get_child(index) as Button
		var profile := ProfileManager.get_profile(slot_number)
		button.text = "%02d  ·  %s" % [slot_number, "EMPTY SLOT" if profile.is_empty() else "%s  |  LEVEL %02d" % [profile["name"], profile["current_level"]]]
		button.disabled = false
		button.button_pressed = false
	_selected_slot = 0
	_selected_label.text = "SELECT A PROFILE OR CREATE A NEW ONE"
	_use_button.disabled = true
	_delete_button.disabled = true

func _create_profile() -> void:
	var new_slot := ProfileManager.create_profile(_name_input.text)
	if new_slot == 0:
		_selected_label.text = "ENTER A NAME OR FREE A PROFILE SLOT"
		return
	_name_input.clear()
	_refresh_slots()
	_on_slot_pressed(new_slot)
	_use_selected_profile()

func _use_selected_profile() -> void:
	if ProfileManager.select_profile(_selected_slot):
		profile_chosen.emit()

func _delete_selected_profile() -> void:
	if ProfileManager.delete_profile(_selected_slot):
		_refresh_slots()
