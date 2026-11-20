Function makeparticleburst%(arg0#, arg1#, arg2#, arg3#, arg4#, arg5%, arg6%, arg7#, arg8#, arg9#, arg10#, arg11#, arg12%)
    Local local0#
    Local local1#
    Local local2%
    Local local3#
    Local local4#
    Local local5#
    Local local6#
    local0 = 0.0
    local1 = (Float ($168 / arg5))
    For local2 = $01 To arg5 Step $01
        If (arg12 = $01) Then
            local0 = rnd(1.0, 90.0)
        EndIf
        local3 = (((Float local2) * local1) + local0)
        local4 = 0.0
        local5 = arg7
        If (arg12 = $01) Then
            local5 = ((Float rand((Int (local6 / 1.5)), (Int (arg7 * 1.5)))) + arg7)
        EndIf
        makeparticle(arg0, arg1, local4, local3, arg2, arg3, arg4, arg6, local5, arg8, arg9, arg10, arg11, $00)
    Next
    Return $00
End Function
