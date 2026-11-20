Function startmusic%(arg0%)
    If (((channelplaying(musicchannel) = $01) And (currentmusicnum = arg0)) <> 0) Then
        Return $00
    EndIf
    If (musicchannel > $00) Then
        If (channelplaying(musicchannel) = $01) Then
            stopchannel(musicchannel)
        EndIf
    EndIf
    If (musicon = $01) Then
        musicchannel = playsound(musics(arg0))
        currentmusicnum = arg0
    EndIf
    Return $00
End Function
