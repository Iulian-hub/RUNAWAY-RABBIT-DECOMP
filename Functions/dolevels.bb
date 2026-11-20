Function dolevels%()
    Local local0%
    Local local1%
    Local local2%
    Local local3$
    Local local4#
    Local local5.levelevent
    Local local6%
    Local local7%
    Local local8%
    local0 = (Int (600.0 / fl\Field1))
    If (local0 < $02) Then
        local0 = $02
    EndIf
    If ((((((rand($01, local0) = $02) And (specialmode = $02)) And (currentmenu = $00)) And (fadingin = $00)) And (fadingout = $00)) <> 0) Then
        makebonus((Float rand($FFFFFF38, $C8)), 310.0, $00)
    EndIf
    nospeciallevelmodetime = (nospeciallevelmodetime + fl\Field1)
    local1 = $02
    If (((hadcoindrop = $00) And (leveltime > (longesttime / 2.0))) <> 0) Then
        local1 = $01
    EndIf
    local2 = (Int ((leveltime / longesttime) * 99.0))
    If ((((2200.0 < nospeciallevelmodetime) And (local2 < $50)) And (specialmode = $00)) <> 0) Then
        If (local1 = $01) Then
            hadcoindrop = $01
        EndIf
        speciallevelmode(local1)
        nospeciallevelmodetime = 0.0
    EndIf
    dospeciallevelmode()
    If ((((((player($01)\Field0 = $00) And (player($02)\Field0 = $00)) And (0.0 = gameovertime)) And (0.0 = wintime)) And (gamemode < $02)) <> 0) Then
        gameovertime = 360.0
        fadengui(gui_getobjectentity(gui_bigtext), 0.0, 1.0, 0.015)
    EndIf
    If (0.0 < getreadytime) Then
        willwin = $00
        getreadytime = (getreadytime - fl\Field1)
        If (gamemode <> $03) Then
            gui_setstring(gui_bigtext, "GET READY", $00)
        EndIf
        If (((60.0 > getreadytime) And (gamemode <> $03)) <> 0) Then
            fadengui(gui_getobjectentity(gui_bigtext), 1.0, 0.0, -0.02)
            gui_positionobject(gui_bigtext, 320.0, 240.0)
        EndIf
        If (1.0 > getreadytime) Then
            getreadytime = 0.0
        EndIf
        Return $00
    EndIf
    If (0.0 < gameovertime) Then
        willwin = $00
        gameovertime = (gameovertime - fl\Field1)
        gui_setstring(gui_bigtext, "GAME OVER", $00)
        If (180.0 > gameovertime) Then
            fadengui(gui_getobjectentity(gui_bigtext), 1.0, 0.0, -0.02)
            gui_positionobject(gui_bigtext, 320.0, 240.0)
        EndIf
        If (1.0 > gameovertime) Then
            changemenu($0D, 0.0, 0.0)
            winorlose = $00
            gameovertime = -1.0
        EndIf
        Return $00
    EndIf
    If (((willwin = $01) And (currentenemies < $01)) <> 0) Then
        willwin = $00
        wintime = 360.0
        fadengui(gui_getobjectentity(gui_bigtext), 0.0, 1.0, 0.015)
    EndIf
    If (0.0 < wintime) Then
        wintime = (wintime - fl\Field1)
        local3 = "Level Complete!"
        If (((currentlevel = $1D) Or (specialmode > $00)) <> 0) Then
            local3 = "*** YOU WIN!!! ***"
        EndIf
        gui_setstring(gui_bigtext, local3, $00)
        If (180.0 > wintime) Then
            fadengui(gui_getobjectentity(gui_bigtext), 1.0, 0.0, -0.02)
            gui_positionobject(gui_bigtext, 320.0, 240.0)
        EndIf
        If (1.0 > wintime) Then
            changemenu($0D, 0.0, 0.0)
            winorlose = $01
            wintime = -1.0
        EndIf
        Return $00
    EndIf
    local4 = 1.5
    If (specialmode > $00) Then
        local4 = 1.0
    EndIf
    If (currentlevel = $01) Then
        local4 = 2.2
    EndIf
    If (leveltime < levels(currentlevel, (currentevent + $01))\Field0) Then
        leveltime = ((fl\Field1 * local4) + leveltime)
    EndIf
    local5 = levels(currentlevel, (currentevent + $01))
    If ((((local5\Field0 <= leveltime) And (0.0 < local5\Field0)) And ((bossesdead >= local5\Field1) Or (local5\Field1 = $00))) <> 0) Then
        currentevent = (currentevent + $01)
        recountcurrentenemiestype()
        If (local5\Field4 > $00) Then
            startmusic(local5\Field4)
        EndIf
        If (((((local5\Field26 = $01) And (0.0 = gameovertime)) And (0.0 = wintime)) And (gamemode < $02)) <> 0) Then
            willwin = $01
        EndIf
        If (len(local5\Field25) > $00) Then
            makescoreflyup(0.0, 0.0, local5\Field25, $32, $DC, $DC, $19, $00, 3.0, $01, 0.015, 2.0, 11.0, $01)
        EndIf
        If (local5\Field28 > $00) Then
            changemenu(local5\Field28, 0.0, 0.0)
        EndIf
        For local6 = $01 To $05 Step $01
            If (local5\Field5[local6] > $00) Then
                local7 = makeenemy(local5\Field5[local6], local5\Field6[local6], local5\Field7[local6], local5\Field8[local6], $00, local5\Field11[local6])
                If (local7 > $00) Then
                    If (0.0 < local5\Field9[local6]) Then
                        actors(local7)\Field58 = (local5\Field9[local6] * actors(local7)\Field58)
                        actors(local7)\Field59 = actors(local7)\Field58
                    EndIf
                    If (0.0 < local5\Field10[local6]) Then
                        For local8 = $01 To $0A Step $01
                            actors(local7)\Field76[local8] = (actors(local7)\Field76[local8] * local5\Field10[local6])
                        Next
                    EndIf
                EndIf
            EndIf
        Next
        For local6 = $01 To $01 Step $01
            If (local5\Field12[local6] > $00) Then
                makeboss(local5\Field13[local6], local5\Field14[local6], local5\Field12[local6])
            EndIf
        Next
        For local6 = $01 To $06 Step $01
            If (local5\Field15[local6] > $00) Then
                makebackgroundenemy(local5\Field18[local6], local5\Field19[local6], local5\Field15[local6], (Float local5\Field16[local6]), (Float local5\Field17[local6]))
            EndIf
        Next
    EndIf
    Return $00
End Function
