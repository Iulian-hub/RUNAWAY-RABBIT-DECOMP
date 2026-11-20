Function firebomb%(arg0.playertype)
    Local local0%
    If (gamemode = $03) Then
        Return $00
    EndIf
    If (soundon = $01) Then
        playsoundoveride(sndbigbomb)
    EndIf
    arg0\Field21 = (arg0\Field21 - $01)
    bombtime = 120.0
    whiteflash(1.0, (1.0 / 180.0), 0.9, $0A)
    stopweapons()
    If (bossbarnum = $00) Then
        For local0 = $01 To $14 Step $01
            If (((local0 <> player($01)\Field1) And (local0 <> player($02)\Field1)) <> 0) Then
                If (((actors(local0)\Field3 = $00) And (enemies(local0)\Field6 = $00)) <> 0) Then
                    actors(local0)\Field59 = -1000.0
                EndIf
                actors(local0)\Field79 = arg0\Field1
            ElseIf (enemies(local0)\Field6 = $01) Then
                actors(local0)\Field59 = (actors(local0)\Field59 - (actors(local0)\Field58 / 4.0))
            EndIf
        Next
    Else
        For local0 = $01 To $14 Step $01
            If (((local0 <> player($01)\Field1) And (local0 <> player($02)\Field1)) <> 0) Then
                If (actors(local0)\Field3 = $00) Then
                    actors(local0)\Field59 = (actors(local0)\Field59 - (actors(local0)\Field58 / 8.0))
                EndIf
                actors(local0)\Field79 = arg0\Field1
            EndIf
        Next
    EndIf
    Return $00
End Function
