Function startmenubkg%(arg0%, arg1%)
    startmusic($02)
    cleargame()
    currentevent = $01
    currentlevel = arg0
    leveltime = 0.0
    currentenemies = $00
    gamemode = $02
    currentbackground = levels(arg0, $01)\Field2
    If (arg1 <> 0) Then
        fadein()
    EndIf
    Return $00
End Function
