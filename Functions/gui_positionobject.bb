Function gui_positionobject%(arg0.gui_object, arg1#, arg2#)
    Local local0%
    Local local1#
    Local local2#
    Local local3#
    local0 = gui_getobjectentity(arg0)
    local1 = (((arg1 - 320.0) * 2.0) + gui_camera_position_x)
    local2 = (((arg2 - 240.0) * -2.0) + gui_camera_position_y)
    local3 = ((gui_camerazoom * 640.0) + gui_camera_position_z)
    positionentity(local0, local1, local2, local3, $01)
    arg0\Field2 = arg1
    arg0\Field3 = arg2
    Return $00
End Function
