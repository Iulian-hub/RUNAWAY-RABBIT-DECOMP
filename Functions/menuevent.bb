Function menuevent%(arg0$)
    Local local2%
    Local local3%
    Local local5%
    If (arg0 = "shophelpcontinue") Then
        changemenu($0F, 0.0, 0.0)
    EndIf
    If (arg0 = "democontinue") Then
        If (buymenutext = "Your demo time has run out. Get the full version now and play for as long as you want!") Then
            fadeout2($00, $00, $01, $00, $00)
            showfullscreen("gfx/mappic.jpg")
            changemenu($09, -1.0, 0.0)
            startmusic($02)
            gui_hidegroup($01)
            startmenubkg($26, $01)
        ElseIf (buymenutext = "Sorry, you can only go here in the full version of Runaway Robbit.") Then
            changemenu($09, 0.0, 0.0)
        Else
            changemenu($00, 0.0, 0.0)
        EndIf
    EndIf
    If (arg0 = "buynow") Then
        buynow()
    EndIf
    If (arg0 = "buycontinue") Then
        If (buycontinueexit = $01) Then
            end()
        EndIf
        fadeout2($00, $00, $01, $00, $00)
        showfullscreen("gfx/mappic.jpg")
        changemenu($09, -1.0, 0.0)
        startmusic($02)
        gui_hidegroup($01)
        startmenubkg($26, $01)
    EndIf
    If (arg0 = "nobuy") Then
        changemenu($0F, 0.0, 0.0)
    EndIf
    If (arg0 = "yesbuy") Then
        If (soundon = $01) Then
            playsound(sndbuy)
        EndIf
        Select upgradenum
            Case $01
                profiles(currentprofile)\Field7 = (profiles(currentprofile)\Field7 + $01)
                profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field7])
            Case $02
                profiles(currentprofile)\Field8 = (profiles(currentprofile)\Field8 + $01)
                profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field8])
            Case $03
                profiles(currentprofile)\Field9 = (profiles(currentprofile)\Field9 + $01)
                profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field9])
            Case $04
                profiles(currentprofile)\Field10 = (profiles(currentprofile)\Field10 + $01)
                profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field10])
            Case $05
                profiles(currentprofile)\Field11 = (profiles(currentprofile)\Field11 + $01)
                profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field11])
        End Select
        saveprofiles()
    EndIf
    If (arg0 = "continueinfo") Then
        changemenu($0F, 0.0, 0.0)
    EndIf
    If (left(arg0, $04) = "info") Then
        upgradenum = (Int mid(arg0, $05, (len(arg0) - $04)))
        changemenu($11, 0.0, 0.0)
    EndIf
    If (left(arg0, $0D) = "upgradeweapon") Then
        upgradenum = (Int mid(arg0, $0E, (len(arg0) - $0D)))
        Select upgradenum
            Case $04
                local2 = profiles(currentprofile)\Field7
                local3 = $13
            Case $05
                local2 = profiles(currentprofile)\Field8
                local3 = $54
            Case $06
                local2 = profiles(currentprofile)\Field9
                local3 = $59
            Case $07
                local2 = profiles(currentprofile)\Field10
                local3 = $5E
            Case $08
                local2 = profiles(currentprofile)\Field11
                local3 = $40
            Case $01
                local2 = profiles(currentprofile)\Field16
                local3 = $45
            Case $02
                local2 = profiles(currentprofile)\Field12
                local3 = $4A
            Case $03
                local2 = profiles(currentprofile)\Field13
                local3 = $4F
        End Select
        If (profiles(currentprofile)\Field6 >= upgradetype(upgradenum)\Field0[(local2 + $01)]) Then
            If (soundon = $01) Then
                playsound(sndbuy)
            EndIf
            Select upgradenum
                Case $01
                    profiles(currentprofile)\Field16 = (profiles(currentprofile)\Field16 + $01)
                    profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field16])
                Case $02
                    profiles(currentprofile)\Field12 = (profiles(currentprofile)\Field12 + $01)
                    profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field12])
                Case $03
                    profiles(currentprofile)\Field13 = (profiles(currentprofile)\Field13 + $01)
                    profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field13])
                Case $04
                    profiles(currentprofile)\Field7 = (profiles(currentprofile)\Field7 + $01)
                    profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field7])
                Case $05
                    profiles(currentprofile)\Field8 = (profiles(currentprofile)\Field8 + $01)
                    profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field8])
                Case $06
                    profiles(currentprofile)\Field9 = (profiles(currentprofile)\Field9 + $01)
                    profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field9])
                Case $07
                    profiles(currentprofile)\Field10 = (profiles(currentprofile)\Field10 + $01)
                    profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field10])
                Case $08
                    profiles(currentprofile)\Field11 = (profiles(currentprofile)\Field11 + $01)
                    profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 - upgradetype(upgradenum)\Field0[profiles(currentprofile)\Field11])
            End Select
        ElseIf (soundon = $01) Then
            playsound(sndcantafford)
        EndIf
        saveprofiles()
    EndIf
    If (arg0 = "upgradedone") Then
        changemenu($00, 0.0, 0.0)
        fadeout2($00, $00, $01, $01, $01)
        hidefullscreen()
        startlevel(levelnum, profiles(currentprofile)\Field14, $00)
    EndIf
    If (arg0 = "scoresback") Then
        changemenu($02, 0.0, 0.0)
        scoretohighlight = $00
    EndIf
    If (arg0 = "viewonlinescores") Then
        viewworldscores()
    EndIf
    If (arg0 = "normalmode") Then
        specialmode = $00
        fadeout2($00, $00, $01, $00, $00)
        showfullscreen("gfx/mappic.jpg")
        changemenu($09, -1.0, 0.0)
        fadein2($00, $00, $01, $00, $00, $00)
    EndIf
    If (arg0 = "arcademode") Then
        leveltime = 0.0
        specialmode = $01
        changemenu($0B, 0.0, 0.0)
    EndIf
    If (arg0 = "bossmode") Then
        leveltime = 0.0
        specialmode = $02
        changemenu($0B, 0.0, 0.0)
    EndIf
    If (arg0 = "backtomain") Then
        changemenu($02, 0.0, 0.0)
    EndIf
    If (arg0 = "continueplayer") Then
        changemenu($00, 0.0, 0.0)
        fadeout2($00, $00, $00, $01, $01)
        If (specialmode = $01) Then
            startlevel($27, numberofplayers2, $00)
        Else
            startlevel($28, numberofplayers2, $00)
        EndIf
    EndIf
    If (arg0 = "mapnextlevel") Then
        arg0 = ("playlevel" + (Str profiles(currentprofile)\Field1))
    EndIf
    If (left(arg0, $09) = "playlevel") Then
        leveltime = 0.0
        levelnum = (Int mid(arg0, $0A, (len(arg0) - $09)))
        If (levelnum = $01) Then
            fadeout2($00, $00, $01, $00, $01)
            showfullscreen("gfx/instructions.jpg")
            changemenu($08, -1.0, 0.0)
            backmenu = $00
            fadein2($00, $00, $01, $00, $00, $01)
        ElseIf (levelnum = $02) Then
            changemenu($0F, 0.0, 0.0)
            changemenu($18, 0.0, 0.0)
        ElseIf (((levelnum > $07) And (demoversionon = $01)) <> 0) Then
            If (shownbuynag = $00) Then
                shownbuynag = $01
                changemenu($00, 0.0, 0.0)
                soundon = $00
                fadeout2($00, $00, $01, $01, $01)
                hidefullscreen()
                buycontinueexit = $00
                startlevel($25, $01, $01)
            Else
                buymenutext = "Sorry, you can only go here in the full version of Runaway Robbit."
                changemenu($17, 0.0, 0.0)
            EndIf
        Else
            changemenu($0F, 0.0, 0.0)
        EndIf
    EndIf
    If (arg0 = "maptomenu") Then
        fadeout2($00, $00, $01, $01, $00)
        hidefullscreen()
        changemenu($02, -1.0, 0.0)
        fadein2($00, $00, $00, $01, $00, $00)
    EndIf
    If (arg0 = "numplayers") Then
        profiles(currentprofile)\Field14 = (profiles(currentprofile)\Field14 + $01)
        If (profiles(currentprofile)\Field14 > $02) Then
            profiles(currentprofile)\Field14 = $01
        EndIf
        saveprofiles()
    EndIf
    If (arg0 = "numplayers2") Then
        numberofplayers2 = (numberofplayers2 + $01)
        If (numberofplayers2 > $02) Then
            numberofplayers2 = $01
        EndIf
    EndIf
    If (arg0 = "continuegame") Then
        changemenu($00, 0.0, 0.0)
    EndIf
    If (arg0 = "exittomenu") Then
        changemenu($0E, 0.0, 0.0)
    EndIf
    If (arg0 = "yesexit") Then
        gameovertime = 360.0
        fadengui(gui_getobjectentity(gui_bigtext), 0.0, 1.0, 0.015)
        changemenu($00, 0.0, 0.0)
    EndIf
    If (arg0 = "noexit") Then
        changemenu($01, 0.0, 0.0)
    EndIf
    If (arg0 = "statstomap") Then
        savedemotime()
        fadeout2($00, $00, $01, $00, $00)
        If (gotomenuno = $09) Then
            showfullscreen("gfx/mappic.jpg")
        EndIf
        tempscore = player($01)\Field11
        If ((((specialmode > $00) And (numberofplayers2 = $02)) Or ((specialmode = $00) And (profiles(currentprofile)\Field14 = $02))) <> 0) Then
            tempscore = (tempscore + player($02)\Field11)
        EndIf
        If (gotomenuno = $0C) Then
            scoretohighlight = addscore(tempscore)
        EndIf
        If (((gotomenuno = $09) And (winorlose = $01)) <> 0) Then
            profiles(currentprofile)\Field6 = (profiles(currentprofile)\Field6 + tempscore)
            If (((profiles(currentprofile)\Field1 = currentlevel) And (profiles(currentprofile)\Field1 < $1D)) <> 0) Then
                profiles(currentprofile)\Field1 = (profiles(currentprofile)\Field1 + $01)
            EndIf
            profiles(currentprofile)\Field16 = player($01)\Field10
            profiles(currentprofile)\Field12 = player($01)\Field9
            profiles(currentprofile)\Field13 = player($01)\Field21
            saveprofiles()
        EndIf
        changemenu(gotomenuno, -1.0, 0.0)
        startmusic($02)
        gui_hidegroup($01)
        startmenubkg($26, $01)
    EndIf
    If (arg0 = "tryagain") Then
        fadeout2($00, $00, $01, $00, $00)
        changemenu($00, -1.0, 0.0)
        If (specialmode = $00) Then
            startlevel(currentlevel, profiles(currentprofile)\Field14, $00)
        Else
            startlevel(currentlevel, numberofplayers2, $00)
        EndIf
    EndIf
    If (arg0 = "postscore") Then
        tempscore = player($01)\Field11
        If ((((specialmode > $00) And (numberofplayers2 = $02)) Or ((specialmode = $00) And (profiles(currentprofile)\Field14 = $02))) <> 0) Then
            tempscore = (tempscore + player($02)\Field11)
        EndIf
        posthighscore(tempscore)
        scoretohighlight = addscore(tempscore)
        changemenu($0C, -1.0, 0.0)
        gui_hidegroup($01)
        startmenubkg($26, $01)
    EndIf
    If (arg0 = "reschangecontinue") Then
        changemenu(backmenu, 0.0, 0.0)
    EndIf
    If (arg0 = "optionsback") Then
        local5 = $00
        If (tempresmode <> loadresmode) Then
            local5 = $01
            oldresmode = loadresmode
            loadresmode = tempresmode
            saveres()
        EndIf
        If (local5 = $01) Then
            changemenu($1E, 0.0, 0.0)
        Else
            changemenu(backmenu, 0.0, 0.0)
        EndIf
    EndIf
    If (arg0 = "musicbutton") Then
        If (musicon = $01) Then
            musicon = $00
            pausechannel(musicchannel)
        Else
            musicon = $01
            resumechannel(musicchannel)
            If (channelplaying(musicchannel) = $00) Then
                startmusic($02)
            EndIf
        EndIf
        profiles(currentprofile)\Field4 = musicon
        saveprofiles()
    EndIf
    If (arg0 = "hintbutton") Then
        If (profiles(currentprofile)\Field3 = $01) Then
            profiles(currentprofile)\Field3 = $00
        Else
            profiles(currentprofile)\Field3 = $01
        EndIf
        saveprofiles()
    EndIf
    If (arg0 = "soundbutton") Then
        If (soundon = $01) Then
            soundon = $00
        Else
            soundon = $01
        EndIf
        profiles(currentprofile)\Field2 = soundon
        saveprofiles()
    EndIf
    If (arg0 = "screenbutton") Then
        tempresmode = (tempresmode + $01)
        If (tempresmode > $08) Then
            tempresmode = $01
        EndIf
        saveres()
    EndIf

; --- NEW: CLICK HANDLERS FOR CUSTOM SETTINGS ---
    If (arg0 = "autofirebutton") Then
        forcefireon = 1 - forcefireon
    EndIf
    
    If (arg0 = "crossvisbutton") Then
        crosshairvisible = 1 - crosshairvisible
    EndIf
    
    If (arg0 = "crosscolorbutton") Then
        If p1crosshairG = 255.0 Then
            p1crosshairR = 255.0 : p1crosshairG = 55.0 : p1crosshairB = 55.0
        ElseIf p1crosshairR = 255.0 Then
            p1crosshairR = 55.0 : p1crosshairG = 150.0 : p1crosshairB = 255.0
        Else
            p1crosshairR = 55.0 : p1crosshairG = 255.0 : p1crosshairB = 0.0
        EndIf
    EndIf

    If (arg0 = "moregames") Then
        othergames()
    EndIf
    If (arg0 = "buygame") Then
        buynow()
    EndIf
    If (arg0 = "playgame") Then
        changemenu($0A, 0.0, 0.0)
    EndIf
    If (arg0 = "options") Then
        backmenu = $02
        tempresmode = loadresmode
        changemenu($06, 0.0, 0.0)
    EndIf
    If (arg0 = "highscores") Then
        changemenu($0C, 0.0, 0.0)
    EndIf
    If (arg0 = "exitgame") Then
        If (demoversionon = $01) Then
            fadeout2($00, $00, $01, $00, $01)
            changemenu($00, -1.0, 0.0)
            buycontinueexit = $01
            soundon = $00
            startlevel($25, $01, $01)
        Else
            end()
        EndIf
    EndIf
    If (arg0 = "instructions") Then
        fadeout2($00, $00, $01, $00, $00)
        showfullscreen("gfx/instructions.jpg")
        changemenu($08, -1.0, 0.0)
        backmenu = $02
        fadein2($00, $00, $01, $00, $00, $00)
    EndIf
    If (arg0 = "instructionscontinue") Then
        If (backmenu = $00) Then
            fadeout2($00, $00, $01, $00, $00)
            changemenu($00, -1.0, 0.0)
            hidefullscreen()
            startlevel($01, profiles(currentprofile)\Field14, $00)
        Else
            fadeout2($00, $00, $01, $00, $00)
            hidefullscreen()
            changemenu(backmenu, -1.0, 0.0)
            fadein2($00, $00, $00, $01, $00, $00)
        EndIf
    EndIf
    If (arg0 = "nameokay") Then
        currentprofile = (currentprofile + $01)
        profiles(currentprofile)\Field0 = left(menus($05)\Field15[$02], (len(menus($05)\Field15[$02]) - $01))
        newprofile()
        changemenu($02, 0.0, 0.0)
        clearinputbuffer()
    EndIf
    If (arg0 = "skillbutton") Then
        tempskill = (tempskill + $01)
        If (tempskill > $04) Then
            tempskill = $01
        EndIf
    EndIf
    If (arg0 = "yesdelete") Then
        deleteprofile()
        changemenu($04, 0.0, 0.0)
    EndIf
    If (arg0 = "nodelete") Then
        changemenu($04, 0.0, 0.0)
    EndIf
    If (arg0 = "deleteslot1") Then
        profiletodelete = $01
        changemenu($03, 0.0, 0.0)
    EndIf
    If (arg0 = "deleteslot2") Then
        profiletodelete = $02
        changemenu($03, 0.0, 0.0)
    EndIf
    If (arg0 = "deleteslot3") Then
        profiletodelete = $03
        changemenu($03, 0.0, 0.0)
    EndIf
    If (arg0 = "deleteslot4") Then
        profiletodelete = $04
        changemenu($03, 0.0, 0.0)
    EndIf
    If (arg0 = "deleteslot5") Then
        profiletodelete = $05
        changemenu($03, 0.0, 0.0)
    EndIf
    If (arg0 = "deleteslot6") Then
        profiletodelete = $06
        changemenu($03, 0.0, 0.0)
    EndIf
    If (arg0 = "playslot1") Then
        If (len(profiles($01)\Field0) > $00) Then
            changeprofile($01)
            changemenu($02, 0.0, 0.0)
        Else
            currentprofile = $00
            changemenu($05, 0.0, 0.0)
        EndIf
    EndIf
    If (arg0 = "playslot2") Then
        If (len(profiles($02)\Field0) > $00) Then
            changeprofile($02)
            changemenu($02, 0.0, 0.0)
        Else
            currentprofile = $01
            changemenu($05, 0.0, 0.0)
        EndIf
    EndIf
    If (arg0 = "playslot3") Then
        If (len(profiles($03)\Field0) > $00) Then
            changeprofile($03)
            changemenu($02, 0.0, 0.0)
        Else
            currentprofile = $02
            changemenu($05, 0.0, 0.0)
        EndIf
    EndIf
    If (arg0 = "playslot4") Then
        If (len(profiles($04)\Field0) > $00) Then
            changeprofile($04)
            changemenu($02, 0.0, 0.0)
        Else
            currentprofile = $03
            changemenu($05, 0.0, 0.0)
        EndIf
    EndIf
    If (arg0 = "playslot5") Then
        If (len(profiles($05)\Field0) > $00) Then
            changeprofile($05)
            changemenu($02, 0.0, 0.0)
        Else
            currentprofile = $04
            changemenu($05, 0.0, 0.0)
        EndIf
    EndIf
    If (arg0 = "playslot6") Then
        If (len(profiles($06)\Field0) > $00) Then
            changeprofile($06)
            changemenu($02, 0.0, 0.0)
        Else
            currentprofile = $05
            changemenu($05, 0.0, 0.0)
        EndIf
    EndIf
    If (arg0 = "changeplayer") Then
        changemenu($04, 0.0, 0.0)
    EndIf
    If (arg0 = "selectback") Then
        changemenu($02, 0.0, 0.0)
    EndIf
    Return $00
End Function
