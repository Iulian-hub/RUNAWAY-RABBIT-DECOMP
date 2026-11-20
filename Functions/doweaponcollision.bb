Function doweaponcollision%(arg0%)
    Local local0%
    Local local1%
    Local local2%
    Local local3%
    Local local4%
    Local local5%
    Local local6#
    Local local7#
    Local local8#
    Local local9.sprite
    Local local10#
    Local local11#
    Local local12#
    Local local13#
    Local local14#
    Local local15#
    Local local16#
    local0 = $9C40
    local1 = $00
    local2 = $00
    For local3 = $01 To $14 Step $01
        If ((((actors(local3)\Field41 = $01) And (180.0 < actors(local3)\Field77)) And (actors(local3)\Field19 = $00)) <> 0) Then
            local4 = $00
            For local5 = $01 To $0A Step $01
                If (((weaponshots(arg0)\Field64[local5] > $00) And (local5 = actors(local3)\Field30)) <> 0) Then
                    local4 = $01
                EndIf
            Next
            If (local4 = $01) Then
                local6 = 0.0
                If (weaponshots(arg0)\Field25 = $01) Then
                    local6 = 0.0
                EndIf
                If (anglecollisionxy(weaponshots(arg0)\Field22, weaponshots(arg0)\Field23, weaponshots(arg0)\Field33, (weaponshots(arg0)\Field34 + local6), ((- weaponshots(arg0)\Field18) + 90.0), actors(local3)\Field48, actors(local3)\Field49, actors(local3)\Field53, actors(local3)\Field54, actors(local3)\Field50) = $01) Then
                    local7 = weaponshots(arg0)\Field21
                    actors(local3)\Field79 = weaponshots(arg0)\Field36
                    If (weaponshots(arg0)\Field7 = $00) Then
                        If (weaponshots(arg0)\Field35 = $00) Then
                            actors(local3)\Field59 = (actors(local3)\Field59 - local7)
                            If (soundon <> 0) Then
                                playsoundoveride(sndimpact)
                            EndIf
                            If (actors(local3)\Field16 > $00) Then
                                makeweaponshot(weaponshots(arg0)\Field22, weaponshots(arg0)\Field23, (weaponshots(arg0)\Field18 - 180.0), actors(local3)\Field16, $00, $00, 0.0)
                            EndIf
                        ElseIf (weaponshots(arg0)\Field25 = $00) Then
                            local7 = (local7 / (((((actors(local3)\Field53 + actors(local3)\Field54) / 2.0) + ((weaponshots(arg0)\Field33 + weaponshots(arg0)\Field34) / 2.0)) / fl\Field1) / weaponshots(arg0)\Field19))
                            actors(local3)\Field59 = (actors(local3)\Field59 - local7)
                        Else
                            actors(local3)\Field59 = (actors(local3)\Field59 - (local7 * fl\Field1))
                        EndIf
                        If (100.0 > actors(local3)\Field55) Then
                            actors(local3)\Field55 = 255.0
                        EndIf
                        local8 = (getangle(0.0, 0.0, weaponshots(arg0)\Field29, weaponshots(arg0)\Field30) - 90.0)
                        rotateentity(mathent, 0.0, 0.0, local8, $00)
                        tformnormal(0.0, 1.0, 0.0, mathent, $00)
                        If (weaponshots(arg0)\Field35 = $00) Then
                            weaponshots(arg0)\Field71 = $00
                            local9 = (Object.sprite weaponshots(arg0)\Field13)
                            local9\Field19 = 0.0
                            If (weaponshots(arg0)\Field62 = $01) Then
                                makeparticleburst(weaponshots(arg0)\Field22, weaponshots(arg0)\Field23, 1.0, (weaponshots(arg0)\Field31 / 2.0), weaponshots(arg0)\Field31, rand($02, $05), getweaptexlayer(weaponshots(arg0)\Field14), (weaponshots(arg0)\Field31 / 8.0), -0.05, 0.1, 0.1, 20.0, $01)
                            EndIf
                            If (weaponshots(arg0)\Field63 > $00) Then
                                makeexplosion(weaponshots(arg0)\Field22, weaponshots(arg0)\Field23, (Float weaponshots(arg0)\Field63), 0.0)
                            Else
                                makeparticle(weaponshots(arg0)\Field22, weaponshots(arg0)\Field23, 0.0, 0.0, 1.0, weaponshots(arg0)\Field31, weaponshots(arg0)\Field31, $05, 0.0, -0.09, 0.5, 0.5, 60.0, $00)
                            EndIf
                        EndIf
                    Else
                        local10 = actors(local3)\Field48
                        local11 = actors(local3)\Field49
                        local12 = local10
                        local13 = local11
                        positionentity(mathent2, local12, local13, 0.0, $00)
                        If ((Float local0) > entitydistance(mathent, mathent2)) Then
                            local0 = (Int entitydistance(mathent, mathent2))
                            local1 = local3
                            local2 = $00
                            local14 = local12
                            local15 = local13
                        EndIf
                    EndIf
                EndIf
            EndIf
        EndIf
    Next
    For local3 = $01 To $64 Step $01
        If ((((weaponshots(local3)\Field71 = $01) And (weaponshots(local3)\Field66 > $00)) And (weaponshots(local3) <> weaponshots(arg0))) <> 0) Then
            local4 = $00
            For local5 = $01 To $0A Step $01
                If (((weaponshots(arg0)\Field64[local5] > $00) And (local5 = weaponshots(local3)\Field65)) <> 0) Then
                    local4 = $01
                EndIf
            Next
            If (local4 = $01) Then
                local6 = (weaponshots(arg0)\Field19 * fl\Field1)
                If (weaponshots(arg0)\Field25 = $01) Then
                    local6 = 0.0
                EndIf
                If (anglecollisionxy(weaponshots(arg0)\Field22, weaponshots(arg0)\Field23, weaponshots(arg0)\Field33, (weaponshots(arg0)\Field34 + local6), ((- weaponshots(arg0)\Field18) + 90.0), weaponshots(local3)\Field22, weaponshots(local3)\Field23, weaponshots(local3)\Field33, ((weaponshots(local3)\Field19 * fl\Field1) + weaponshots(local3)\Field34), weaponshots(local3)\Field18) = $01) Then
                    If (weaponshots(arg0)\Field7 = $00) Then
                        local7 = weaponshots(arg0)\Field21
                        If (weaponshots(arg0)\Field35 = $00) Then
                            weaponshots(local3)\Field67 = (Int ((Float weaponshots(local3)\Field67) - local7))
                        Else
                            weaponshots(local3)\Field67 = (Int ((Float weaponshots(local3)\Field67) - (local7 * fl\Field1)))
                        EndIf
                        If (100.0 > weaponshots(local3)\Field69) Then
                            weaponshots(local3)\Field69 = 255.0
                        EndIf
                        If (weaponshots(arg0)\Field35 = $00) Then
                            weaponshots(arg0)\Field71 = $00
                            local9 = (Object.sprite weaponshots(arg0)\Field13)
                            local9\Field19 = 0.0
                            If (weaponshots(arg0)\Field62 = $01) Then
                                makeparticleburst(weaponshots(arg0)\Field22, weaponshots(arg0)\Field23, 1.0, (weaponshots(arg0)\Field31 / 2.0), weaponshots(arg0)\Field31, rand($02, $05), getweaptexlayer(weaponshots(arg0)\Field14), (weaponshots(arg0)\Field31 / 8.0), -0.05, 0.1, 0.1, 20.0, $01)
                            EndIf
                            If (weaponshots(arg0)\Field63 > $00) Then
                                makeexplosion(weaponshots(arg0)\Field22, weaponshots(arg0)\Field23, (Float weaponshots(arg0)\Field63), 0.0)
                            Else
                                makeparticle(weaponshots(arg0)\Field22, weaponshots(arg0)\Field23, 0.0, 0.0, 1.0, weaponshots(arg0)\Field31, weaponshots(arg0)\Field31, $05, 0.0, -0.09, 0.5, 0.5, 60.0, $00)
                            EndIf
                        EndIf
                        If (weaponshots(local3)\Field67 <= $00) Then
                            weaponshots(local3)\Field71 = $00
                            local9 = (Object.sprite weaponshots(local3)\Field13)
                            local9\Field19 = 0.0
                            If (weaponshots(local3)\Field62 = $01) Then
                                makeparticleburst(weaponshots(local3)\Field22, weaponshots(local3)\Field23, 1.0, (weaponshots(local3)\Field31 / 2.0), weaponshots(local3)\Field31, rand($02, $05), getweaptexlayer(weaponshots(local3)\Field14), (weaponshots(local3)\Field31 / 8.0), -0.05, 0.1, 0.1, 20.0, $01)
                            EndIf
                            If (weaponshots(local3)\Field63 > $00) Then
                                makeexplosion(weaponshots(local3)\Field22, weaponshots(local3)\Field23, (Float weaponshots(local3)\Field63), 0.0)
                            Else
                                makeparticle(weaponshots(local3)\Field22, weaponshots(local3)\Field23, 0.0, 0.0, 1.0, weaponshots(local3)\Field31, weaponshots(local3)\Field31, $05, 0.0, -0.09, 0.5, 0.5, 60.0, $00)
                            EndIf
                        EndIf
                    Else
                        positionentity(mathent, weaponshots(arg0)\Field9, weaponshots(arg0)\Field10, 0.0, $00)
                        positionentity(mathent2, weaponshots(local3)\Field22, weaponshots(local3)\Field23, 0.0, $00)
                        local12 = weaponshots(local3)\Field22
                        local13 = weaponshots(local3)\Field23
                        If ((Float local0) > entitydistance(mathent, mathent2)) Then
                            local0 = (Int entitydistance(mathent, mathent2))
                            local1 = local3
                            local2 = $01
                            local14 = local12
                            local16 = local13
                        EndIf
                    EndIf
                EndIf
            EndIf
        EndIf
    Next
    If (weaponshots(arg0)\Field7 = $01) Then
        weaponshots(arg0)\Field28 = weaponshots(arg0)\Field8
        weaponshots(arg0)\Field32 = weaponshots(arg0)\Field28
        If (local1 > $00) Then
            positionentity(mathent, weaponshots(arg0)\Field9, weaponshots(arg0)\Field10, 0.0, $00)
            positionentity(mathent2, local14, local15, 0.0, $00)
            weaponshots(arg0)\Field28 = (entitydistance(mathent, mathent2) / 2.0)
            weaponshots(arg0)\Field32 = weaponshots(arg0)\Field28
            Select local2
                Case $00
                    If (weaponshots(arg0)\Field35 = $00) Then
                        actors(local1)\Field59 = (actors(local1)\Field59 - local7)
                    Else
                        actors(local1)\Field59 = (actors(local1)\Field59 - (local7 * fl\Field1))
                    EndIf
                    If (100.0 > actors(local1)\Field55) Then
                        actors(local1)\Field55 = 255.0
                    EndIf
                    local8 = (getangle(0.0, 0.0, weaponshots(arg0)\Field29, weaponshots(arg0)\Field30) - 90.0)
                    rotateentity(mathent, 0.0, 0.0, local8, $00)
                    tformnormal(0.0, 1.0, 0.0, mathent, $00)
                    If (weaponshots(arg0)\Field35 = $00) Then
                        weaponshots(arg0)\Field71 = $00
                        local9 = (Object.sprite weaponshots(arg0)\Field13)
                        local9\Field19 = 0.0
                        If (weaponshots(arg0)\Field62 = $01) Then
                            makeparticleburst(local14, local15, 1.0, (weaponshots(arg0)\Field31 / 2.0), weaponshots(arg0)\Field31, rand($02, $05), getweaptexlayer(weaponshots(arg0)\Field14), (weaponshots(arg0)\Field31 / 8.0), -0.05, 0.1, 0.1, 20.0, $01)
                        EndIf
                        If (weaponshots(arg0)\Field63 > $00) Then
                            makeexplosion(local14, local15, (Float weaponshots(arg0)\Field63), 0.0)
                        EndIf
                    ElseIf (weaponshots(arg0)\Field12 < weaponshots(arg0)\Field11) Then
                        If (weaponshots(arg0)\Field62 = $01) Then
                            makeparticleburst(local14, local15, 1.0, (weaponshots(arg0)\Field31 / 2.0), weaponshots(arg0)\Field31, rand($02, $05), getweaptexlayer(weaponshots(arg0)\Field14), (weaponshots(arg0)\Field31 / 8.0), -0.05, 0.1, 0.1, 20.0, $01)
                        EndIf
                        If (weaponshots(arg0)\Field63 > $00) Then
                            makeexplosion(local14, local15, (Float weaponshots(arg0)\Field63), 0.0)
                        EndIf
                        weaponshots(arg0)\Field11 = 0.0
                    EndIf
                Case $01
                    local7 = weaponshots(arg0)\Field21
                    If (weaponshots(arg0)\Field35 = $00) Then
                        weaponshots(local1)\Field67 = (Int ((Float weaponshots(local1)\Field67) - local7))
                    Else
                        weaponshots(local1)\Field67 = (Int ((Float weaponshots(local1)\Field67) - (local7 * fl\Field1)))
                    EndIf
                    If (100.0 > weaponshots(local1)\Field69) Then
                        weaponshots(local1)\Field69 = 255.0
                    EndIf
                    If (weaponshots(arg0)\Field35 = $00) Then
                        weaponshots(arg0)\Field71 = $00
                        local9 = (Object.sprite weaponshots(arg0)\Field13)
                        local9\Field19 = 0.0
                        If (weaponshots(arg0)\Field62 = $01) Then
                            makeparticleburst(local14, local15, 1.0, (weaponshots(arg0)\Field31 / 2.0), weaponshots(arg0)\Field31, rand($02, $05), getweaptexlayer(weaponshots(arg0)\Field14), (weaponshots(arg0)\Field31 / 8.0), -0.05, 0.1, 0.1, 20.0, $01)
                        EndIf
                        If (weaponshots(arg0)\Field63 > $00) Then
                            makeexplosion(local14, local15, (Float weaponshots(arg0)\Field63), 0.0)
                        EndIf
                    EndIf
                    If (weaponshots(local1)\Field67 <= $00) Then
                        weaponshots(local1)\Field71 = $00
                        local9 = (Object.sprite weaponshots(local1)\Field13)
                        local9\Field19 = 0.0
                    EndIf
                    If (weaponshots(arg0)\Field62 = $01) Then
                        makeparticleburst(weaponshots(local1)\Field22, weaponshots(local1)\Field22, 1.0, (weaponshots(local1)\Field31 / 2.0), weaponshots(local1)\Field31, rand($02, $05), getweaptexlayer(weaponshots(local1)\Field14), (weaponshots(local1)\Field31 / 8.0), -0.05, 0.1, 0.1, 20.0, $01)
                    EndIf
                    If (weaponshots(arg0)\Field63 > $00) Then
                        makeexplosion(weaponshots(local1)\Field22, weaponshots(local1)\Field22, (Float weaponshots(local1)\Field63), 0.0)
                    EndIf
            End Select
        EndIf
    EndIf
    Return $00
End Function
