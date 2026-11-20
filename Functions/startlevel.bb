Function startlevel%(arg0%, arg1%, arg2%)
    Local local0%
    Local local1%
    longesttime = 0.0
    For local0 = $01 To $3C Step $01
        If (longesttime < levels(arg0, local0)\Field0) Then
            longesttime = levels(arg0, local0)\Field0
        EndIf
    Next
    hadcoindrop = $00
    player($01)\Field7 = $00
    player($02)\Field7 = $00
    player($01)\Field8 = $00
    player($02)\Field8 = $00
    totalenemies = $00
    bossesdead = $00
    bestcombo = $00
    local1 = $00
    bonustimer = 0.0
    willwin = $00
    lastspeciallevelmode = $00
    nospeciallevelmodetime = 0.0
    leveltimewait = $00
    speciallevelmode = $00
    speciallevelmodetime = 0.0
    If (specialmode = $00) Then
        playersetup($01)\Field0 = profiles(currentprofile)\Field12
        playersetup($01)\Field1 = profiles(currentprofile)\Field16
        playersetup($01)\Field3 = profiles(currentprofile)\Field13
        playersetup($01)\Field4 = profiles(currentprofile)\Field7
        playersetup($01)\Field8 = profiles(currentprofile)\Field10
        playersetup($01)\Field7 = profiles(currentprofile)\Field11
        playersetup($01)\Field5 = profiles(currentprofile)\Field9
        playersetup($01)\Field6 = profiles(currentprofile)\Field8
        playersetup($01)\Field9 = profiles(currentprofile)\Field15
        playersetup($02)\Field0 = profiles(currentprofile)\Field12
        playersetup($02)\Field1 = profiles(currentprofile)\Field16
        playersetup($02)\Field3 = profiles(currentprofile)\Field13
        playersetup($02)\Field4 = profiles(currentprofile)\Field7
        playersetup($02)\Field8 = profiles(currentprofile)\Field10
        playersetup($02)\Field7 = profiles(currentprofile)\Field11
        playersetup($02)\Field5 = profiles(currentprofile)\Field9
        playersetup($02)\Field6 = profiles(currentprofile)\Field8
        playersetup($02)\Field9 = profiles(currentprofile)\Field15
    Else
        playersetup($01)\Field0 = $03
        playersetup($01)\Field1 = $02
        playersetup($01)\Field3 = $03
        playersetup($01)\Field4 = $01
        playersetup($01)\Field8 = $00
        playersetup($01)\Field7 = $00
        playersetup($01)\Field5 = $00
        playersetup($01)\Field6 = $00
        playersetup($01)\Field9 = $03
        playersetup($02)\Field0 = $03
        playersetup($02)\Field1 = $02
        playersetup($02)\Field3 = $03
        playersetup($02)\Field4 = $01
        playersetup($02)\Field8 = $00
        playersetup($02)\Field7 = $00
        playersetup($02)\Field5 = $00
        playersetup($02)\Field6 = $00
        playersetup($02)\Field9 = $03
    EndIf
    If (arg2 = $01) Then
        playersetup($01)\Field0 = $04
        playersetup($01)\Field1 = $270F
        playersetup($01)\Field3 = $00
        playersetup($01)\Field4 = $07
        playersetup($01)\Field8 = $03
        playersetup($01)\Field7 = $03
        playersetup($01)\Field5 = $03
        playersetup($01)\Field6 = $00
        playersetup($01)\Field9 = $01
    EndIf
    startmusic($01)
    If (specialmode = $02) Then
        startmusic($03)
    EndIf
    If (specialmode = $00) Then
        gotomenuno = $09
    EndIf
    If (specialmode = $01) Then
        gotomenuno = $0C
    EndIf
    If (specialmode = $02) Then
        gotomenuno = $02
    EndIf
    cleargame()
    recountcurrentenemiestype()
    currentevent = $01
    currentlevel = arg0
    leveltime = 0.0
    bossesdead = $00
    currentenemies = $00
    numplayers = arg1
    player($01)\Field11 = $00
    player($02)\Field11 = $00
    If (numplayers > $01) Then
        addplayer($01, -400.0, -245.0, playersetup($01)\Field2, playersetup($01)\Field1, playersetup($01)\Field0, playersetup($01)\Field3, $01)
        addplayer($02, -400.0, -255.0, playersetup($02)\Field2, playersetup($02)\Field1, playersetup($02)\Field0, playersetup($02)\Field3, $01)
    Else
        addplayer($01, -400.0, -250.0, $00, playersetup($01)\Field1, playersetup($01)\Field0, playersetup($01)\Field3, $01)
    EndIf
    gamemode = $01
    If (arg2 = $01) Then
        gamemode = $03
        getreadytime = 60.0
    Else
        getreadytime = 180.0
        fadengui(gui_getobjectentity(gui_bigtext), 0.0, 1.0, 0.015)
    EndIf
    forcep1crosshairx = 0.0
    forcep1crosshairy = 0.0
    forcefireon = $00
    currentbackground = levels(arg0, $01)\Field2
    If (arg2 = $01) Then
        gui_hidegroup($01)
    EndIf
    fadein()
    Return $00
End Function
