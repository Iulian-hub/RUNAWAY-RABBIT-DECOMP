Function wrapangle#(arg0#)
    Local local0#
    local0 = (arg0 Mod 360.0)
    If (0.0 > local0) Then
        local0 = (360.0 + local0)
    EndIf
    Return local0
    Return 0.0
End Function
