Function loadprofiles%()
    Local local0%
    Local local1%
    If (filetype("profiles.dat") = $00) Then
        saveprofiles()
    EndIf
    local0 = readfile("profiles.dat")
    currentprofile = readint(local0)
    If (((currentprofile < $00) Or (currentprofile > $0A)) <> 0) Then
        currentprofile = $00
    EndIf
    For local1 = $01 To $0A Step $01
        profiles(local1)\Field0 = readstring(local0)
        profiles(local1)\Field4 = readint(local0)
        profiles(local1)\Field2 = readint(local0)
        profiles(local1)\Field3 = readint(local0)
        profiles(local1)\Field1 = readint(local0)
        profiles(local1)\Field5 = readint(local0)
        profiles(local1)\Field6 = readint(local0)
        profiles(local1)\Field7 = readint(local0)
        profiles(local1)\Field8 = readint(local0)
        profiles(local1)\Field9 = readint(local0)
        profiles(local1)\Field10 = readint(local0)
        profiles(local1)\Field11 = readint(local0)
        profiles(local1)\Field12 = readint(local0)
        profiles(local1)\Field13 = readint(local0)
        profiles(local1)\Field14 = readint(local0)
        profiles(local1)\Field15 = readint(local0)
        profiles(local1)\Field16 = readint(local0)
        If (profiles(local1)\Field15 = $00) Then
            profiles(local1)\Field15 = $01
        EndIf
    Next
    closefile(local0)
    If (currentprofile > $00) Then
        soundon = profiles(currentprofile)\Field2
        musicon = profiles(currentprofile)\Field4
    Else
        soundon = $01
        musicon = $01
    EndIf
    Return $00
End Function
