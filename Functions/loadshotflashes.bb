Function loadshotflashes%()
    Local local0%
    blueflash = loadtexture(blueflashtexture, $01)
    redflash = loadtexture(redflashtexture, $01)
    greenflash = loadtexture(greenflashtexture, $01)
    yellowflash = loadtexture(yellowflashtexture, $01)
    purpleflash = loadtexture(purpleflashtexture, $01)
    For local0 = $01 To $14 Step $01
        If (local0 = $01) Then
            shotflashes(local0)\Field0 = loadsprite(blueflashtexture, $01, $00)
        Else
            shotflashes(local0)\Field0 = copyentity(shotflashes($01)\Field0, $00)
        EndIf
        entityorder(shotflashes(local0)\Field0, $FFFFFFFE)
        hideentity(shotflashes(local0)\Field0)
    Next
    Return $00
End Function
