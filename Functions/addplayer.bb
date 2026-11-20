Function addplayer%(arg0%, arg1#, arg2#, arg3%, arg4%, arg5%, arg6%, arg7%)
    Local local0%
    Local local1%
    player(arg0)\Field0 = $01
    actors(player(arg0)\Field1)\Field19 = $00
    actors(player(arg0)\Field1)\Field20 = $00
    actors(player(arg0)\Field1)\Field59 = 1500000.0
    player(arg0)\Field3 = arg3
    player(arg0)\Field4 = arg1
    player(arg0)\Field5 = arg2
    player(arg0)\Field16 = $00
    player(arg0)\Field12 = 120.0
    player(arg0)\Field33 = 0.0
    player(arg0)\Field9 = arg5
    player(arg0)\Field19 = 1.0
    player(arg0)\Field10 = arg4
    player(arg0)\Field14 = 0.0
    player(arg0)\Field15 = $01
    player(arg0)\Field21 = arg6
    If (((specialmode = $00) Or (arg7 = $01)) <> 0) Then
        player(arg0)\Field28 = $00
        player(arg0)\Field29 = $00
        player(arg0)\Field30 = $00
        player(arg0)\Field31 = $00
        player(arg0)\Field32 = $00
    Else
        local0 = (player(arg0)\Field29 - $01)
        If (local0 < $00) Then
            local0 = $00
        EndIf
        player(arg0)\Field29 = local0
        local0 = (player(arg0)\Field30 - $01)
        If (local0 < $00) Then
            local0 = $00
        EndIf
        player(arg0)\Field30 = local0
        local0 = (player(arg0)\Field31 - $01)
        If (local0 < $00) Then
            local0 = $00
        EndIf
        player(arg0)\Field31 = local0
        local0 = (player(arg0)\Field32 - $01)
        If (local0 < $00) Then
            local0 = $00
        EndIf
        player(arg0)\Field32 = local0
    EndIf
    player(arg0)\Field13 = 0.0
    player(arg0)\Field24 = 0.0
    player(arg0)\Field23 = $00
    player(arg0)\Field25 = 200.0
    player(arg0)\Field26 = 200.0
    For local1 = $01 To $0A Step $01
        showentity(actors(player(arg0)\Field1)\Field70[local1])
        actors(player(arg0)\Field1)\Field75[local1] = 0.0
    Next
    actors(player(arg0)\Field1)\Field30 = $01
    actors(player(arg0)\Field1)\Field0 = $00
    doplayerweaponsetup(player(arg0), arg0)
    Return $00
End Function
