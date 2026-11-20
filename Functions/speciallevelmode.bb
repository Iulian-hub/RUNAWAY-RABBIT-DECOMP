Function speciallevelmode%(arg0%)
    Local local1%
    If ((((((((((currentlevel = $01) Or (0.0 < getreadytime)) Or (0.0 < gameovertime)) Or (currentmenu > $00)) Or (0.0 < wintime)) Or (willwin > $00)) Or (0.0 < bombtime)) Or (speciallevelmode > $00)) Or (gamemode = $03)) <> 0) Then
        Return $00
    EndIf
    speciallevelmode = arg0
    leveltimewait = $01
    Select arg0
        Case $01
            speciallevelmodetime = 900.0
            makescoreflyup(0.0, 0.0, "COIN DROP!!!", $32, $DC, $DC, $19, $00, 3.0, $02, 0.03, 2.0, 11.0, $01)
        Case $02
            speciallevelmodetime = 600.0
            makescoreflyup(0.0, 0.0, "LOOK OUT!!!", $32, $DC, $DC, $19, $00, 3.0, $02, 0.03, 2.0, 11.0, $01)
            local1 = rand($01, $02)
            Select local1
                Case $01
                    speciallevelmodeenemytype = $0A
                    speciallevelmodenumber = $02
                Case $02
                    speciallevelmodeenemytype = $11
                    speciallevelmodenumber = $03
            End Select
            If (currentlevel > $03) Then
                local1 = rand($01, $02)
                Select local1
                    Case $01
                        speciallevelmodeenemytype = $0A
                        speciallevelmodenumber = $03
                    Case $02
                        speciallevelmodeenemytype = $11
                        speciallevelmodenumber = $05
                End Select
            EndIf
            If (currentlevel > $07) Then
                local1 = rand($01, $02)
                Select local1
                    Case $01
                        speciallevelmodeenemytype = $0A
                        speciallevelmodenumber = $04
                    Case $02
                        speciallevelmodeenemytype = $11
                        speciallevelmodenumber = $06
                End Select
            EndIf
            If (currentlevel > $0D) Then
                local1 = rand($01, $02)
                Select local1
                    Case $01
                        speciallevelmodeenemytype = $0A
                        speciallevelmodenumber = $06
                    Case $02
                        speciallevelmodeenemytype = $12
                        speciallevelmodenumber = $06
                End Select
            EndIf
    End Select
    Return $00
End Function
