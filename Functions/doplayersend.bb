Function doplayersend%(arg0.actortype, arg1%)
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
    If (player(local0)\Field0 = $01) Then
        If (player(local0)\Field16 = $01) Then
            entitytexture(actors(player(local0)\Field1)\Field42, hamsterdeadtex, player(local0)\Field22, $00)
        EndIf
    EndIf
    Return $00
End Function
