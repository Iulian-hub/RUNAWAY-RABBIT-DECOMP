Function dobosses%()
    Local local0%
    Local local1.bosstype
    Local local2%
    Local local3%
    Local local4%
    Local local5#
    Local local6#
    Local local7#
    Local local8%
    For local0 = $01 To $03 Step $01
        local1 = boss(local0)
        If (local1\Field0 = $01) Then
            local1\Field11 = 0.0
            For local2 = $01 To $0A Step $01
                If (local1\Field12[local2] = $01) Then
                    If (actors(local1\Field13[local2])\Field41 = $01) Then
                        local1\Field11 = (actors(local1\Field13[local2])\Field59 + local1\Field11)
                        entityorder(actors(local1\Field13[local2])\Field42, (((local0 * $0C) + $3C) + ($0A - local2)))
                    Else
                        local1\Field12[local2] = $00
                        If (local1\Field20[local2] > $00) Then
                            If (player($01)\Field1 = actors(local1\Field13[local2])\Field79) Then
                                player($01)\Field11 = (player($01)\Field11 + local1\Field20[local2])
                                makescoreflyup((local1\Field15[local2] + local1\Field3), (local1\Field16[local2] + local1\Field4), ("$" + (Str local1\Field20[local2])), $64, $FF, $64, $0F, $00, 0.0, $00, 0.0075, 1.0, 7.0, $00)
                            EndIf
                            If (player($02)\Field1 = actors(local1\Field13[local2])\Field79) Then
                                player($02)\Field11 = (player($02)\Field11 + local1\Field20[local2])
                                makescoreflyup((local1\Field15[local2] + local1\Field3), (local1\Field16[local2] + local1\Field4), ("$" + (Str local1\Field20[local2])), $FF, $64, $64, $0F, $00, 0.0, $00, 0.0075, 1.0, 7.0, $00)
                            EndIf
                        EndIf
                        For local3 = $01 To $0A Step $01
                            If (partondeathdestroy(local1\Field2, local2, local3) > $00) Then
                                actors(local1\Field13[local3])\Field59 = 0.0
                                actors(local1\Field13[local3])\Field35 = 0.0
                                local1\Field12[local3] = $00
                            EndIf
                        Next
                    EndIf
                EndIf
            Next
            If (bossbarnum = $00) Then
                bossbarnum = local0
            EndIf
            If (bossbarnum = local0) Then
                bossbarvalue = ((local1\Field11 / local1\Field10) * 100.0)
            EndIf
            local4 = (Int (60.0 / fl\Field1))
            If (local4 < $02) Then
                local4 = $02
            EndIf
            If (rand($01, local4) = $02) Then
                local1\Field6 = (Float rand($FFFFFF06, $FA))
                local1\Field6 = (local1\Field6 + local1\Field8)
                local1\Field7 = 200.0
            EndIf
            local4 = (Int (120.0 / fl\Field1))
            If (local4 < $02) Then
                local4 = $02
            EndIf
            If (rand($01, local4) = $02) Then
                local1\Field9 = (rnd(0.5, 1.0) * bosstypes(local1\Field2)\Field9)
            EndIf
            local5 = ((- getangle(local1\Field3, local1\Field4, local1\Field6, local1\Field7)) + 90.0)
            local1\Field5 = reachangle(local1\Field5, local5, (local1\Field9 * fl\Field1), $00, $00)
            rotateentity(mathent, 0.0, 0.0, (- local1\Field5), $00)
            tformnormal(0.0, 1.0, 0.0, mathent, $00)
            local6 = (local1\Field9 * tformedx())
            local7 = (local1\Field9 * tformedy())
            local1\Field3 = ((local6 * fl\Field1) + local1\Field3)
            local1\Field4 = ((local7 * fl\Field1) + local1\Field4)
            For local2 = $01 To $0A Step $01
                If (local1\Field12[local2] = $01) Then
                    actors(local1\Field13[local2])\Field46 = 0.0
                    actors(local1\Field13[local2])\Field47 = 0.0
                    If (local1\Field18[local2] = $00) Then
                        actors(local1\Field13[local2])\Field48 = (local1\Field15[local2] + local1\Field3)
                    EndIf
                    If (local1\Field19[local2] = $00) Then
                        actors(local1\Field13[local2])\Field49 = (local1\Field16[local2] + local1\Field4)
                    EndIf
                    positionentity(actors(local1\Field13[local2])\Field42, actors(local1\Field13[local2])\Field48, actors(local1\Field13[local2])\Field49, 0.0, $00)
                EndIf
            Next
            dobossai(local1, local0)
            If (0.0 >= local1\Field11) Then
                local8 = $00
                For local2 = $01 To $0A Step $01
                    If (local1\Field12[local2] = $01) Then
                        local8 = $01
                    EndIf
                Next
                If (local8 = $00) Then
                    local1\Field0 = $00
                    If (bossbarnum = local0) Then
                        bossbarnum = $00
                    EndIf
                    bossesdead = (bossesdead + $01)
                    If (soundon = $01) Then
                        playsoundoveride(sndbigbomb)
                    EndIf
                    whiteflash(1.0, (1.0 / 180.0), 0.9, $0A)
                    cameramain\Field7 = (cameramain\Field7 + 15.0)
                    If (specialmode = $01) Then
                        makebonus(local1\Field3, local1\Field4, $07)
                    EndIf
                EndIf
            EndIf
        EndIf
    Next
    Return $00
End Function
