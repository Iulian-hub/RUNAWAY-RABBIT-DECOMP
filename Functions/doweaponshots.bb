Function doweaponshots%()
    Local local0%
    Local local1.sprite
    Local local2#
    Local local3#
    Local local4%
    Local local5.sprite
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
    Local local16.actortype
    Local local17#
    Local local18#
    Local local19#
    Local local20#
    Local local21#
    Local local22#
    Local local23#
    Local local24#
    Local local25#
    Local local26#
    For local0 = $01 To $64 Step $01
        If (weaponshots(local0)\Field71 = $01) Then
            local1 = (Object.sprite weaponshots(local0)\Field13)
            If (((local1\Field24 = $00) Or (0.0 >= local1\Field19)) <> 0) Then
                weaponshots(local0)\Field71 = $00
                weaponshots(local0)\Field13 = $00
            Else
                weaponshots(local0)\Field11 = (weaponshots(local0)\Field11 + fl\Field1)
                If (weaponshots(local0)\Field37 = $01) Then
                    weaponshots(local0)\Field22 = (((weaponshots(local0)\Field29 + actors(weaponshots(local0)\Field36)\Field44) * fl\Field1) + weaponshots(local0)\Field22)
                    weaponshots(local0)\Field23 = (((weaponshots(local0)\Field30 + actors(weaponshots(local0)\Field36)\Field45) * fl\Field1) + weaponshots(local0)\Field23)
                Else
                    weaponshots(local0)\Field22 = ((weaponshots(local0)\Field29 * fl\Field1) + weaponshots(local0)\Field22)
                    weaponshots(local0)\Field23 = ((weaponshots(local0)\Field30 * fl\Field1) + weaponshots(local0)\Field23)
                EndIf
                doweaponcollision(local0)
                If (((weaponshots(local0)\Field25 = $00) And ((((gamesizex < weaponshots(local0)\Field22) Or ((- gamesizex) > weaponshots(local0)\Field22)) Or (gamesizey < weaponshots(local0)\Field23)) Or ((- gamesizey) > weaponshots(local0)\Field23))) <> 0) Then
                    weaponshots(local0)\Field71 = $00
                    local1\Field24 = $00
                    local1\Field19 = 0.0
                EndIf
                weaponshots(local0)\Field48 = ((weaponshots(local0)\Field47 * fl\Field1) + weaponshots(local0)\Field48)
                local1\Field19 = (local1\Field19 - fl\Field1)
                weaponshots(local0)\Field18 = ((weaponshots(local0)\Field51 * fl\Field1) + weaponshots(local0)\Field18)
                weaponshots(local0)\Field31 = ((weaponshots(local0)\Field49 * fl\Field1) + weaponshots(local0)\Field31)
                weaponshots(local0)\Field32 = ((weaponshots(local0)\Field50 * fl\Field1) + weaponshots(local0)\Field32)
                If (((0.1 > weaponshots(local0)\Field48) Or (1.0 > weaponshots(local0)\Field31)) <> 0) Then
                    weaponshots(local0)\Field71 = $00
                    local1\Field24 = $00
                    local1\Field19 = 0.0
                EndIf
                If (weaponshots(local0)\Field57 > $00) Then
                    weaponshots(local0)\Field53 = (weaponshots(local0)\Field53 + fl\Field1)
                    If (((((weaponshots(local0)\Field52 < weaponshots(local0)\Field53) And (local1\Field24 > $00)) And (0.0 < local1\Field19)) And (weaponshots(local0)\Field71 > $00)) <> 0) Then
                        rotateentity(mathent, 0.0, 0.0, (weaponshots(local0)\Field18 + 180.0), $00)
                        tformnormal(0.0, 1.0, 0.0, mathent, $00)
                        local2 = ((weaponshots(local0)\Field32 / 2.0) * tformedx())
                        local3 = ((weaponshots(local0)\Field32 / 2.0) * tformedy())
                        local4 = makeparticle((weaponshots(local0)\Field22 + local2), (weaponshots(local0)\Field23 + local3), 0.0, 0.0, weaponshots(local0)\Field48, weaponshots(local0)\Field56, weaponshots(local0)\Field56, weaponshots(local0)\Field57, 0.0, (- weaponshots(local0)\Field54), 0.0, 0.0, 60.0, $00)
                        local5 = (Object.sprite local4)
                        local5\Field15 = (rnd(0.5, 1.5) * weaponshots(local0)\Field55)
                        weaponshots(local0)\Field53 = 0.0
                    EndIf
                EndIf
                If (((weaponshots(local0)\Field38 = $01) And (0.0 < weaponshots(local0)\Field40)) <> 0) Then
                    weaponshots(local0)\Field40 = (weaponshots(local0)\Field40 - fl\Field1)
                    weaponshots(local0)\Field22 = (weaponshots(local0)\Field22 - (weaponshots(local0)\Field29 * fl\Field1))
                    weaponshots(local0)\Field23 = (weaponshots(local0)\Field23 - (weaponshots(local0)\Field30 * fl\Field1))
                    local6 = (getangle(weaponshots(local0)\Field22, weaponshots(local0)\Field23, entityx(actors(weaponshots(local0)\Field36)\Field42, $00), entityy(actors(weaponshots(local0)\Field36)\Field42, $00)) - 90.0)
                    local6 = (local6 + 180.0)
                    rotateentity(mathent, 0.0, 0.0, local6, $00)
                    tformnormal(0.0, 1.0, 0.0, mathent, $00)
                    local7 = (weaponshots(local0)\Field39 * tformedx())
                    local8 = (weaponshots(local0)\Field39 * tformedy())
                    weaponshots(local0)\Field22 = (((actors(weaponshots(local0)\Field36)\Field44 * fl\Field1) + local7) + weaponshots(local0)\Field22)
                    weaponshots(local0)\Field23 = (((actors(weaponshots(local0)\Field36)\Field45 * fl\Field1) + local8) + weaponshots(local0)\Field23)
                EndIf
                If (weaponshots(local0)\Field42 = $01) Then
                    findtarget(weaponshots(local0)\Field22, weaponshots(local0)\Field23, (Float weaponshots(local0)\Field43), weaponshots(local0)\Field45, $00, weaponshots(local0)\Field15, $00)
                    weaponshots(local0)\Field45 = foundtarget
                    If (((weaponshots(local0)\Field45 > $00) And ((weaponshots(local0)\Field38 = $00) Or (0.0 >= weaponshots(local0)\Field40))) <> 0) Then
                        If (0.0 = weaponshots(local0)\Field17) Then
                            local9 = (getangle(weaponshots(local0)\Field22, weaponshots(local0)\Field23, foundtargetx, foundtargety) - 90.0)
                            If (0.0 = weaponshots(local0)\Field24) Then
                                weaponshots(local0)\Field18 = reachangle(weaponshots(local0)\Field18, local9, (weaponshots(local0)\Field44 * fl\Field1), $00, $00)
                            EndIf
                            rotateentity(mathent, 0.0, 0.0, weaponshots(local0)\Field18, $00)
                            tformnormal(0.0, 1.0, 0.0, mathent, $00)
                            weaponshots(local0)\Field29 = (weaponshots(local0)\Field19 * tformedx())
                            weaponshots(local0)\Field30 = (weaponshots(local0)\Field19 * tformedy())
                        EndIf
                    EndIf
                EndIf
                If (((0.0 < weaponshots(local0)\Field60) And ((weaponshots(local0)\Field38 = $00) Or (0.0 >= weaponshots(local0)\Field40))) <> 0) Then
                    local10 = (weaponshots(local0)\Field60 - 1.0)
                    weaponshots(local0)\Field19 = (((local10 * weaponshots(local0)\Field19) * fl\Field1) + weaponshots(local0)\Field19)
                    weaponshots(local0)\Field29 = (((local10 * weaponshots(local0)\Field29) * fl\Field1) + weaponshots(local0)\Field29)
                    weaponshots(local0)\Field30 = (((local10 * weaponshots(local0)\Field30) * fl\Field1) + weaponshots(local0)\Field30)
                EndIf
                If (0.0 < weaponshots(local0)\Field20) Then
                    local6 = (getangle(0.0, 0.0, weaponshots(local0)\Field29, weaponshots(local0)\Field30) - 90.0)
                    rotateentity(mathent, 0.0, 0.0, local6, $00)
                    tformnormal(0.0, 1.0, 0.0, mathent, $00)
                    local11 = (weaponshots(local0)\Field29 / tformedx())
                    If (weaponshots(local0)\Field20 < local11) Then
                        rotateentity(mathent, 0.0, 0.0, local6, $00)
                        tformnormal(0.0, 1.0, 0.0, mathent, $00)
                        weaponshots(local0)\Field29 = (weaponshots(local0)\Field20 * tformedx())
                        weaponshots(local0)\Field30 = (weaponshots(local0)\Field20 * tformedy())
                    EndIf
                EndIf
                If (-1.0 < weaponshots(local0)\Field69) Then
                    local11 = (255.0 / weaponshots(local0)\Field0)
                    weaponshots(local0)\Field69 = (weaponshots(local0)\Field69 - (local11 * fl\Field1))
                    If (0.0 > weaponshots(local0)\Field69) Then
                        weaponshots(local0)\Field69 = -1.0
                    EndIf
                    local12 = ((Float (weaponshots(local0)\Field1 - weaponshots(local0)\Field4)) / 255.0)
                    local13 = ((Float (weaponshots(local0)\Field2 - weaponshots(local0)\Field5)) / 255.0)
                    local14 = ((Float (weaponshots(local0)\Field3 - weaponshots(local0)\Field6)) / 255.0)
                    local1\Field12 = (Int ((local12 * weaponshots(local0)\Field69) + (Float weaponshots(local0)\Field4)))
                    local1\Field13 = (Int ((local13 * weaponshots(local0)\Field69) + (Float weaponshots(local0)\Field5)))
                    local1\Field14 = (Int ((local14 * weaponshots(local0)\Field69) + (Float weaponshots(local0)\Field6)))
                EndIf
                If (0.0 < weaponshots(local0)\Field17) Then
                    If (weaponshots(local0)\Field42 = $01) Then
                        If (((weaponshots(local0)\Field45 > $00) And ((weaponshots(local0)\Field38 = $00) Or (0.0 >= weaponshots(local0)\Field40))) <> 0) Then
                            local15 = (foundtargetx - weaponshots(local0)\Field22)
                            If (((0.0 < local15) And (weaponshots(local0)\Field29 < (local15 / 80.0))) <> 0) Then
                                weaponshots(local0)\Field29 = (((weaponshots(local0)\Field44 / 10.0) * fl\Field1) + weaponshots(local0)\Field29)
                            EndIf
                            If (((0.0 > local15) And (weaponshots(local0)\Field29 > (local15 / 80.0))) <> 0) Then
                                weaponshots(local0)\Field29 = (weaponshots(local0)\Field29 - ((weaponshots(local0)\Field44 / 10.0) * fl\Field1))
                            EndIf
                        EndIf
                    EndIf
                    weaponshots(local0)\Field30 = (weaponshots(local0)\Field30 - (weaponshots(local0)\Field17 * fl\Field1))
                    If (0.0 = weaponshots(local0)\Field24) Then
                        weaponshots(local0)\Field18 = reachangle(weaponshots(local0)\Field18, (getangle(0.0, 0.0, weaponshots(local0)\Field29, weaponshots(local0)\Field30) - 90.0), (2.0 * fl\Field1), $00, $00)
                    EndIf
                EndIf
                If (0.0 < weaponshots(local0)\Field24) Then
                    weaponshots(local0)\Field24 = (weaponshots(local0)\Field24 - fl\Field1)
                    If (0.0 > weaponshots(local0)\Field24) Then
                        weaponshots(local0)\Field24 = 0.0
                    EndIf
                EndIf
                If (((weaponshots(local0)\Field25 > $00) And (weaponshots(local0)\Field27 > $00)) <> 0) Then
                    local16 = actors(weaponshots(local0)\Field26)
                    local6 = local16\Field50
                    If (maingameview = $00) Then
                        tformnormal(0.0, 1.0, 0.0, local16\Field42, $00)
                        local17 = (local16\Field72[weaponshots(local0)\Field27] * tformedx())
                        local18 = (local16\Field72[weaponshots(local0)\Field27] * tformedy())
                        tformnormal(1.0, 0.0, 0.0, local16\Field42, $00)
                        local19 = (local16\Field71[weaponshots(local0)\Field27] * tformedx())
                        local20 = (local16\Field71[weaponshots(local0)\Field27] * tformedy())
                        local21 = ((local16\Field48 + local17) + local19)
                        local22 = ((local16\Field49 + local18) + local20)
                    Else
                        local21 = (local16\Field71[weaponshots(local0)\Field27] + local16\Field48)
                        local22 = (local16\Field72[weaponshots(local0)\Field27] + local16\Field49)
                        If (((0.0 < wrapangle(local16\Field50)) And (181.0 > wrapangle(local16\Field50))) <> 0) Then
                            local21 = (local16\Field48 - local16\Field71[weaponshots(local0)\Field27])
                        EndIf
                    EndIf
                    local23 = weaponshots(local0)\Field28
                    local24 = local16\Field66[weaponshots(local0)\Field27]
                    local23 = (local23 + local24)
                    rotateentity(mathent, 0.0, 0.0, (- local16\Field65[weaponshots(local0)\Field27]), $00)
                    local6 = (local16\Field73[weaponshots(local0)\Field27] + local16\Field50)
                    If (local16\Field62[weaponshots(local0)\Field27] > $00) Then
                        local6 = local16\Field65[weaponshots(local0)\Field27]
                    EndIf
                    tformnormal(0.0, 1.0, 0.0, mathent, $00)
                    weaponshots(local0)\Field9 = ((local24 * tformedx()) + local21)
                    weaponshots(local0)\Field10 = ((local24 * tformedy()) + local22)
                    local25 = (local23 * tformedx())
                    local26 = (local23 * tformedy())
                    local21 = (local21 + local25)
                    local22 = (local22 + local26)
                    weaponshots(local0)\Field18 = (- local6)
                    weaponshots(local0)\Field22 = local21
                    weaponshots(local0)\Field23 = local22
                    If (((local16\Field41 = $00) Or (local16\Field0 = $01)) <> 0) Then
                        weaponshots(local0)\Field48 = 0.0
                        local1\Field19 = 0.0
                    EndIf
                EndIf
                setsprite((Handle local1), local1\Field19, weaponshots(local0)\Field22, weaponshots(local0)\Field23, weaponshots(local0)\Field29, weaponshots(local0)\Field30, 0.0, 0.0, (- weaponshots(local0)\Field18), weaponshots(local0)\Field31, 0.0, weaponshots(local0)\Field32, 0.0, weaponshots(local0)\Field48, 0.0, local1\Field20)
            EndIf
        EndIf
    Next
    Return $00
End Function
