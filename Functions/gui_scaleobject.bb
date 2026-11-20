Function gui_scaleobject%(arg0.gui_object, arg1#, arg2#)
    Local local0%
    local0 = gui_getobjectentity(arg0)
    scaleentity(local0, (arg1 * 2.0), (arg2 * 2.0), 1.0, $00)
    Return $00
End Function
