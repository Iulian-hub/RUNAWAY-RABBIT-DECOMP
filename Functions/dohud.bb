Function dohud%()
    Local local0%
    Local local1.actortype
    Local local2#
    gui_setstring(gui_fpstext, ("FPS: " + (Str (Int fl\Field2))), $00)
    If (1.0 = debugmode) Then
        showentity(gui_getobjectentity(gui_fpstext))
    Else
        hideentity(gui_getobjectentity(gui_fpstext))
    EndIf
    local0 = (Int ((leveltime / longesttime) * 99.0))
    If (0.0 < wintime) Then
        local0 = $64
    EndIf
    If (local0 < $00) Then
        local0 = $00
    EndIf
    gui_setstring(gui_completetext, ((Str local0) + "% COMPLETED"), $00)
    gui_positionobject(gui_completetext, 320.0, 5.0)
    If ((((gamemode < $03) And (specialmode < $02)) And ((currentmenu = $00) Or (currentmenu = $01))) <> 0) Then
        showentity(gui_getobjectentity(gui_completetext))
    Else
        hideentity(gui_getobjectentity(gui_completetext))
    EndIf
    If (((demoversionon = $01) And ((currentmenu = $00) Or (currentmenu = $01))) <> 0) Then
        showentity(gui_getobjectentity(gui_demotimertext))
        gui_positionobject(gui_demotimertext, 320.0, 460.0)
    Else
        hideentity(gui_getobjectentity(gui_demotimertext))
        gui_positionobject(gui_demotimertext, 3320.0, 3240.0)
    EndIf
    If (player($01)\Field0 = $01) Then
        showentity(gui_getobjectentity(gui_player1lives))
        showentity(gui_getobjectentity(gui_player1livestext))
        showentity(gui_getobjectentity(gui_player1score))
        showentity(gui_getobjectentity(gui_player1shields))
        showentity(gui_getobjectentity(gui_player1bombs))
        hideentity(gui_getobjectentity(gui_player1beamlaser))
        If ((playersetup($01)\Field6 + player($01)\Field30) > $00) Then
            showentity(gui_getobjectentity(gui_player1beamlaser))
        EndIf
        gui_setstring(gui_player1livestext, (Str player($01)\Field10), $00)
        gui_setstring(gui_player1score, ("$" + digitalnumber(player($01)\Field11, $07)), $00)
        gui_scaleobject(gui_player1shields, (Float (player($01)\Field9 * $18)), 16.0)
        gui_scaleobject(gui_player1bombs, (Float (player($01)\Field21 * $18)), 24.0)
        local1 = actors(player($01)\Field1)
        local2 = (local1\Field75[$09] / (local1\Field74[$09] * local1\Field76[$09]))
        If (1.0 < local2) Then
            local2 = 1.0
        EndIf
        If (0.01 > local2) Then
            local2 = 0.01
        EndIf
        gui_scaleobject(gui_player1beamlaser, 16.0, (96.0 * local2))
        gui_positionobject(gui_player1beamlaser, 9.0, ((96.0 - (96.0 * local2)) + 126.0))
        If (1.0 = local2) Then
            entitycolor(gui_getobjectentity(gui_player1beamlaser), 255.0, 255.0, 50.0)
        Else
            entitycolor(gui_getobjectentity(gui_player1beamlaser), 50.0, 255.0, 50.0)
        EndIf
        Select player($01)\Field9
            Case $00
                hideentity(gui_getobjectentity(gui_player1shields))
            Case $01
                scaletexture(player1shieldtex, 1.0, 1.0)
            Case $02
                scaletexture(player1shieldtex, 0.5, 1.0)
            Case $03
                scaletexture(player1shieldtex, (1.0 / 3.0), 1.0)
            Case $04
                scaletexture(player1shieldtex, 0.25, 1.0)
        End Select
        Select player($01)\Field21
            Case $00
                hideentity(gui_getobjectentity(gui_player1bombs))
            Case $01
                scaletexture(player1bombtex, 1.0, 1.0)
            Case $02
                scaletexture(player1bombtex, 0.5, 1.0)
            Case $03
                scaletexture(player1bombtex, (1.0 / 3.0), 1.0)
            Case $04
                scaletexture(player1bombtex, 0.25, 1.0)
        End Select
    Else
        hideentity(gui_getobjectentity(gui_player1lives))
        hideentity(gui_getobjectentity(gui_player1livestext))
        hideentity(gui_getobjectentity(gui_player1score))
        hideentity(gui_getobjectentity(gui_player1shields))
        hideentity(gui_getobjectentity(gui_player1bombs))
        hideentity(gui_getobjectentity(gui_player1beamlaser))
    EndIf
    If (player($02)\Field0 = $01) Then
        showentity(gui_getobjectentity(gui_player2lives))
        showentity(gui_getobjectentity(gui_player2livestext))
        showentity(gui_getobjectentity(gui_player2score))
        showentity(gui_getobjectentity(gui_player2shields))
        showentity(gui_getobjectentity(gui_player2bombs))
        hideentity(gui_getobjectentity(gui_player2beamlaser))
        If ((playersetup($02)\Field6 + player($02)\Field30) > $00) Then
            showentity(gui_getobjectentity(gui_player2beamlaser))
        EndIf
        gui_setstring(gui_player2livestext, (Str player($02)\Field10), $00)
        gui_setstring(gui_player2score, ("$" + digitalnumber(player($02)\Field11, $07)), $00)
        gui_scaleobject(gui_player2shields, (Float (player($02)\Field9 * $18)), 16.0)
        gui_scaleobject(gui_player2bombs, (Float (player($02)\Field21 * $18)), 24.0)
        local1 = actors(player($02)\Field1)
        local2 = (local1\Field75[$09] / (local1\Field74[$09] * local1\Field76[$09]))
        If (1.0 < local2) Then
            local2 = 1.0
        EndIf
        If (0.01 > local2) Then
            local2 = 0.01
        EndIf
        gui_scaleobject(gui_player2beamlaser, 16.0, (96.0 * local2))
        gui_positionobject(gui_player2beamlaser, 614.0, ((96.0 - (96.0 * local2)) + 126.0))
        If (1.0 = local2) Then
            entitycolor(gui_getobjectentity(gui_player2beamlaser), 255.0, 255.0, 50.0)
        Else
            entitycolor(gui_getobjectentity(gui_player2beamlaser), 255.0, 50.0, 50.0)
        EndIf
        Select player($02)\Field9
            Case $00
                hideentity(gui_getobjectentity(gui_player2shields))
            Case $01
                scaletexture(player2shieldtex, 1.0, 1.0)
                gui_positionobject(gui_player2shields, 609.0, 75.0)
            Case $02
                scaletexture(player2shieldtex, 0.5, 1.0)
                gui_positionobject(gui_player2shields, 585.0, 75.0)
            Case $03
                scaletexture(player2shieldtex, (1.0 / 3.0), 1.0)
                gui_positionobject(gui_player2shields, 561.0, 75.0)
            Case $04
                scaletexture(player2shieldtex, 0.25, 1.0)
                gui_positionobject(gui_player2shields, 537.0, 75.0)
        End Select
        Select player($02)\Field21
            Case $00
                hideentity(gui_getobjectentity(gui_player2bombs))
            Case $01
                scaletexture(player2bombtex, 1.0, 1.0)
                gui_positionobject(gui_player2bombs, 609.0, 100.0)
            Case $02
                scaletexture(player2bombtex, 0.5, 1.0)
                gui_positionobject(gui_player2bombs, 585.0, 100.0)
            Case $03
                scaletexture(player2bombtex, (1.0 / 3.0), 1.0)
                gui_positionobject(gui_player2bombs, 561.0, 100.0)
            Case $04
                scaletexture(player2bombtex, 0.25, 1.0)
                gui_positionobject(gui_player2bombs, 537.0, 100.0)
        End Select
    Else
        hideentity(gui_getobjectentity(gui_player2lives))
        hideentity(gui_getobjectentity(gui_player2livestext))
        hideentity(gui_getobjectentity(gui_player2score))
        hideentity(gui_getobjectentity(gui_player2shields))
        hideentity(gui_getobjectentity(gui_player2bombs))
        hideentity(gui_getobjectentity(gui_player2beamlaser))
    EndIf
    Return $00
End Function
