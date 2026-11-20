Function reachangle#(arg0#, arg1#, arg2#, arg3%, arg4%)
    Local local0#
    Local local1#
    Local local2#
    Local local3#
    turneddirection = $00
    local0 = (arg0 Mod 360.0)
    local1 = (arg1 Mod 360.0)
    arg3 = $01
    If (0.0 > local0) Then
        local0 = (360.0 + local0)
    EndIf
    If (0.0 > local1) Then
        local1 = (360.0 + local1)
    EndIf
    If (((local0 > (local1 - arg2)) And (local0 < (local1 + arg2))) <> 0) Then
        If (local1 > local0) Then
            turneddirection = $01
        EndIf
        If (local1 < local0) Then
            turneddirection = $FFFFFFFF
        EndIf
        If (local1 = local0) Then
            turneddirection = $00
        EndIf
        local0 = local1
        local2 = local0
    Else
        local0 = (arg0 Mod 360.0)
        local1 = (arg1 Mod 360.0)
        arg3 = $01
        If (0.0 > local0) Then
            local0 = (360.0 + local0)
        EndIf
        If (0.0 > local1) Then
            local1 = (360.0 + local1)
        EndIf
        local3 = (local0 - local1)
        If (arg4 = $00) Then
            If ((((0.0 < local3) And (180.0 > local3)) Or (-180.0 > local3)) <> 0) Then
                local2 = (local0 - arg2)
                turneddirection = $FFFFFFFF
            Else
                local2 = (local0 + arg2)
                turneddirection = $01
            EndIf
        ElseIf (arg4 = $FFFFFFFF) Then
            local2 = (local0 - arg2)
            turneddirection = $FFFFFFFF
        Else
            local2 = (local0 + arg2)
            turneddirection = $01
        EndIf
        If (((local2 > (local1 - arg2)) And (local2 < (local1 + arg2))) <> 0) Then
            local2 = local1
        EndIf
    EndIf
    Return local2
    Return 0.0
End Function
