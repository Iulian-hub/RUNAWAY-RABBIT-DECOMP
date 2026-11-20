Function dospecialmenucode%(arg0%)
    Local local1%
    Local local2%
    Local local3%
    Local local5%
    Local local6%
    Local local9%
    Local local11%
    Local local12$
    Local local13%
    Local local16$
    Local local18#
    If (((arg0 = $17) And (currentmenu = $17)) <> 0) Then
        menus($17)\Field15[$03] = buymenutext
    EndIf
    If (((arg0 = $1D) And (currentmenu = $1D)) <> 0) Then
        menus($1D)\Field15[$03] = buymenutext
    EndIf
    If (((arg0 = $11) And (currentmenu = $11)) <> 0) Then
        menus($11)\Field15[$03] = upgradetype(upgradenum)\Field5
    EndIf
    If (((arg0 = $10) And (currentmenu = $10)) <> 0) Then
        Select upgradenum
            Case $01
                local1 = upgradetype(upgradenum)\Field0[(profiles(currentprofile)\Field7 + $01)]
            Case $02
                local1 = upgradetype(upgradenum)\Field0[(profiles(currentprofile)\Field8 + $01)]
            Case $03
                local1 = upgradetype(upgradenum)\Field0[(profiles(currentprofile)\Field9 + $01)]
            Case $04
                local1 = upgradetype(upgradenum)\Field0[(profiles(currentprofile)\Field10 + $01)]
            Case $05
                local1 = upgradetype(upgradenum)\Field0[(profiles(currentprofile)\Field11 + $01)]
        End Select
        menus($10)\Field15[$03] = (((("Are you sure you want to upgrade your " + upgradetype(upgradenum)\Field4) + " for $") + (Str local1)) + "?")
    EndIf
    If ((((arg0 = $0F) And (currentmenu = $0F)) Or ((arg0 = $18) And (currentmenu = $18))) <> 0) Then
        menus($0F)\Field15[$62] = ("CASH: $" + (Str profiles(currentprofile)\Field6))
        menus($0F)\Field20[$62] = 255.0
        menus($0F)\Field21[$62] = 255.0
        menus($0F)\Field22[$62] = 255.0
        local2 = $FFFFFFFF
        For local3 = $01 To $08 Step $01
            Select local3
                Case $04
                    local5 = profiles(currentprofile)\Field7
                    local6 = $13
                Case $05
                    local5 = profiles(currentprofile)\Field8
                    local6 = $54
                Case $06
                    local5 = profiles(currentprofile)\Field9
                    local6 = $59
                Case $07
                    local5 = profiles(currentprofile)\Field10
                    local6 = $5E
                Case $08
                    local5 = profiles(currentprofile)\Field11
                    local6 = $40
                Case $01
                    local5 = profiles(currentprofile)\Field16
                    local6 = $45
                Case $02
                    local5 = profiles(currentprofile)\Field12
                    local6 = $4A
                Case $03
                    local5 = profiles(currentprofile)\Field13
                    local6 = $4F
            End Select
            If (((upgradetype(local3)\Field3 <= profiles(currentprofile)\Field1) And (local5 < upgradetype(local3)\Field1)) <> 0) Then
                menus($0F)\Field36[(local3 + $01)] = ((local6 + local5) + $01)
                menus($0F)\Field15[(local3 + $01)] = ""
                If (upgradetype(local3)\Field0[(local5 + $01)] > profiles(currentprofile)\Field6) Then
                EndIf
                menus($0F)\Field26[(local3 + $01)] = $00
                If (local3 > $03) Then
                    menus($0F)\Field36[(local3 + $01)] = (local6 + local5)
                EndIf
                If (local5 = $00) Then
                    Select local3
                        Case $06
                            menus($0F)\Field36[(local3 + $01)] = $1E
                        Case $07
                            menus($0F)\Field36[(local3 + $01)] = $1F
                        Case $08
                            menus($0F)\Field36[(local3 + $01)] = $20
                    End Select
                EndIf
            ElseIf (upgradetype(local3)\Field3 > profiles(currentprofile)\Field1) Then
                menus($0F)\Field36[(local3 + $01)] = ((local6 + local5) + $01)
                If (local3 > $03) Then
                    menus($0F)\Field36[(local3 + $01)] = (local6 + local5)
                EndIf
                If (local5 = $00) Then
                    Select local3
                        Case $06
                            menus($0F)\Field36[(local3 + $01)] = $1E
                        Case $07
                            menus($0F)\Field36[(local3 + $01)] = $1F
                        Case $08
                            menus($0F)\Field36[(local3 + $01)] = $20
                    End Select
                EndIf
                menus($0F)\Field15[(local3 + $01)] = "unavailable"
                menus($0F)\Field26[(local3 + $01)] = $01
            Else
                menus($0F)\Field36[(local3 + $01)] = ((local6 + local5) + $01)
                If (local3 > $03) Then
                    menus($0F)\Field36[(local3 + $01)] = (local6 + local5)
                EndIf
                menus($0F)\Field15[(local3 + $01)] = "MAXED"
                menus($0F)\Field26[(local3 + $01)] = $01
                menus($0F)\Field41[(local3 + $01)] = 255.0
                menus($0F)\Field42[(local3 + $01)] = 255.0
                menus($0F)\Field43[(local3 + $01)] = 255.0
            EndIf
            menus($0F)\Field20[(local3 + $01)] = 255.0
            menus($0F)\Field21[(local3 + $01)] = 0.0
            menus($0F)\Field22[(local3 + $01)] = 0.0
        Next
        menus($0F)\Field15[$61] = ""
        menus($0F)\Field15[$60] = ""
        menus($0F)\Field15[$5F] = ""
        menus($0F)\Field20[$61] = 255.0
        menus($0F)\Field21[$61] = 255.0
        menus($0F)\Field22[$61] = 255.0
        menus($0F)\Field20[$60] = 255.0
        menus($0F)\Field21[$60] = 255.0
        menus($0F)\Field22[$60] = 255.0
        If (((currentmouseover > $01) And (local9 < $0A)) <> 0) Then
            Select (currentmouseover - $01)
                Case $04
                    local5 = profiles(currentprofile)\Field7
                    local6 = $13
                Case $05
                    local5 = profiles(currentprofile)\Field8
                    local6 = $54
                Case $06
                    local5 = profiles(currentprofile)\Field9
                    local6 = $59
                Case $07
                    local5 = profiles(currentprofile)\Field10
                    local6 = $5E
                Case $08
                    local5 = profiles(currentprofile)\Field11
                    local6 = $40
                Case $01
                    local5 = profiles(currentprofile)\Field16
                    local6 = $45
                Case $02
                    local5 = profiles(currentprofile)\Field12
                    local6 = $4A
                Case $03
                    local5 = profiles(currentprofile)\Field13
                    local6 = $4F
            End Select
            If (upgradetype((currentmouseover - $01))\Field0[(local5 + $01)] > profiles(currentprofile)\Field6) Then
                menus($0F)\Field20[$61] = 255.0
                menus($0F)\Field21[$61] = 0.0
                menus($0F)\Field22[$61] = 0.0
                menus($0F)\Field20[$60] = 255.0
                menus($0F)\Field21[$60] = 0.0
                menus($0F)\Field22[$60] = 0.0
            EndIf
            menus($0F)\Field15[$61] = upgradetype((currentmouseover - $01))\Field4
            menus($0F)\Field15[$60] = ("COST: $" + (Str upgradetype((currentmouseover - $01))\Field0[(local5 + $01)]))
            menus($0F)\Field15[$5F] = upgradetype((currentmouseover - $01))\Field5
        EndIf
    EndIf
    If (((arg0 = $0D) And (currentmenu = $0D)) <> 0) Then
        local11 = (player($01)\Field11 + player($02)\Field11)
        If (local11 > $FFFFFFFF) Then
            local12 = "Meek Mouse"
        EndIf
        If (local11 > $186A0) Then
            local12 = "Rabid Rat"
        EndIf
        If (local11 > $30D40) Then
            local12 = "Pokey Porcupine"
        EndIf
        If (local11 > $493E0) Then
            local12 = "Militant Mole"
        EndIf
        If (local11 > $61A80) Then
            local12 = "Ferocious Ferret"
        EndIf
        If (local11 > $927C0) Then
            local12 = "Badass Badger"
        EndIf
        If (local11 > $C3500) Then
            local12 = "Heroic Hedgehog!!!"
        EndIf
        If (specialmode <> $01) Then
            If (local11 > $FFFFFFFF) Then
                local12 = "Meek Mouse"
            EndIf
            If (local11 > $C350) Then
                local12 = "Rabid Rat"
            EndIf
            If (local11 > $186A0) Then
                local12 = "Pokey Porcupine"
            EndIf
            If (local11 > $249F0) Then
                local12 = "Militant Mole"
            EndIf
            If (local11 > $3D090) Then
                local12 = "Ferocious Ferret"
            EndIf
            If (local11 > $493E0) Then
                local12 = "Badass Badger"
            EndIf
            If (local11 > $61A80) Then
                local12 = "Heroic Hedgehog!!!"
            EndIf
        EndIf
        If (totalenemies = $00) Then
            totalenemies = $01
            player($01)\Field7 = $01
        EndIf
        local13 = (Int (((Float (player($01)\Field7 + player($02)\Field7)) / (Float totalenemies)) * 100.0))
        menus($0D)\Field15[$07] = ((Str local13) + "%")
        menus($0D)\Field15[$09] = (Str (player($01)\Field8 + player($02)\Field8))
        menus($0D)\Field15[$0B] = (Str bestcombo)
        menus($0D)\Field15[$0D] = ("$" + (Str local11))
        menus($0D)\Field15[$0F] = local12
        If ((((specialmode = $00) And (profiles(currentprofile)\Field14 = $02)) Or ((specialmode > $00) And (numberofplayers2 = $02))) <> 0) Then
            menus($0D)\Field15[$10] = "        Best Rodent: "
            menus($0D)\Field15[$11] = "Draw"
            If (player($01)\Field11 > player($02)\Field11) Then
                menus($0D)\Field15[$11] = "Player One"
            EndIf
            If (player($02)\Field11 > player($01)\Field11) Then
                menus($0D)\Field15[$11] = "Player Two"
            EndIf
        Else
            menus($0D)\Field15[$10] = ""
            menus($0D)\Field15[$11] = ""
        EndIf
        If (winorlose = $00) Then
            menus($0D)\Field15[$05] = "GAME OVER"
            menus($0D)\Field20[$05] = 150.0
            menus($0D)\Field21[$05] = 0.0
            menus($0D)\Field22[$05] = 0.0
        Else
            menus($0D)\Field15[$05] = "WELL DONE!"
            menus($0D)\Field20[$05] = 255.0
            menus($0D)\Field21[$05] = 255.0
            menus($0D)\Field22[$05] = 255.0
        EndIf
        If (((gamebuilds(currentbuild)\Field7 = $00) Or (specialmode <> $01)) <> 0) Then
            menus($0D)\Field15[$03] = ""
            menus($0D)\Field26[$03] = $01
        Else
            menus($0D)\Field15[$03] = "POST SCORE"
            menus($0D)\Field26[$03] = $00
        EndIf
        If (winorlose = $00) Then
            menus($0D)\Field15[$01] = "BACK TO MENU"
        Else
            menus($0D)\Field15[$01] = "CONTINUE"
        EndIf
        If (winorlose = $00) Then
            menus($0D)\Field17[$03] = 60.0
            menus($0D)\Field40[$03] = 60.0
            menus($0D)\Field15[$02] = "TRY AGAIN"
            menus($0D)\Field26[$02] = $00
        Else
            menus($0D)\Field17[$03] = 100.0
            menus($0D)\Field40[$03] = 100.0
            menus($0D)\Field15[$02] = ""
            menus($0D)\Field26[$02] = $01
        EndIf
    EndIf
    If (((arg0 = $0C) And (currentmenu = $0C)) <> 0) Then
        If (gamebuilds(currentbuild)\Field7 = $00) Then
            menus($0C)\Field15[$21] = ""
            menus($0C)\Field26[$21] = $01
        Else
            menus($0C)\Field15[$21] = "VIEW ONLINE SCORES"
            menus($0C)\Field26[$21] = $00
        EndIf
        For local3 = $01 To $0A Step $01
            If (local3 = scoretohighlight) Then
                menus($0C)\Field20[local3] = 255.0
                menus($0C)\Field21[local3] = 55.0
                menus($0C)\Field22[local3] = 55.0
                menus($0C)\Field20[(local3 + $0A)] = 255.0
                menus($0C)\Field21[(local3 + $0A)] = 55.0
                menus($0C)\Field22[(local3 + $0A)] = 55.0
                menus($0C)\Field20[(local3 + $14)] = 255.0
                menus($0C)\Field21[(local3 + $14)] = 55.0
                menus($0C)\Field22[(local3 + $14)] = 55.0
            Else
                menus($0C)\Field20[local3] = 255.0
                menus($0C)\Field21[local3] = 255.0
                menus($0C)\Field22[local3] = 0.0
                menus($0C)\Field20[(local3 + $0A)] = 255.0
                menus($0C)\Field21[(local3 + $0A)] = 255.0
                menus($0C)\Field22[(local3 + $0A)] = 0.0
                menus($0C)\Field20[(local3 + $14)] = 255.0
                menus($0C)\Field21[(local3 + $14)] = 255.0
                menus($0C)\Field22[(local3 + $14)] = 0.0
            EndIf
            menus($0C)\Field15[(local3 + $0A)] = highscores(local3)\Field0
            menus($0C)\Field15[(local3 + $14)] = ("$" + (Str highscores(local3)\Field1))
        Next
    EndIf
    If (((arg0 = $0A) And (currentmenu = $0A)) <> 0) Then
        Select currentmouseover
            Case $01
                menus($0A)\Field15[$05] = "Play the normal campaign"
            Case $02
                menus($0A)\Field15[$05] = "Battle your way through all the enemies in the game and earn a highscore"
            Case $03
                menus($0A)\Field15[$05] = "Take on all the bosses in one packed level"
            Default
                menus($0A)\Field15[$05] = ""
        End Select
        If (((profiles(currentprofile)\Field1 > $07) And (demoversionon = $00)) <> 0) Then
            If (menus($0A)\Field47[$02] = $01) Then
                menus($0A)\Field20[$02] = 150.0
                menus($0A)\Field21[$02] = 220.0
                menus($0A)\Field22[$02] = 220.0
            EndIf
            menus($0A)\Field47[$02] = $00
        Else
            If (menus($0A)\Field47[$02] = $00) Then
                menus($0A)\Field20[$02] = 150.0
                menus($0A)\Field21[$02] = 0.0
                menus($0A)\Field22[$02] = 0.0
            EndIf
            menus($0A)\Field47[$02] = $01
        EndIf
        If (profiles(currentprofile)\Field1 > $1C) Then
            menus($0A)\Field47[$03] = $00
            If (menus($0A)\Field47[$03] = $01) Then
                menus($0A)\Field20[$03] = 150.0
                menus($0A)\Field21[$03] = 220.0
                menus($0A)\Field22[$03] = 220.0
            EndIf
        Else
            menus($0A)\Field47[$03] = $01
            If (menus($0A)\Field47[$03] = $01) Then
                menus($0A)\Field20[$03] = 150.0
                menus($0A)\Field21[$03] = 0.0
                menus($0A)\Field22[$03] = 0.0
            EndIf
        EndIf
    EndIf
    If (((arg0 = $0B) And (currentmenu = $0B)) <> 0) Then
        If (numberofplayers2 = $01) Then
            menus($0B)\Field15[$03] = "PLAYERS: ONE"
        Else
            menus($0B)\Field15[$03] = "PLAYERS: TWO"
        EndIf
    EndIf
    If (((arg0 = $09) And (currentmenu = $09)) <> 0) Then
        gui_objectorder(menus($09)\Field52[$01], $FFFE7939)
        menus($09)\Field54[$01] = ((menus($09)\Field16[(profiles(currentprofile)\Field1 + $05)] + 33.0) + menus($09)\Field2)
        menus($09)\Field55[$01] = ((menus($09)\Field17[(profiles(currentprofile)\Field1 + $05)] - 40.0) + menus($09)\Field3)
        If (profiles(currentprofile)\Field1 > $1D) Then
            menus($09)\Field54[$01] = -300.0
            menus($09)\Field55[$01] = -300.0
        EndIf
        menus($09)\Field15[$62] = ("CASH: $" + (Str profiles(currentprofile)\Field6))
        menus($09)\Field20[$62] = 255.0
        menus($09)\Field21[$62] = 255.0
        menus($09)\Field22[$62] = 255.0
        Select profiles(currentprofile)\Field5
            Case $01
                local16 = "EASY"
            Case $02
                local16 = "MEDIUM"
            Case $03
                local16 = "HARD"
            Case $04
                local16 = "INSANE"
        End Select
        menus($09)\Field15[$61] = ("SKILL LEVEL: " + local16)
        menus($09)\Field20[$61] = 255.0
        menus($09)\Field21[$61] = 255.0
        menus($09)\Field22[$61] = 255.0
        If (profiles(currentprofile)\Field14 = $01) Then
            menus($09)\Field15[$03] = "PLAYERS: ONE"
        Else
            menus($09)\Field15[$03] = "PLAYERS: TWO"
        EndIf
        For local3 = $01 To $1D Step $01
            If (profiles(currentprofile)\Field1 < local3) Then
                If ((((((local3 = $03) Or (local3 = $07)) Or (local3 = $0D)) Or (local3 = $15)) Or (local3 = $1D)) <> 0) Then
                    menus($09)\Field36[(local3 + $05)] = $21
                Else
                    menus($09)\Field36[(local3 + $05)] = $22
                EndIf
                menus($09)\Field47[(local3 + $05)] = $01
            Else
                menus($09)\Field47[(local3 + $05)] = $00
                If ((((((local3 = $03) Or (local3 = $07)) Or (local3 = $0D)) Or (local3 = $15)) Or (local3 = $1D)) <> 0) Then
                    menus($09)\Field36[(local3 + $05)] = $05
                Else
                    menus($09)\Field36[(local3 + $05)] = $06
                EndIf
            EndIf
            If (profiles(currentprofile)\Field1 = local3) Then
                mappulsesize = (((fl\Field1 * (Float mappulsedirection)) * 1.5) + mappulsesize)
                If (60.0 < mappulsesize) Then
                    mappulsedirection = $FFFFFFFF
                    mappulsesize = 60.0
                EndIf
                If (30.0 > mappulsesize) Then
                    mappulsedirection = $01
                    mappulsesize = 30.0
                EndIf
                menus($09)\Field37[(local3 + $05)] = mappulsesize
                menus($09)\Field38[(local3 + $05)] = mappulsesize
                gui_scaleobject(menus($09)\Field35[(local3 + $05)], mappulsesize, mappulsesize)
            Else
                menus($09)\Field37[(local3 + $05)] = 30.0
                menus($09)\Field38[(local3 + $05)] = 30.0
                gui_scaleobject(menus($09)\Field35[(local3 + $05)], 20.0, 20.0)
            EndIf
        Next
    EndIf
    If (((arg0 = $06) And (currentmenu = $06)) <> 0) Then
        If (musicon = $01) Then
            menus($06)\Field15[$01] = "MUSIC: ON"
        Else
            menus($06)\Field15[$01] = "MUSIC: OFF"
        EndIf
        If (soundon = $01) Then
            menus($06)\Field15[$02] = "SOUND: ON"
        Else
            menus($06)\Field15[$02] = "SOUND: OFF"
        EndIf

; --- DYNAMIC SCANNER FOR CUSTOM SETTINGS ---
        Local j% = 0
        For j = 1 To 60
            If (menus(6)\Field24[j] = "autofirebutton") Then
                If (forcefireon = 1) Then menus(6)\Field15[j] = "AUTO-FIRE: ON" Else menus(6)\Field15[j] = "AUTO-FIRE: OFF"
            EndIf
            
            If (menus(6)\Field24[j] = "crossvisbutton") Then
                If (crosshairvisible = 1) Then menus(6)\Field15[j] = "CROSSHAIR: VISIBLE" Else menus(6)\Field15[j] = "CROSSHAIR: HIDDEN"
            EndIf
            
            If (menus(6)\Field24[j] = "crosscolorbutton") Then
                If (p1crosshairG = 255.0) Then
                    menus(6)\Field15[j] = "CROSS COLOR: GREEN"
                ElseIf (p1crosshairR = 255.0) Then
                    menus(6)\Field15[j] = "CROSS COLOR: RED"
                Else
                    menus(6)\Field15[j] = "CROSS COLOR: BLUE"
                EndIf
            EndIf
        Next
        ; -------------------------------------------

        Select tempresmode
            Case $01
                menus($06)\Field15[$06] = "SCREEN: 640x480x16"
            Case $02
                menus($06)\Field15[$06] = "SCREEN: 800x600x16"
            Case $03
                menus($06)\Field15[$06] = "SCREEN: 1024x768x16"
            Case $04
                menus($06)\Field15[$06] = "SCREEN: WINDOWED"
            Case $05
                menus($06)\Field15[$06] = "SCREEN: 640x480x32"
            Case $06
                menus($06)\Field15[$06] = "SCREEN: 800x600x32"
            Case $07
                menus($06)\Field15[$06] = "SCREEN: 1024x768x32"
            Case $08
                menus($06)\Field15[$06] = "SCREEN: WINDOWED"
        End Select
    EndIf
    nokeys = $00
    If (currentmenu = $05) Then
        nokeys = $01
    EndIf
    If (((arg0 = $07) And (currentmenu = $07)) <> 0) Then
        local18 = (currentloadamount / 8.0)
        menus($07)\Field56[$01] = (200.0 * local18)
        menus($07)\Field54[$01] = ((menus($07)\Field56[$01] / 2.0) + 195.0)
        entitycolor(gui_getobjectentity(menus($07)\Field52[$01]), 100.0, 255.0, 100.0)
    EndIf
    If (((arg0 = $04) And (currentmenu = $04)) <> 0) Then
        For local3 = $01 To $06 Step $01
            If (len(profiles(local3)\Field0) > $00) Then
                menus($04)\Field15[local3] = profiles(local3)\Field0
                If (menus($04)\Field47[local3] = $01) Then
                    menus($04)\Field20[local3] = 150.0
                    menus($04)\Field21[local3] = 220.0
                    menus($04)\Field22[local3] = 220.0
                EndIf
                If (menus($04)\Field47[(local3 + $0A)] = $01) Then
                    menus($04)\Field20[(local3 + $0A)] = 150.0
                    menus($04)\Field21[(local3 + $0A)] = 220.0
                    menus($04)\Field22[(local3 + $0A)] = 220.0
                EndIf
                menus($04)\Field47[local3] = $00
                menus($04)\Field47[(local3 + $0A)] = $00
            Else
                menus($04)\Field15[local3] = "NEW PLAYER"
                If (menus($04)\Field47[local3] = $01) Then
                    menus($04)\Field20[local3] = 150.0
                    menus($04)\Field21[local3] = 220.0
                    menus($04)\Field22[local3] = 220.0
                EndIf
                menus($04)\Field47[local3] = $00
                menus($04)\Field47[(local3 + $0A)] = $01
            EndIf
            If (local3 = currentprofile) Then
                menus($04)\Field47[local3] = $01
                menus($04)\Field47[(local3 + $0A)] = $01
            EndIf
        Next
    EndIf
    If (((arg0 = $03) And (currentmenu = $03)) <> 0) Then
        menus($03)\Field15[$03] = (("Are you sure you want to delete " + profiles(profiletodelete)\Field0) + "?")
    EndIf
    If (((arg0 = $05) And (currentmenu = $05)) <> 0) Then
        menus($05)\Field15[$02] = dotextinput($08, $01)
        If (len(menus($05)\Field15[$02]) < $02) Then
            menus($05)\Field47[$01] = $01
        Else
            If (menus($05)\Field47[$01] = $01) Then
                menus($05)\Field20[$01] = 150.0
                menus($05)\Field21[$01] = 220.0
                menus($05)\Field22[$01] = 220.0
            EndIf
            If ((keydown($1C) And (enterdown = $00)) <> 0) Then
                mouseisdown = $01
                currentprofile = (currentprofile + $01)
                profiles(currentprofile)\Field0 = left(menus($05)\Field15[$02], (len(menus($05)\Field15[$02]) - $01))
                newprofile()
                enterdown = $01
                changemenu($02, 0.0, 0.0)
                clearinputbuffer()
            EndIf
            If (keydown($1C) = $00) Then
                enterdown = $00
            EndIf
            menus($05)\Field47[$01] = $00
        EndIf
    EndIf
    If (((arg0 = $05) And (currentmenu = $05)) <> 0) Then
        If (tempskill = $00) Then
            tempskill = $02
        EndIf
        Select tempskill
            Case $01
                menus($05)\Field15[$04] = "SKILL LEVEL: EASY"
            Case $02
                menus($05)\Field15[$04] = "SKILL LEVEL: MEDIUM"
            Case $03
                menus($05)\Field15[$04] = "SKILL LEVEL: HARD"
            Case $04
                menus($05)\Field15[$04] = "SKILL LEVEL: INSANE!"
        End Select
    EndIf
    If (((arg0 = $02) And (currentmenu = $02)) <> 0) Then
        menus($02)\Field15[$08] = (("WELCOME " + profiles(currentprofile)\Field0) + "!")
    EndIf
    Return $00
End Function
