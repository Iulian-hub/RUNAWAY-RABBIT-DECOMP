Function gui_createsprite.gui_object(arg0#, arg1#, arg2#, arg3#, arg4#, arg5#, arg6#, arg7#, arg8%)
    Local local0.gui_object
    Local local1.gui_sprite
    arg4 = arg4
    arg5 = arg5
    arg6 = arg6
    arg7 = arg7
    local0 = (New gui_object)
    local1 = (New gui_sprite)
    local0\Field0 = $01
    local0\Field1 = (Handle local1)
    local1\Field0 = gui_createspritemesh(arg4, arg5, arg6, arg7, arg8)
    gui_positionobject(local0, arg0, arg1)
    gui_scaleobject(local0, arg2, arg3)
    local0\Field6 = gui_current_group
    Return local0
    Return Null
End Function
