Function doenemiesend%(arg0.actortype, arg1%)
    Local local0%
    Local local1%
    Local local2%
    Local local3%
    Local local5%
    Local local6#
    Local local7#
    Local local8#
    Local local9%
    Local local11%
    Local local12%
    If (arg0\Field3 > $00) Then
        If (actors(arg0\Field3)\Field41 = $00) Then
            arg0\Field41 = $00
        EndIf
    EndIf
    If (((arg0\Field43 > $02) And (arg0\Field43 < $1E)) <> 0) Then
        enemies(arg1)\Field3 = (enemies(arg1)\Field3 + fl\Field1)
        actors(arg1)\Field46 = actors(arg1)\Field47
        If (600.0 < actors(arg1)\Field48) Then
            local0 = $FFFFFFCE
            local1 = $113
            If (enemytypes((arg0\Field43 - $02))\Field0 <> $00) Then
                local0 = enemytypes((arg0\Field43 - $02))\Field0
            EndIf
            If (enemytypes((arg0\Field43 - $02))\Field1 <> $00) Then
                local1 = enemytypes((arg0\Field43 - $02))\Field1
            EndIf
            If (((enemies(arg1)\Field6 = $01) And (actors(arg1)\Field3 = $00)) <> 0) Then
                local2 = makeenemy((arg0\Field43 - $02), 500.0, (Float rand(local0, local1)), 270.0, $00, $01)
                actors(local2)\Field59 = actors(arg1)\Field59
                actors(local2)\Field58 = actors(arg1)\Field58
                If (((arg0\Field43 <> $06) And (arg0\Field43 <> $05)) <> 0) Then
                    For local3 = $01 To $0A Step $01
                        actors(local2)\Field76[local3] = actors(arg1)\Field76[local3]
                    Next
                EndIf
            EndIf
            If (actors(arg1)\Field2 > $00) Then
                actors(actors(arg1)\Field2)\Field41 = $00
            EndIf
            actors(arg1)\Field41 = $00
            If (actors(arg1)\Field3 = $00) Then
                currentenemies = (currentenemies - $01)
            EndIf
            If (actors(arg1)\Field3 = $00) Then
                currentenemiestype(getenemiestype((actors(arg1)\Field43 - $02))) = (currentenemiestype(getenemiestype((actors(arg1)\Field43 - $02))) - $01)
            EndIf
        EndIf
        If (-600.0 > actors(arg1)\Field48) Then
            local0 = $FFFFFFCE
            local1 = $113
            If (enemytypes((arg0\Field43 - $02))\Field0 <> $00) Then
                local0 = enemytypes((arg0\Field43 - $02))\Field0
            EndIf
            If (enemytypes((arg0\Field43 - $02))\Field1 <> $00) Then
                local1 = enemytypes((arg0\Field43 - $02))\Field1
            EndIf
            If (((enemies(arg1)\Field6 = $01) And (actors(arg1)\Field3 = $00)) <> 0) Then
                local2 = makeenemy((arg0\Field43 - $02), -500.0, (Float rand(local0, local1)), 90.0, $00, $01)
                actors(local2)\Field59 = actors(arg1)\Field59
                actors(local2)\Field58 = actors(arg1)\Field58
                If (((arg0\Field43 <> $06) And (arg0\Field43 <> $05)) <> 0) Then
                    For local3 = $01 To $0A Step $01
                        actors(local2)\Field76[local3] = actors(arg1)\Field76[local3]
                    Next
                EndIf
            EndIf
            If (actors(arg1)\Field2 > $00) Then
                actors(actors(arg1)\Field2)\Field41 = $00
            EndIf
            actors(arg1)\Field41 = $00
            If (actors(arg1)\Field3 = $00) Then
                currentenemies = (currentenemies - $01)
            EndIf
            If (actors(arg1)\Field3 = $00) Then
                currentenemiestype(getenemiestype((actors(arg1)\Field43 - $02))) = (currentenemiestype(getenemiestype((actors(arg1)\Field43 - $02))) - $01)
            EndIf
        EndIf
        actors(arg1)\Field32 = $01
    EndIf
    If (arg0\Field4 = $01) Then
        If (arg0\Field43 = $27) Then
            actors(arg1)\Field46 = actors(arg1)\Field47
            If (600.0 < actors(arg1)\Field48) Then
                actors(arg1)\Field41 = $00
            EndIf
        EndIf
        If (arg0\Field43 = $0C) Then
            arg0\Field46 = 0.0
            If (0.0 = enemies(arg1)\Field5[$01]) Then
                enemies(arg1)\Field5[$04] = -1.0
                enemies(arg1)\Field5[$01] = 1.0
                enemies(arg1)\Field5[$05] = rnd(8.0, 10.0)
                enemies(arg1)\Field5[$02] = 0.0
                enemies(arg1)\Field5[$06] = (Float rand($01, $02))
                If (2.0 = enemies(arg1)\Field5[$06]) Then
                    enemies(arg1)\Field5[$06] = -1.0
                EndIf
                arg0\Field23 = $00
                arg0\Field25 = $01
                arg0\Field26 = 0.0
            EndIf
            If (1.0 = enemies(arg1)\Field5[$06]) Then
                arg0\Field50 = 90.0
            Else
                arg0\Field50 = 270.0
            EndIf
            If (0.0 = enemies(arg1)\Field5[$02]) Then
                enemies(arg1)\Field5[$04] = (enemies(arg1)\Field5[$04] - (fl\Field1 * 0.1))
            EndIf
            If (enemies(arg1)\Field5[$05] < enemies(arg1)\Field5[$04]) Then
                enemies(arg1)\Field5[$04] = enemies(arg1)\Field5[$05]
            EndIf
            If ((- enemies(arg1)\Field5[$05]) > enemies(arg1)\Field5[$04]) Then
                enemies(arg1)\Field5[$04] = (- enemies(arg1)\Field5[$05])
            EndIf
            If ((((arg0\Field49 < (-250.0 + enemies(arg1)\Field5[$05])) And (0.0 = enemies(arg1)\Field5[$02])) And (enemies(arg1)\Field5[$04] < (enemies(arg1)\Field5[$05] / 2.0))) <> 0) Then
                arg0\Field23 = $01
                enemies(arg1)\Field5[$02] = 2.0
                If (soundon = $01) Then
                    playsoundoveride(sndboing)
                EndIf
                makedust(arg0\Field48, -270.0, $02)
                If (rand($01, $02) = $02) Then
                    enemies(arg1)\Field5[$06] = (enemies(arg1)\Field5[$06] * -1.0)
                EndIf
            EndIf
            If (0.0 = enemies(arg1)\Field5[$02]) Then
                arg0\Field49 = ((enemies(arg1)\Field5[$04] * fl\Field1) + arg0\Field49)
            EndIf
            If (0.0 <> enemies(arg1)\Field5[$02]) Then
                Select arg0\Field25
                    Case $01
                        arg0\Field49 = -250.0
                        If (1.0 = enemies(arg1)\Field5[$02]) Then
                            enemies(arg1)\Field5[$02] = 0.0
                        EndIf
                    Case $02
                        arg0\Field49 = -255.0
                        If (1.0 = enemies(arg1)\Field5[$02]) Then
                            enemies(arg1)\Field5[$02] = 0.0
                        EndIf
                    Case $03
                        arg0\Field49 = -260.0
                        If (1.0 = enemies(arg1)\Field5[$02]) Then
                            enemies(arg1)\Field5[$02] = 0.0
                        EndIf
                    Case $04
                        arg0\Field49 = -265.0
                        If (1.0 = enemies(arg1)\Field5[$02]) Then
                            enemies(arg1)\Field5[$02] = 0.0
                        EndIf
                    Case $05
                        arg0\Field49 = -255.0
                        If (1.0 = enemies(arg1)\Field5[$02]) Then
                            enemies(arg1)\Field5[$02] = 0.0
                        EndIf
                    Case $06
                        arg0\Field49 = -245.0
                        enemies(arg1)\Field5[$02] = 1.0
                End Select
                If (0.0 = enemies(arg1)\Field5[$02]) Then
                    arg0\Field25 = $01
                    arg0\Field26 = 0.0
                    arg0\Field49 = -235.0
                    enemies(arg1)\Field5[$04] = enemies(arg1)\Field5[$05]
                EndIf
            EndIf
            arg0\Field48 = (((arg0\Field47 * fl\Field1) * enemies(arg1)\Field5[$06]) + arg0\Field48)
            If ((((-300.0 > arg0\Field48) And (-1.0 = enemies(arg1)\Field5[$06])) And (300.0 > enemies(arg1)\Field3)) <> 0) Then
                enemies(arg1)\Field5[$06] = 1.0
            EndIf
            If ((((300.0 < arg0\Field48) And (1.0 = enemies(arg1)\Field5[$06])) And (300.0 > enemies(arg1)\Field3)) <> 0) Then
                enemies(arg1)\Field5[$06] = -1.0
            EndIf
        EndIf
        If (((arg0\Field43 = $13) Or (arg0\Field43 = $14)) <> 0) Then
            arg0\Field50 = 180.0
            arg0\Field47 = (((arg0\Field47 * 0.02) * fl\Field1) + arg0\Field47)
        EndIf
        If (arg0\Field43 = $06) Then
            If (90.0 = wrapangle(arg0\Field50)) Then
                arg0\Field65[$01] = 270.0
            Else
                arg0\Field65[$01] = 90.0
            EndIf
        EndIf
        If (arg0\Field43 = $08) Then
            If (0.0 = enemies(arg1)\Field5[$01]) Then
                If (90.0 = wrapangle(arg0\Field50)) Then
                    enemies(arg1)\Field5[$01] = 1.0
                    enemies(arg1)\Field5[$03] = (Float rand($FFFFFE70, $00))
                EndIf
                If (270.0 = wrapangle(arg0\Field50)) Then
                    enemies(arg1)\Field5[$01] = 2.0
                    enemies(arg1)\Field5[$03] = (Float rand($00, $190))
                EndIf
            EndIf
            If ((((enemies(arg1)\Field5[$03] < arg0\Field48) And (1.0 = enemies(arg1)\Field5[$01])) Or ((enemies(arg1)\Field5[$03] > arg0\Field48) And (2.0 = enemies(arg1)\Field5[$01]))) <> 0) Then
                enemies(arg1)\Field5[$02] = 1.0
            EndIf
            If (1.0 = enemies(arg1)\Field5[$02]) Then
                arg0\Field46 = (arg0\Field47 * 2.0)
                If (1.0 = enemies(arg1)\Field5[$01]) Then
                    arg0\Field50 = reachangle(arg0\Field50, 170.0, (((arg0\Field47 / 3.0) * fl\Field1) * ((((arg0\Field50 - 170.0) - 1.0) * -1.0) / 40.0)), $00, $00)
                Else
                    arg0\Field50 = reachangle(arg0\Field50, 190.0, ((((arg0\Field47 / 3.0) * fl\Field1) * ((arg0\Field50 - 190.0) + 1.0)) / 40.0), $00, $00)
                EndIf
            EndIf
        EndIf
        If (arg0\Field43 = $07) Then
            If (0.0 = enemies(arg1)\Field5[$01]) Then
                If (90.0 = wrapangle(arg0\Field50)) Then
                    enemies(arg1)\Field5[$01] = 1.0
                EndIf
                If (270.0 = wrapangle(arg0\Field50)) Then
                    enemies(arg1)\Field5[$01] = 2.0
                EndIf
            EndIf
            If (0.0 = enemies(arg1)\Field5[$02]) Then
                If (1.0 = enemies(arg1)\Field5[$01]) Then
                    arg0\Field50 = reachangle(arg0\Field50, 140.0, (((arg0\Field47 / 3.0) * fl\Field1) * ((((arg0\Field50 - 140.0) - 1.0) * -1.0) / 25.0)), $00, $00)
                    If (100.0 > arg0\Field49) Then
                        enemies(arg1)\Field5[$02] = 1.0
                    EndIf
                Else
                    arg0\Field50 = reachangle(arg0\Field50, 220.0, ((((arg0\Field47 / 3.0) * fl\Field1) * ((arg0\Field50 - 220.0) + 1.0)) / 25.0), $00, $00)
                    If (100.0 > arg0\Field49) Then
                        enemies(arg1)\Field5[$02] = 1.0
                    EndIf
                EndIf
            ElseIf (1.0 = enemies(arg1)\Field5[$02]) Then
                If (1.0 = enemies(arg1)\Field5[$01]) Then
                    arg0\Field50 = reachangle(arg0\Field50, 90.0, ((((arg0\Field47 / 3.0) * fl\Field1) * ((arg0\Field50 - 90.0) + 3.0)) / 40.0), $00, $00)
                    If (90.0 = wrapangle(arg0\Field50)) Then
                        enemies(arg1)\Field5[$02] = 2.0
                    EndIf
                Else
                    arg0\Field50 = reachangle(arg0\Field50, 270.0, ((((arg0\Field47 / 3.0) * fl\Field1) * (((arg0\Field50 - 270.0) - 3.0) * -1.0)) / 40.0), $00, $00)
                    If (270.0 = wrapangle(arg0\Field50)) Then
                        enemies(arg1)\Field5[$02] = 2.0
                    EndIf
                EndIf
            Else
                arg0\Field32 = $00
                If (1.0 = enemies(arg1)\Field5[$01]) Then
                    arg0\Field50 = reachangle(arg0\Field50, 70.0, ((((arg0\Field47 / 3.0) * fl\Field1) * (((arg0\Field50 - 90.0) - 3.0) * -1.0)) / 40.0), $00, $00)
                Else
                    arg0\Field50 = reachangle(arg0\Field50, 290.0, ((((arg0\Field47 / 3.0) * fl\Field1) * ((arg0\Field50 - 270.0) + 3.0)) / 40.0), $00, $00)
                EndIf
            EndIf
        EndIf
        If (((arg0\Field43 = $05) Or (arg0\Field43 = $06)) <> 0) Then
            If (0.0 = enemies(arg1)\Field5[$01]) Then
                enemies(arg1)\Field5[$01] = arg0\Field76[$01]
            EndIf
            actors(arg1)\Field47 = (((0.01 * fl\Field1) * actors(arg1)\Field47) + actors(arg1)\Field47)
            actors(arg1)\Field46 = actors(arg1)\Field47
            actors(arg1)\Field76[$01] = (enemies(arg1)\Field5[$01] / actors(arg1)\Field46)
        EndIf
        If (((arg0\Field43 = $0A) Or (arg0\Field43 = $0B)) <> 0) Then
            actors(arg1)\Field47 = (((0.01 * fl\Field1) * actors(arg1)\Field47) + actors(arg1)\Field47)
            actors(arg1)\Field46 = actors(arg1)\Field47
        EndIf
        If ((((((arg0\Field43 = $11) Or (arg0\Field43 = $0E)) Or (arg0\Field43 = $10)) Or (arg0\Field43 = $0F)) Or (arg0\Field43 = $0D)) <> 0) Then
            arg0\Field46 = 0.0
            local5 = (Int (60.0 / fl\Field1))
            If (local5 < $02) Then
                local5 = $02
            EndIf
            If (rand($01, local5) = $02) Then
                enemies(arg1)\Field0 = (Float rand($FFFFFF1F, $E1))
                enemies(arg1)\Field1 = (Float rand($32, $C8))
            EndIf
            local5 = (Int (120.0 / fl\Field1))
            If (local5 < $02) Then
                local5 = $02
            EndIf
            If (rand($01, local5) = $02) Then
                arg0\Field47 = (rnd(0.5, 1.0) * enemies(arg1)\Field4)
            EndIf
            If (((900.0 < enemies(arg1)\Field3) And (enemies(arg1)\Field6 = $00)) <> 0) Then
                enemies(arg1)\Field0 = 1700.0
            EndIf
            local6 = ((- getangle(arg0\Field48, arg0\Field49, enemies(arg1)\Field0, enemies(arg1)\Field1)) + 90.0)
            enemies(arg1)\Field2 = reachangle(enemies(arg1)\Field2, local6, (arg0\Field47 * fl\Field1), $00, $00)
            rotateentity(mathent, 0.0, 0.0, (- enemies(arg1)\Field2), $00)
            tformnormal(0.0, 1.0, 0.0, mathent, $00)
            local7 = (arg0\Field47 * tformedx())
            local8 = (arg0\Field47 * tformedy())
            arg0\Field48 = ((local7 * fl\Field1) + arg0\Field48)
            arg0\Field49 = ((local8 * fl\Field1) + arg0\Field49)
        EndIf
        If (arg0\Field43 = $12) Then
            If (0.0 = enemies(arg1)\Field5[$02]) Then
                enemies(arg1)\Field5[$03] = (Float rand($FFFFFF38, $C8))
            EndIf
            If (0.0 = enemies(arg1)\Field5[$01]) Then
                If (90.0 = wrapangle(arg0\Field50)) Then
                    actors(arg0\Field2)\Field48 = (arg0\Field48 + 22.0)
                EndIf
                If (270.0 = wrapangle(arg0\Field50)) Then
                    actors(arg0\Field2)\Field48 = (arg0\Field48 - 27.0)
                EndIf
                entityorder(actors(arg0\Field2)\Field42, ((arg1 * $03) + $01))
                actors(arg0\Field2)\Field49 = (arg0\Field49 - 80.0)
                actors(arg0\Field2)\Field50 = 180.0
                local9 = (Int (actors(arg0\Field2)\Field59 - actors(arg0\Field2)\Field58))
                If (local9 > $00) Then
                    arg0\Field59 = (arg0\Field59 - (Float local9))
                EndIf
                actors(arg0\Field2)\Field59 = actors(arg0\Field2)\Field58
                If ((((90.0 = wrapangle(arg0\Field50)) And (enemies(arg1)\Field5[$03] < arg0\Field48)) Or ((270.0 = wrapangle(arg0\Field50)) And (enemies(arg1)\Field5[$03] > arg0\Field48))) <> 0) Then
                    enemies(arg1)\Field5[$01] = 1.0
                    actors(arg0\Field2)\Field3 = $00
                    actors(arg0\Field2)\Field4 = $01
                    actors(arg0\Field2)\Field47 = 2.0
                    arg0\Field2 = $00
                    currentenemies = (currentenemies + $01)
                EndIf
            EndIf
            If (arg0\Field2 > $00) Then
                positionentity(actors(arg0\Field2)\Field42, actors(arg0\Field2)\Field48, actors(arg0\Field2)\Field49, 0.0, $00)
            EndIf
        EndIf
        If (arg0\Field43 = $11) Then
            actors(arg0\Field2)\Field59 = 2000.0
            If (90.0 = wrapangle(arg0\Field50)) Then
                actors(arg0\Field2)\Field48 = arg0\Field48
            EndIf
            If (270.0 = wrapangle(arg0\Field50)) Then
                actors(arg0\Field2)\Field48 = arg0\Field48
            EndIf
            Select enemies(arg1)\Field5[$01]
                Case 0.0
                    actors(arg0\Field2)\Field49 = (arg0\Field49 - 22.0)
                    If (rand($01, (Int (100.0 / fl\Field1))) = $02) Then
                        enemies(arg1)\Field5[$01] = 1.0
                        enemies(arg1)\Field5[$02] = 1.0
                    EndIf
                Case 1.0
                    enemies(arg1)\Field5[$02] = ((fl\Field1 * 0.6) + enemies(arg1)\Field5[$02])
                    If (30.0 < enemies(arg1)\Field5[$02]) Then
                        enemies(arg1)\Field5[$02] = 30.0
                    EndIf
                    actors(arg0\Field2)\Field49 = (actors(arg0\Field2)\Field49 - (enemies(arg1)\Field5[$02] * fl\Field1))
                    If (-250.0 > actors(arg0\Field2)\Field49) Then
                        actors(arg0\Field2)\Field49 = -250.0
                        If (soundon = $01) Then
                            playsoundoveride(sndrumblehit)
                        EndIf
                        enemies(arg1)\Field5[$01] = 2.0
                        enemies(arg1)\Field5[$02] = 20.0
                        cameramain\Field7 = (cameramain\Field7 + 6.0)
                        makedust(actors(arg0\Field2)\Field48, -270.0, $02)
                        makedust((actors(arg0\Field2)\Field48 + 40.0), -270.0, $02)
                        makedust((actors(arg0\Field2)\Field48 - 40.0), -270.0, $02)
                        If (90.0 = wrapangle(arg0\Field50)) Then
                            makedust((actors(arg0\Field2)\Field48 + 80.0), -270.0, $01)
                        EndIf
                        If (270.0 = wrapangle(arg0\Field50)) Then
                            makedust((actors(arg0\Field2)\Field48 - 80.0), -270.0, $01)
                        EndIf
                    EndIf
                Case 2.0
                    enemies(arg1)\Field5[$02] = (enemies(arg1)\Field5[$02] - (fl\Field1 * 0.6))
                    If (1.0 > enemies(arg1)\Field5[$02]) Then
                        enemies(arg1)\Field5[$02] = 1.0
                    EndIf
                    actors(arg0\Field2)\Field49 = ((enemies(arg1)\Field5[$02] * fl\Field1) + actors(arg0\Field2)\Field49)
                    If (actors(arg0\Field2)\Field49 > (arg0\Field49 - 22.0)) Then
                        enemies(arg1)\Field5[$01] = 0.0
                    EndIf
            End Select
            local11 = (Int (arg0\Field49 - actors(arg0\Field2)\Field49))
            actors(arg0\Field2)\Field6 = $02
            actors(arg0\Field2)\Field7 = $01
            entitytexture(actors(arg0\Field2)\Field5, actordecals(actors(arg0\Field2)\Field6), $00, $00)
            scalesprite(actors(arg0\Field2)\Field5, 20.0, (Float (local11 Sar $01)))
            positionentity(actors(arg0\Field2)\Field5, (Float (- ((local11 Sar $01) + $0A))), 0.0, 0.0, $00)
            If (270.0 = wrapangle(arg0\Field50)) Then
                positionentity(actors(arg0\Field2)\Field5, (Float ((local11 Sar $01) + $0A)), 0.0, 0.0, $00)
            EndIf
            entityorder(actors(arg0\Field2)\Field5, ((arg1 * $03) + $02))
            showentity(actors(arg0\Field2)\Field5)
            If (actors(arg0\Field2)\Field49 > (arg0\Field49 - 22.0)) Then
                actors(arg0\Field2)\Field49 = (arg0\Field49 - 22.0)
            EndIf
            entityorder(actors(arg0\Field2)\Field42, ((arg1 * $03) + $01))
            positionentity(actors(arg0\Field2)\Field42, actors(arg0\Field2)\Field48, actors(arg0\Field2)\Field49, 0.0, $00)
        EndIf
        If (arg0\Field43 = $0D) Then
            If (((arg0\Field41 > $00) Or (actors(arg0\Field2)\Field41 > $00)) <> 0) Then
                If (0.0 = enemies(arg1)\Field5[$01]) Then
                    enemies(arg1)\Field5[$01] = 1.0
                EndIf
                actors(arg0\Field2)\Field59 = 2000.0
                actors(arg0\Field2)\Field48 = arg0\Field48
                actors(arg0\Field2)\Field49 = arg0\Field49
                If (rand($01, (Int (300.0 / fl\Field1))) = $02) Then
                    enemies(arg1)\Field5[$01] = (enemies(arg1)\Field5[$01] * -1.0)
                EndIf
                If (-1.0 = enemies(arg1)\Field5[$01]) Then
                    arg0\Field30 = $02
                    actors(arg0\Field2)\Field30 = $00
                    hideentity(actors(arg0\Field2)\Field42)
                Else
                    arg0\Field30 = $00
                    actors(arg0\Field2)\Field30 = $02
                    showentity(actors(arg0\Field2)\Field42)
                EndIf
                positionentity(actors(arg0\Field2)\Field42, actors(arg0\Field2)\Field48, actors(arg0\Field2)\Field49, 0.0, $00)
            EndIf
        EndIf
        If ((((arg0\Field43 = $03) Or (arg0\Field43 = $0A)) Or (arg0\Field43 = $0B)) <> 0) Then
            If (0.0 = enemies(arg1)\Field5[$01]) Then
                If (100.0 < arg0\Field49) Then
                    enemies(arg1)\Field5[$01] = -1.0
                Else
                    enemies(arg1)\Field5[$01] = 1.0
                EndIf
            EndIf
            enemies(arg1)\Field5[$02] = (((enemies(arg1)\Field5[$01] * fl\Field1) * 0.1) + enemies(arg1)\Field5[$02])
            local12 = (Int enemies(arg1)\Field5[$03])
            If ((((Float local12) < enemies(arg1)\Field5[$02]) Or (200.0 < arg0\Field49)) <> 0) Then
                enemies(arg1)\Field5[$03] = rnd(2.0, 3.5)
                If (((arg0\Field43 = $0A) Or (arg0\Field43 = $0B)) <> 0) Then
                    enemies(arg1)\Field5[$03] = rnd(1.0, 2.0)
                EndIf
                enemies(arg1)\Field5[$01] = -1.0
            EndIf
            If ((((Float (- local12)) > enemies(arg1)\Field5[$02]) Or (0.0 > arg0\Field49)) <> 0) Then
                enemies(arg1)\Field5[$03] = rnd(2.0, 3.5)
                If (((arg0\Field43 = $0A) Or (arg0\Field43 = $0B)) <> 0) Then
                    enemies(arg1)\Field5[$03] = rnd(1.0, 2.0)
                EndIf
                enemies(arg1)\Field5[$01] = 1.0
            EndIf
            arg0\Field49 = ((enemies(arg1)\Field5[$02] * fl\Field1) + arg0\Field49)
        EndIf
        positionentity(arg0\Field42, arg0\Field48, arg0\Field49, 0.0, $00)
    EndIf
    Return $00
End Function
