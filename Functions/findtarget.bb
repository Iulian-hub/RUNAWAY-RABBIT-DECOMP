Function findtarget%(arg0#, arg1#, arg2#, arg3%, arg4%, arg5%, arg6%)
    Local local0%
    Local local1#
    Local local2#
    Local local3#
    Local local4%
    Local local5%
    Local local6#
    Local local7%
    foundtarget = $00
    local0 = $00
    If (arg3 = $00) Then
        local0 = $01
    EndIf
    If (local0 = $00) Then
        If (arg4 = $01) Then
            If (((actors(arg3)\Field41 = $00) Or (actors(arg3)\Field20 = $01)) <> 0) Then
                local0 = $01
            Else
                foundtargetx = actors(arg3)\Field48
                foundtargety = actors(arg3)\Field49
                foundtarget = arg3
                foundtargettype = arg4
            EndIf
        ElseIf (weaponshots(arg3)\Field71 = $00) Then
            local0 = $01
        Else
            foundtargetx = actors(arg3)\Field48
            foundtargety = actors(arg3)\Field49
            foundtarget = arg3
            foundtargettype = arg4
        EndIf
    EndIf
    local1 = 0.0
    local2 = 0.0
    local3 = 100000.0
    local4 = $00
    If (local0 = $01) Then
        For local5 = $01 To $14 Step $01
            If ((((actors(local5)\Field41 = $01) And (180.0 < actors(local5)\Field77)) And (actors(local5)\Field20 = $00)) <> 0) Then
                positionentity(mathent, arg0, arg1, 0.0, $00)
                positionentity(mathent2, actors(local5)\Field48, actors(local5)\Field49, 0.0, $00)
                local6 = entitydistance(mathent, mathent2)
                local7 = $00
                For arg1 = 1.0 To 10.0 Step 1.0
                    If (((weaponshotkinds(arg5)\Field64[(Int arg1)] > $00) And ((Float actors(local5)\Field30) = arg1)) <> 0) Then
                        local7 = $01
                    EndIf
                Next
                If (((local3 > local6) And (local7 = $01)) <> 0) Then
                    local1 = actors(local5)\Field48
                    local2 = actors(local5)\Field49
                    local3 = local6
                    local4 = local5
                EndIf
            EndIf
        Next
        If (((local4 > $00) And (100000.0 > local3)) <> 0) Then
            foundtargettype = $01
            foundtarget = local4
            foundtargetx = local1
            foundtargety = local2
        EndIf
        If (arg6 = $01) Then
            For local5 = $01 To $64 Step $01
                If (((weaponshots(local5)\Field71 = $01) And (weaponshots(local5)\Field65 > $00)) <> 0) Then
                    positionentity(mathent, arg0, arg1, 0.0, $00)
                    positionentity(mathent2, weaponshots(local5)\Field22, weaponshots(local5)\Field23, 0.0, $00)
                    local6 = entitydistance(mathent, mathent2)
                    local7 = $00
                    For arg1 = 1.0 To 10.0 Step 1.0
                        If (((weaponshotkinds(arg5)\Field64[(Int arg1)] > $00) And ((Float weaponshots(local5)\Field65) = arg1)) <> 0) Then
                            local7 = $01
                        EndIf
                    Next
                    If (((local3 > local6) And (local7 = $01)) <> 0) Then
                        local1 = weaponshots(local5)\Field22
                        local2 = weaponshots(local5)\Field23
                        local3 = local6
                        local4 = local5
                    EndIf
                EndIf
            Next
            If (((local4 > $00) And (100000.0 > local3)) <> 0) Then
                foundtargettype = $02
                foundtarget = local4
                foundtargetx = local1
                foundtargety = local2
            EndIf
        EndIf
    EndIf
    Return $00
End Function
