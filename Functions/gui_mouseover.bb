Function gui_mouseover%(arg0.gui_object)
    Local local0%
    Local local1%
    local0 = gui_getobjectentity(arg0)
    entitypickmode(local0, $02, $01)
    local1 = camerapick(gui_camera, (Float mousex()), (Float mousey()))
    entitypickmode(local0, $00, $01)
    If (local1 = $00) Then
        Return $00
    Else
        Return $01
    EndIf
    Return $00
End Function
