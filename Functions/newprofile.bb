Function newprofile%()
    profiles(currentprofile)\Field2 = $01
    profiles(currentprofile)\Field3 = $01
    profiles(currentprofile)\Field4 = $01
    profiles(currentprofile)\Field6 = $00
    profiles(currentprofile)\Field1 = $01
    profiles(currentprofile)\Field5 = tempskill
    profiles(currentprofile)\Field7 = $01
    profiles(currentprofile)\Field14 = $01
    profiles(currentprofile)\Field8 = $01
    profiles(currentprofile)\Field9 = $00
    profiles(currentprofile)\Field10 = $00
    profiles(currentprofile)\Field11 = $00
    profiles(currentprofile)\Field15 = $01
    Select tempskill
        Case $01
            profiles(currentprofile)\Field16 = $02
            profiles(currentprofile)\Field12 = $04
            profiles(currentprofile)\Field13 = $03
        Case $02
            profiles(currentprofile)\Field16 = $02
            profiles(currentprofile)\Field12 = $03
            profiles(currentprofile)\Field13 = $02
        Case $03
            profiles(currentprofile)\Field16 = $02
            profiles(currentprofile)\Field12 = $02
            profiles(currentprofile)\Field13 = $01
        Case $04
            profiles(currentprofile)\Field16 = $02
            profiles(currentprofile)\Field12 = $01
            profiles(currentprofile)\Field13 = $01
    End Select
    profiles(currentprofile)\Field14 = $01
    musicon = $01
    soundon = $01
    If (musicon = $00) Then
        pausechannel(musicchannel)
    Else
        resumechannel(musicchannel)
        If (channelplaying(musicchannel) = $00) Then
            startmusic($02)
        EndIf
    EndIf
    saveprofiles()
    Return $00
End Function
