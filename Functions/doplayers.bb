Function doplayers%(arg0.actortype, arg1%)
    Local local0%
    If (arg1 = player($01)\Field1) Then
        local0 = $01
    EndIf
    If (arg1 = player($02)\Field1) Then
        local0 = $02
    EndIf
    If (local0 = $00) Then
        Return $00
    EndIf
    If (0.0 < bombtime) Then
        bombtime = (bombtime - (fl\Field1 / 2.0))
        If ((rand($01, (Int (5.0 / fl\Field1))) And (60.0 < bombtime)) <> 0) Then
            makeexplosion((Float rand($FFFFFED4, $12C)), (Float rand($FFFFFED4, $12C)), (Float rand($32, $64)), 1.0)
        EndIf
    EndIf
    If (90.0 < bombtime) Then
        stopweapons()
    EndIf
    If (0.0 > bombtime) Then
        bombtime = 0.0
    EndIf
    If (player(local0)\Field0 = $01) Then
        actors(player(local0)\Field1)\Field19 = $00
        showentity(player(local0)\Field18)
        showentity(player(local0)\Field2)
        docrosshair(player(local0))
        If (player(local0)\Field16 = $00) Then
            doplayermovement(player(local0))
            setturretposition(arg0, arg1)
            doplayerweapons(player(local0))
        EndIf
        doplayerhits(player(local0))
        updateplayer(player(local0))
    Else
        actors(player(local0)\Field1)\Field19 = $01
        hideentity(player(local0)\Field2)
        hideentity(player(local0)\Field18)
    EndIf
    Return $00
End Function
