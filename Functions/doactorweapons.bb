Function doactorweapons%(arg0.actortype, arg1%)
    Local local0%
    Local local1%
    Local local2#
    Local local3#
    Local local4#
    Local local5#
    Local local6#
    Local local7#
    Local local8#
    Local local9#
    Local local10#
    Local local11#
    Local local12#
    Local local13#
    Local local14#
    Local local15#
    Local local16#
    local0 = $00
    If (arg0\Field32 = $01) Then
        local0 = $01
    EndIf
    For local1 = $01 To $0A Step $01
        If (arg0\Field61[local1] > $00) Then
            arg0\Field74[local1] = weaponshotkinds(arg0\Field61[local1])\Field70
            If (arg0\Field62[local1] > $00) Then
                local2 = arg0\Field71[local1]
                local3 = arg0\Field72[local1]
                tformnormal(0.0, 1.0, 0.0, arg0\Field42, $00)
                local4 = (local3 * tformedx())
                local5 = (local3 * tformedy())
                tformnormal(1.0, 0.0, 0.0, arg0\Field42, $00)
                local6 = (local2 * tformedx())
                local7 = (local2 * tformedy())
                local8 = ((arg0\Field48 + local4) + local6)
                local9 = ((arg0\Field49 + local5) + local7)
                If (maingameview = $01) Then
                    local8 = (arg0\Field48 + local2)
                    local9 = (arg0\Field49 + local3)
                    If (((0.0 < wrapangle(arg0\Field50)) And (181.0 > wrapangle(arg0\Field50))) <> 0) Then
                        local8 = (arg0\Field48 - local2)
                    EndIf
                EndIf
                If (arg0\Field67[local1] = $01) Then
                    findtarget(local8, local9, 1000.0, $00, $00, arg0\Field61[local1], arg0\Field63[local1])
                    If (foundtarget > $00) Then
                        local10 = ((- getangle(local8, local9, foundtargetx, foundtargety)) + 90.0)
                        arg0\Field65[local1] = reachangle(arg0\Field65[local1], local10, (arg0\Field69[local1] * fl\Field1), $00, $00)
                    Else
                        local10 = arg0\Field65[local1]
                    EndIf
                    rotateentity(arg0\Field70[local1], 0.0, 0.0, local10, $00)
                    rotatesprite(arg0\Field70[local1], (- local10))
                Else
                    local10 = arg0\Field65[local1]
                    rotateentity(arg0\Field70[local1], 0.0, 0.0, local10, $00)
                    rotatesprite(arg0\Field70[local1], (- local10))
                EndIf
            EndIf
            arg0\Field75[local1] = (arg0\Field75[local1] + fl\Field1)
            local11 = (arg0\Field74[local1] * arg0\Field76[local1])
            If ((((local0 = $01) Or arg0\Field68[local1]) And (local11 < arg0\Field75[local1])) <> 0) Then
                arg0\Field75[local1] = 0.0
                If (arg0\Field62[local1] = $00) Then
                    local2 = arg0\Field71[local1]
                    local3 = arg0\Field72[local1]
                    tformnormal(0.0, 1.0, 0.0, arg0\Field42, $00)
                    local4 = (local3 * tformedx())
                    local5 = (local3 * tformedy())
                    tformnormal(1.0, 0.0, 0.0, arg0\Field42, $00)
                    local6 = (local2 * tformedx())
                    local7 = (local2 * tformedy())
                    local8 = ((arg0\Field48 + local4) + local6)
                    local9 = ((arg0\Field49 + local5) + local7)
                    If (maingameview = $01) Then
                        local8 = (arg0\Field48 + local2)
                        local9 = (arg0\Field49 + local3)
                        If (((0.0 < wrapangle(arg0\Field50)) And (181.0 > wrapangle(arg0\Field50))) <> 0) Then
                            local8 = (arg0\Field48 - local2)
                        EndIf
                    EndIf
                EndIf
                local12 = (arg0\Field73[local1] + arg0\Field50)
                local13 = 0.0
                If (arg0\Field62[local1] > $00) Then
                    local12 = (arg0\Field65[local1] + arg0\Field73[local1])
                    local13 = arg0\Field66[local1]
                    local14 = arg0\Field65[local1]
                Else
                    local14 = local12
                EndIf
                If (weaponshotkinds(arg0\Field61[local1])\Field25 > $00) Then
                    local13 = weaponshotkinds(arg0\Field61[local1])\Field32
                EndIf
                rotateentity(mathent, 0.0, 0.0, (- local14), $00)
                tformnormal(0.0, 1.0, 0.0, mathent, $00)
                local15 = (local13 * tformedx())
                local16 = (local13 * tformedy())
                local8 = (local8 + local15)
                local9 = (local9 + local16)
                makeweaponshot(local8, local9, local12, arg0\Field61[local1], arg1, local1, local13)
            EndIf
        EndIf
    Next
    Return $00
End Function
