Function dobonus%()
    Local local0%
    Local local1%
    Local local2%
    Local local3%
    Local local4%
    Local local5%
    For local0 = $01 To $0A Step $01
        If (bonus(local0)\Field0 = $01) Then
            showentity(bonus(local0)\Field1)
            If (0.0 = bonus(local0)\Field6) Then
                bonus(local0)\Field4 = ((bonus(local0)\Field5 * fl\Field1) + bonus(local0)\Field4)
            EndIf
            bonus(local0)\Field5 = (bonus(local0)\Field5 - (0.1 * fl\Field1))
            If (((-280.0 > bonus(local0)\Field4) And (bonus(local0)\Field8 = $00)) <> 0) Then
                bonus(local0)\Field8 = $01
                bonus(local0)\Field5 = 5.0
            EndIf
            If (0.0 > bonus(local0)\Field6) Then
                bonus(local0)\Field7 = ((bonus(local0)\Field6 * fl\Field1) + bonus(local0)\Field7)
                scalesprite(bonus(local0)\Field1, bonus(local0)\Field7, bonus(local0)\Field7)
                If (3.0 > bonus(local0)\Field7) Then
                    bonus(local0)\Field0 = $00
                EndIf
            Else
                For local1 = $01 To $02 Step $01
                    If ((player(local1)\Field0 And (player(local1)\Field16 = $00)) <> 0) Then
                        If (((-260.0 < bonus(local0)\Field4) And fastcollisionxy((Int player(local1)\Field4), (Int player(local1)\Field5), (Int actors(player(local1)\Field1)\Field53), (Int actors(player(local1)\Field1)\Field54), (Int bonus(local0)\Field3), (Int bonus(local0)\Field4), (Int (bonus(local0)\Field7 * 0.66)), (Int (bonus(local0)\Field7 * 0.66)))) <> 0) Then
                            local2 = $00
                            If (local1 = $01) Then
                                local3 = $64
                                local4 = $FF
                                local5 = $64
                            Else
                                local3 = $FF
                                local4 = $64
                                local5 = $64
                            EndIf
                            Select bonus(local0)\Field2
                                Case $01
                                    If (((((player(local1)\Field28 + playersetup(local1)\Field4) < $0A) And ((player(local1)\Field28 < $03) Or (specialmode > $00))) And ((playersetup(local1)\Field4 > $00) Or (specialmode > $00))) <> 0) Then
                                        local2 = $01
                                        player(local1)\Field28 = (player(local1)\Field28 + $01)
                                        makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "Cannon Upgrade", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                    EndIf
                                Case $02
                                    If (((((player(local1)\Field29 + playersetup(local1)\Field5) < $05) And ((player(local1)\Field29 < $02) Or (specialmode > $00))) And ((playersetup(local1)\Field5 > $00) Or (specialmode > $00))) <> 0) Then
                                        local2 = $01
                                        player(local1)\Field29 = (player(local1)\Field29 + $01)
                                        makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "Missile Upgrade", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                    EndIf
                                Case $03
                                    If (((((player(local1)\Field30 + playersetup(local1)\Field6) < $05) And ((player(local1)\Field30 < $02) Or (specialmode > $00))) And ((playersetup(local1)\Field6 > $00) Or (specialmode > $00))) <> 0) Then
                                        local2 = $01
                                        player(local1)\Field30 = (player(local1)\Field30 + $01)
                                        makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "B-Laser Upgrade", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                    EndIf
                                Case $04
                                    If (((((player(local1)\Field32 + playersetup(local1)\Field8) < $05) And ((player(local1)\Field32 < $02) Or (specialmode > $00))) And ((playersetup(local1)\Field8 > $00) Or (specialmode > $00))) <> 0) Then
                                        local2 = $01
                                        player(local1)\Field32 = (player(local1)\Field32 + $01)
                                        makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "Turret Upgrade", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                    EndIf
                                Case $05
                                    If (((((player(local1)\Field31 + playersetup(local1)\Field7) < $05) And ((player(local1)\Field31 < $02) Or (specialmode > $00))) And ((playersetup(local1)\Field7 > $00) Or (specialmode > $00))) <> 0) Then
                                        local2 = $01
                                        player(local1)\Field31 = (player(local1)\Field31 + $01)
                                        makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "W-Laser Upgrade", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                    EndIf
                                Case $06
                                    If (player(local1)\Field9 < $04) Then
                                        local2 = $01
                                        player(local1)\Field9 = (player(local1)\Field9 + $01)
                                        makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "Extra Shield", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                    EndIf
                                Case $07
                                    If (player(local1)\Field10 < $04) Then
                                        local2 = $01
                                        player(local1)\Field10 = (player(local1)\Field10 + $01)
                                        makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "Extra Life", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                    EndIf
                                Case $08
                                    If (player(local1)\Field21 < $04) Then
                                        local2 = $01
                                        player(local1)\Field21 = (player(local1)\Field21 + $01)
                                        makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "Extra Bomb", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                    EndIf
                                Case $09
                                    local2 = $01
                                    player(local1)\Field12 = 900.0
                                    player(local1)\Field15 = $FFFFFFFF
                                    makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "Mega Shield!", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                Case $0A
                                    local2 = $01
                                    player(local1)\Field13 = 900.0
                                    makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "Mega Upgrade!!!", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                Case $0B
                                    local2 = $01
                                    makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "$100", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                    player(local1)\Field11 = (player(local1)\Field11 + $64)
                                Case $0C
                                    local2 = $01
                                    makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "$250", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                    player(local1)\Field11 = (player(local1)\Field11 + $FA)
                                Case $0D
                                    local2 = $01
                                    makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "$500", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                    player(local1)\Field11 = (player(local1)\Field11 + $1F4)
                                Case $0E
                                    local2 = $01
                                    makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "$1000", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                    player(local1)\Field11 = (player(local1)\Field11 + $3E8)
                            End Select
                            If (local2 = $01) Then
                                bonus(local0)\Field6 = -0.8
                                If (soundon = $01) Then
                                    If (bonus(local0)\Field2 > $0A) Then
                                        playsoundoveride(sndbuy)
                                    Else
                                        playsoundoveride(sndpowerup)
                                    EndIf
                                EndIf
                                doplayerweaponsetup(player(local1), local1)
                            Else
                                makescoreflyup(bonus(local0)\Field3, (bonus(local0)\Field4 + 20.0), "$3000", local3, local4, local5, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                                player(local1)\Field11 = (player(local1)\Field11 + $BB8)
                                bonus(local0)\Field6 = -0.8
                                If (soundon = $01) Then
                                    If (bonus(local0)\Field2 > $0A) Then
                                        playsoundoveride(sndbuy)
                                    Else
                                        playsoundoveride(sndpowerup)
                                    EndIf
                                EndIf
                            EndIf
                        EndIf
                    EndIf
                Next
            EndIf
            positionentity(bonus(local0)\Field1, bonus(local0)\Field3, bonus(local0)\Field4, 0.0, $00)
            If (-400.0 > bonus(local0)\Field4) Then
                bonus(local0)\Field0 = $00
            EndIf
        Else
            hideentity(bonus(local0)\Field1)
        EndIf
    Next
    Return $00
End Function
