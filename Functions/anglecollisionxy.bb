Function anglecollisionxy%(arg0#, arg1#, arg2#, arg3#, arg4#, arg5#, arg6#, arg7#, arg8#, arg9#)
    Local local0#
    Local local1#
    Local local2#
    Local local3#
    Local local4#
    Local local5#
    Local local6#
    Local local7#
    Local local8%
    Local local9%
    Local local10#
    Local local11#
    Local local12#
    Local local13#
    Local local14#
    Local local15#
    Local local16%
    Local local17%
    Local local18%
    Local local19%
    local0 = 0.0
    local1 = 0.0
    If (arg2 < (arg3 / 4.0)) Then
        local0 = arg2
    EndIf
    If (arg3 < (arg2 / 4.0)) Then
        local0 = arg3
    EndIf
    If (arg7 < (arg8 / 4.0)) Then
        local1 = arg7
    EndIf
    If (arg8 < (arg7 / 4.0)) Then
        local1 = arg8
    EndIf
    If (((0.0 = local0) And (0.0 = local1)) <> 0) Then
        local2 = ((arg2 + arg3) / 2.0)
        local3 = ((arg7 + arg8) / 2.0)
        Return fastcollisionxy((Int arg0), (Int arg1), (Int local2), (Int local2), (Int arg5), (Int arg6), (Int local3), (Int local3))
    EndIf
    If (arg3 < arg2) Then
        local4 = arg2
    Else
        local4 = arg3
    EndIf
    If (arg8 < arg7) Then
        local5 = arg7
    Else
        local5 = arg8
    EndIf
    If (fastcollisionxy((Int arg0), (Int arg1), (Int local4), (Int local4), (Int arg5), (Int arg6), (Int local5), (Int local5)) = $00) Then
        Return $00
    EndIf
    If (0.0 = local0) Then
        If (arg3 > arg2) Then
            local0 = arg2
        EndIf
        If (arg2 > arg3) Then
            local0 = arg3
        EndIf
        If (0.0 = local0) Then
            local0 = arg2
        EndIf
    EndIf
    If (0.0 = local1) Then
        If (arg8 > arg7) Then
            local1 = arg7
        EndIf
        If (arg7 > arg8) Then
            local1 = arg8
        EndIf
        If (0.0 = local1) Then
            local1 = arg7
        EndIf
    EndIf
    If (arg2 = local0) Then
        local6 = (arg3 / local0)
    EndIf
    If (arg3 = local0) Then
        local6 = (arg2 / local0)
    EndIf
    If (arg7 = local1) Then
        local7 = (arg8 / local1)
    EndIf
    If (arg8 = local1) Then
        local7 = (arg7 / local1)
    EndIf
    local8 = (Int ceil(local6))
    local9 = (Int ceil(local7))
    If (local8 < $01) Then
        local8 = $01
    EndIf
    If (local9 < $01) Then
        local9 = $01
    EndIf
    If (local8 > $64) Then
        local8 = $64
    EndIf
    If (local9 > $64) Then
        local9 = $64
    EndIf
    local10 = 0.0
    local11 = 0.0
    local12 = arg3
    local13 = arg8
    If (arg3 < arg2) Then
        local10 = 90.0
        local12 = arg2
    EndIf
    If (arg8 < arg7) Then
        local11 = 90.0
        local13 = arg7
    EndIf
    If (local8 > $01) Then
        local14 = 0.0
        local15 = 0.0
        rotateentity(mathent, 0.0, 0.0, (((- arg4) + 90.0) + local10), $00)
        tformnormal(0.0, 1.0, 0.0, mathent, $00)
        local14 = (local14 - (((local12 / 2.0) + (local0 / 2.0)) * tformedx()))
        local15 = (local15 - (((local12 / 2.0) + (local0 / 2.0)) * tformedy()))
        For local16 = $01 To local8 Step $01
            local14 = ((local0 * tformedx()) + local14)
            local15 = ((local0 * tformedy()) + local15)
            e1box($00, local16) = (arg0 + local14)
            e1box($01, local16) = (arg1 + local15)
        Next
    Else
        e1box($00, $01) = arg0
        e1box($01, $01) = arg1
    EndIf
    If (local9 > $01) Then
        local14 = 0.0
        local15 = 0.0
        rotateentity(mathent, 0.0, 0.0, (((- arg9) + 90.0) + local11), $00)
        tformnormal(0.0, 1.0, 0.0, mathent, $00)
        local14 = (local14 - (((local13 / 2.0) + (local1 / 2.0)) * tformedx()))
        local15 = (local15 - (((local13 / 2.0) + (local1 / 2.0)) * tformedy()))
        For local16 = $01 To local9 Step $01
            local14 = ((local1 * tformedx()) + local14)
            local15 = ((local1 * tformedy()) + local15)
            e2box($00, local16) = (arg5 + local14)
            e2box($01, local16) = (arg6 + local15)
        Next
    Else
        e2box($00, $01) = arg5
        e2box($01, $01) = arg6
    EndIf
    local17 = $00
    For local18 = $01 To local8 Step $01
        For local19 = $01 To local9 Step $01
            If (fastcollisionxy((Int e1box($00, local18)), (Int e1box($01, local18)), (Int local0), (Int local0), (Int e2box($00, local19)), (Int e2box($01, local19)), (Int local1), (Int local1)) <> 0) Then
                local17 = $01
                Exit
            EndIf
        Next
        If (local17 = $01) Then
            Exit
        EndIf
    Next
    Return local17
    Return $00
End Function
