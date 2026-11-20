Function deleteprofile%()
    profiles(profiletodelete)\Field0 = ""
    profiles(profiletodelete)\Field2 = $00
    profiles(profiletodelete)\Field4 = $00
    profiles(profiletodelete)\Field3 = $00
    profiles(profiletodelete)\Field5 = $00
    profiles(profiletodelete)\Field6 = $00
    profiles(profiletodelete)\Field1 = $00
    profiles(profiletodelete)\Field7 = $00
    profiles(profiletodelete)\Field8 = $00
    profiles(profiletodelete)\Field9 = $00
    profiles(profiletodelete)\Field10 = $00
    profiles(profiletodelete)\Field11 = $00
    profiles(profiletodelete)\Field12 = $00
    profiles(profiletodelete)\Field13 = $00
    profiles(currentprofile)\Field14 = $00
    saveprofiles()
    Return $00
End Function
