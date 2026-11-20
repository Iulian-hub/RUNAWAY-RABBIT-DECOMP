Function gui_objectorder%(arg0.gui_object, arg1%)
    Local local0%
    local0 = gui_getobjectentity(arg0)
    entityorder(local0, arg1)
    Return $00
End Function
