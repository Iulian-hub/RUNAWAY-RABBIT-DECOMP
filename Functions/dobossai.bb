Function dobossai%(arg0.bosstype, arg1%)
    Local local1%
    Local local2.actortype
    Local local4%
    Local local5%
    Local local6%
    Local local7%
    Local local8%
    Select arg0\Field2
        Case $01
        Case $02
            For local1 = $01 To $02 Step $01
                local2 = actors(arg0\Field13[local1])
                If (arg0\Field12[local1] = $01) Then
                    local2\Field59 = 2000.0
                EndIf
                Select arg0\Field21[(((local1 - $01) * $05) + $01)]
                    Case 0.0
                        local2\Field49 = (arg0\Field16[local1] + arg0\Field4)
                        If (rand($01, (Int (300.0 / fl\Field1))) = $02) Then
                            arg0\Field21[(((local1 - $01) * $05) + $01)] = 1.0
                            arg0\Field21[(((local1 - $01) * $05) + $02)] = 1.0
                        EndIf
                    Case 1.0
                        arg0\Field21[(((local1 - $01) * $05) + $02)] = (arg0\Field21[(((local1 - $01) * $05) + $02)] + (fl\Field1 * 0.6))
                        If (30.0 < arg0\Field21[(((local1 - $01) * $05) + $02)]) Then
                            arg0\Field21[(((local1 - $01) * $05) + $02)] = 30.0
                        EndIf
                        local2\Field49 = (local2\Field49 - (arg0\Field21[(((local1 - $01) * $05) + $02)] * fl\Field1))
                        If (-250.0 > local2\Field49) Then
                            If (soundon = $01) Then
                                playsoundoveride(sndrumblehit)
                            EndIf
                            local2\Field49 = -250.0
                            arg0\Field21[(((local1 - $01) * $05) + $01)] = 2.0
                            arg0\Field21[(((local1 - $01) * $05) + $02)] = 20.0
                            cameramain\Field7 = (cameramain\Field7 + 6.0)
                            makedust(local2\Field48, -270.0, $02)
                            makedust((local2\Field48 + 40.0), -270.0, $02)
                            makedust((local2\Field48 - 40.0), -270.0, $02)
                            If (90.0 = wrapangle(local2\Field50)) Then
                                makedust((local2\Field48 + 80.0), -270.0, $01)
                            EndIf
                            If (270.0 = wrapangle(local2\Field50)) Then
                                makedust((local2\Field48 - 80.0), -270.0, $01)
                            EndIf
                        EndIf
                    Case 2.0
                        arg0\Field21[(((local1 - $01) * $05) + $02)] = (arg0\Field21[(((local1 - $01) * $05) + $02)] - (fl\Field1 * 0.6))
                        If (1.0 > arg0\Field21[(((local1 - $01) * $05) + $02)]) Then
                            arg0\Field21[(((local1 - $01) * $05) + $02)] = 1.0
                        EndIf
                        local2\Field49 = ((arg0\Field21[(((local1 - $01) * $05) + $02)] * fl\Field1) + local2\Field49)
                        If (local2\Field49 > (arg0\Field16[local1] + arg0\Field4)) Then
                            arg0\Field21[(((local1 - $01) * $05) + $01)] = 0.0
                        EndIf
                End Select
                local4 = (Int ((arg0\Field16[local1] + arg0\Field4) - local2\Field49))
                local2\Field6 = $02
                local2\Field7 = $01
                entitytexture(local2\Field5, actordecals(local2\Field6), $00, $00)
                scalesprite(local2\Field5, 20.0, (Float (local4 Sar $01)))
                positionentity(local2\Field5, (Float ((- (local4 Sar $01)) - $0A)), 0.0, 0.0, $00)
                If (270.0 = wrapangle(local2\Field50)) Then
                    positionentity(local2\Field5, (Float ((local4 Sar $01) + $0A)), 0.0, 0.0, $00)
                EndIf
                entityorder(local2\Field5, ((((arg1 * $0C) + $3C) + ($0A - local1)) + $05))
                showentity(local2\Field5)
                If (local2\Field49 > (arg0\Field16[local1] + arg0\Field4)) Then
                    local2\Field49 = (arg0\Field16[local1] + arg0\Field4)
                EndIf
                positionentity(local2\Field42, local2\Field48, local2\Field49, 0.0, $00)
            Next
        Case $03
            If (0.0 = arg0\Field21[$01]) Then
                arg0\Field21[$01] = 1.0
            EndIf
            arg0\Field15[$02] = (((arg0\Field21[$01] * 2.0) * fl\Field1) + arg0\Field15[$02])
            If (140.0 < arg0\Field15[$02]) Then
                arg0\Field15[$02] = 140.0
                arg0\Field21[$01] = -1.0
            EndIf
            If (-145.0 > arg0\Field15[$02]) Then
                arg0\Field15[$02] = -145.0
                arg0\Field21[$01] = 1.0
            EndIf
            actors(arg0\Field13[$01])\Field65[$02] = actors(arg0\Field13[$01])\Field65[$01]
            actors(arg0\Field13[$01])\Field65[$03] = actors(arg0\Field13[$01])\Field65[$01]
        Case $04
            arg0\Field21[$01] = (arg0\Field21[$01] + fl\Field1)
            If (300.0 < arg0\Field21[$01]) Then
                arg0\Field21[$02] = (arg0\Field21[$02] + 1.0)
                arg0\Field21[$01] = 0.0
                local5 = $10E
                If (rand($01, $02) = $02) Then
                    local5 = $5A
                EndIf
                makeenemy($0B, arg0\Field3, (arg0\Field4 - 100.0), (Float local5), $01, $00)
            EndIf
        Case $05
            local6 = $01
            For local1 = $01 To $04 Step $01
                If (arg0\Field12[local1] = $01) Then
                    If (local1 > local6) Then
                        local6 = local1
                    EndIf
                    local7 = $01
                    If (local6 = $01) Then
                        local7 = $00
                    EndIf
                    arg0\Field8 = ((- arg0\Field15[local6]) + (Float ($64 * local7)))
                    If (local1 > $01) Then
                        local8 = (local1 - $01)
                        While (local8 >= $01)
                            actors(arg0\Field13[local8])\Field59 = actors(arg0\Field13[local8])\Field58
                            local8 = (local8 + $FFFFFFFF)
                        Wend
                    EndIf
                EndIf
            Next
    End Select
    Return $00
End Function
