Function gui_correctcamerafov%(arg0%)
    Local local0#
    Local local1#
    Local local2#
    local0 = 0.75
    local1 = ((Float graphicsheight()) / (Float graphicswidth()))
    local2 = (local0 / local1)
    scaleentity(arg0, 1.0, local2, 1.0, $01)
    Return $00
End Function
