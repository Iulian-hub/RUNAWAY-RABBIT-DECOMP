Function changeprofile%(arg0%)
    currentprofile = arg0
    If (currentprofile > $00) Then
        soundon = profiles(currentprofile)\Field2
        musicon = profiles(currentprofile)\Field4
    Else
        soundon = $01
        musicon = $01
    EndIf
    If (musicon = $00) Then
    EndIf
    saveprofiles()
    Return $00
End Function
