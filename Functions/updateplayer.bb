Function updateplayer%(arg0.playertype)
    Local local0%
    Local local1%
    Local local3%
    If (arg0 = player($01)) Then
        local0 = $01
    Else
        local0 = $02
    EndIf
    If (0.0 < arg0\Field24) Then
        arg0\Field24 = (arg0\Field24 - fl\Field1)
        If (0.0 > arg0\Field24) Then
            arg0\Field24 = 0.0
        EndIf
        If (0.0 = arg0\Field24) Then
            If (arg0\Field23 > bestcombo) Then
                bestcombo = arg0\Field23
            EndIf
            If (arg0\Field23 > $02) Then
                local1 = (arg0\Field23 * $1F4)
                If (soundon = $01) Then
                    playsoundoveride(sndcombo)
                EndIf
                If (local0 = $01) Then
                    makescoreflyup(arg0\Field25, (arg0\Field26 - 20.0), ("COMBO X " + (Str arg0\Field23)), $64, $FF, $64, $10, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                EndIf
                If (local0 = $02) Then
                    makescoreflyup(arg0\Field25, (arg0\Field26 - 20.0), ("COMBO X " + (Str arg0\Field23)), $FF, $64, $64, $10, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                EndIf
                If (local0 = $01) Then
                    makescoreflyup(arg0\Field25, (arg0\Field26 - 60.0), ("$" + (Str local1)), $64, $FF, $64, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                EndIf
                If (local0 = $02) Then
                    makescoreflyup(arg0\Field25, (arg0\Field26 - 60.0), ("$" + (Str local1)), $FF, $64, $64, $0F, $01, 5.0, $00, 0.0075, 1.0, 7.0, $00)
                EndIf
                arg0\Field11 = (arg0\Field11 + local1)
            EndIf
            arg0\Field23 = $00
        EndIf
    EndIf
    positionentity(arg0\Field18, arg0\Field4, arg0\Field5, 0.0, $00)
    entityalpha(arg0\Field18, (arg0\Field19 - 0.3))
    Select arg0\Field9
        Case $00
            entitycolor(arg0\Field18, 255.0, 255.0, 255.0)
            If (local0 = $01) Then
                entitycolor(gui_getobjectentity(gui_player1shields), 255.0, 255.0, 255.0)
            EndIf
            If (local0 = $02) Then
                entitycolor(gui_getobjectentity(gui_player2shields), 255.0, 255.0, 255.0)
            EndIf
        Case $01
            entitycolor(arg0\Field18, (((arg0\Field19 * 200.0) + -200.0) + 255.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0))
            If (local0 = $01) Then
                entitycolor(gui_getobjectentity(gui_player1shields), (((arg0\Field19 * 200.0) + -200.0) + 255.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0))
            EndIf
            If (local0 = $02) Then
                entitycolor(gui_getobjectentity(gui_player2shields), (((arg0\Field19 * 200.0) + -200.0) + 255.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0))
            EndIf
        Case $02
            entitycolor(arg0\Field18, (((arg0\Field19 * 200.0) + -200.0) + 255.0), (((arg0\Field19 * 200.0) + -200.0) + 255.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0))
            If (local0 = $01) Then
                entitycolor(gui_getobjectentity(gui_player1shields), (((arg0\Field19 * 200.0) + -200.0) + 255.0), (((arg0\Field19 * 200.0) + -200.0) + 255.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0))
            EndIf
            If (local0 = $02) Then
                entitycolor(gui_getobjectentity(gui_player2shields), (((arg0\Field19 * 200.0) + -200.0) + 255.0), (((arg0\Field19 * 200.0) + -200.0) + 255.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0))
            EndIf
        Case $03
            entitycolor(arg0\Field18, (((arg0\Field19 * 200.0) + -200.0) + 100.0), (((arg0\Field19 * 200.0) + -200.0) + 255.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0))
            If (local0 = $01) Then
                entitycolor(gui_getobjectentity(gui_player1shields), (((arg0\Field19 * 200.0) + -200.0) + 100.0), (((arg0\Field19 * 200.0) + -200.0) + 255.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0))
            EndIf
            If (local0 = $02) Then
                entitycolor(gui_getobjectentity(gui_player2shields), (((arg0\Field19 * 200.0) + -200.0) + 100.0), (((arg0\Field19 * 200.0) + -200.0) + 255.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0))
            EndIf
        Case $04
            entitycolor(arg0\Field18, (((arg0\Field19 * 200.0) + -200.0) + 100.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0), (((arg0\Field19 * 200.0) + -200.0) + 255.0))
            If (local0 = $01) Then
                entitycolor(gui_getobjectentity(gui_player1shields), (((arg0\Field19 * 200.0) + -200.0) + 100.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0), (((arg0\Field19 * 200.0) + -200.0) + 255.0))
            EndIf
            If (local0 = $02) Then
                entitycolor(gui_getobjectentity(gui_player2shields), (((arg0\Field19 * 200.0) + -200.0) + 100.0), (((arg0\Field19 * 200.0) + -200.0) + 100.0), (((arg0\Field19 * 200.0) + -200.0) + 255.0))
            EndIf
    End Select
    positionentity(actors(arg0\Field1)\Field42, arg0\Field4, arg0\Field5, 0.0, $00)
    actors(arg0\Field1)\Field48 = arg0\Field4
    actors(arg0\Field1)\Field49 = arg0\Field5
    If (0.0 = arg0\Field12) Then
        entityalpha(actors(arg0\Field1)\Field42, arg0\Field14)
    ElseIf (120.0 < arg0\Field12) Then
        entityalpha(actors(arg0\Field1)\Field42, 0.25)
    Else
        entityalpha(actors(arg0\Field1)\Field42, arg0\Field14)
    EndIf
    For local3 = $01 To $0A Step $01
        entityalpha(actors(arg0\Field1)\Field70[local3], arg0\Field14)
    Next
    If (0.0 < arg0\Field13) Then
        entitycolor(actors(arg0\Field1)\Field42, 255.0, 100.0, 100.0)
        arg0\Field13 = (arg0\Field13 - fl\Field1)
        If (1.0 > arg0\Field13) Then
            doplayerweaponsetup(arg0, local0)
            entitycolor(actors(arg0\Field1)\Field42, 255.0, 255.0, 255.0)
        EndIf
    EndIf
    Return $00
End Function
