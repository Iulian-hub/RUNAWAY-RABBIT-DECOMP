Function gui_createtext.gui_object(arg0#, arg1#, arg2$, arg3%, arg4%, arg5#)
    Local local0.gui_object
    Local local1.gui_text
    local0 = (New gui_object)
    local1 = (New gui_text)
    local0\Field0 = $02
    local0\Field1 = (Handle local1)
    local1\Field0 = $00
    local1\Field1 = gui_current_brush
    local1\Field2 = gui_current_font
    local1\Field3 = ""
    local1\Field4 = arg0
    local1\Field5 = arg1
    local1\Field6 = arg3
    local1\Field7 = arg4
    local1\Field8 = arg5
    local1\Field9 = gui_font_height
    local1\Field10 = gui_font_kerning
    gui_setstring(local0, arg2, $01)
    local0\Field6 = gui_current_group
    Return local0
    Return Null
End Function
