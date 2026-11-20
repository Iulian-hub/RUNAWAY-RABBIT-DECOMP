Function viewworldscores%()
    stopchannel(musicchannel)
    If (affiliatescores = $00) Then
        execfile("MightyScore.exe")
    Else
        execfile("MightyScoreAff.exe")
    EndIf
    startmusic($02)
    Return $00
End Function
