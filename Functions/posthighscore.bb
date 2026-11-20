Function posthighscore%(arg0%)
    stopchannel(musicchannel)
    If (affiliatescores = $00) Then
        execfile(("MightyScore.exe sdF435asd45" + (Str arg0)))
    Else
        execfile(("MightyScoreAff.exe sdF435asd45" + (Str arg0)))
    EndIf
    startmusic($02)
    Return $00
End Function
