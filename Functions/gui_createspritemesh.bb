Function gui_createspritemesh%(arg0#, arg1#, arg2#, arg3#, arg4%)
    Local local0%
    Local local1%
    Local local2#
    Local local3#
    Local local4#
    Local local5#
    local0 = createmesh($00)
    local1 = createsurface(local0, $00)
    If (arg4 <> 0) Then
        local2 = -0.5
        local3 = 0.5
        local4 = 0.5
        local5 = -0.5
    Else
        local2 = 0.0
        local3 = 0.0
        local4 = 1.0
        local5 = -1.0
    EndIf
    addvertex(local1, local2, local3, 0.0, arg0, arg1, 1.0)
    addvertex(local1, local4, local3, 0.0, arg2, arg1, 1.0)
    addvertex(local1, local4, local5, 0.0, arg2, arg3, 1.0)
    addvertex(local1, local2, local5, 0.0, arg0, arg3, 1.0)
    addtriangle(local1, $00, $01, $02)
    addtriangle(local1, $00, $02, $03)
    Return local0
    Return $00
End Function
