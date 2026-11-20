Function makeweaponshot%(arg0#, arg1#, arg2#, arg3%, arg4%, arg5%, arg6#)
    Local local0%
    Local local1%
    Local local2.sprite
    Local local3%
    If ((((((gamesizex < arg0) Or ((- gamesizex) > arg0)) Or (gamesizey < arg1)) Or ((- gamesizey) > arg1)) And (weaponshotkinds(arg3)\Field25 = $00)) <> 0) Then
        Return $00
    EndIf
    For local0 = $01 To $64 Step $01
        If (weaponshots(local0)\Field71 = $00) Then
            local1 = local0
        EndIf
    Next
    If (local1 = $00) Then
        local1 = $01
    EndIf
    weaponshots(local1)\Field15 = arg3
    weaponshots(local1)\Field46 = weaponshotkinds(arg3)\Field46
    arg2 = ((Float rand((- weaponshots(local1)\Field46), weaponshots(local1)\Field46)) + arg2)
    weaponshots(local1)\Field31 = weaponshotkinds(arg3)\Field31
    weaponshots(local1)\Field32 = weaponshotkinds(arg3)\Field32
    weaponshots(local1)\Field13 = makeparticle(arg0, arg1, 0.0, (- arg2), 1.0, weaponshotkinds(arg3)\Field31, weaponshotkinds(arg3)\Field32, getweaptexlayer(weaponshotkinds(arg3)\Field14), weaponshotkinds(arg3)\Field19, 0.0, 0.0, 0.0, 5000.0, $01)
    If (weaponshots(local1)\Field13 = $00) Then
        Return $00
    EndIf
    weaponshots(local1)\Field69 = 0.0
    weaponshots(local1)\Field19 = weaponshotkinds(arg3)\Field19
    weaponshots(local1)\Field12 = weaponshotkinds(arg3)\Field12
    weaponshotkinds(arg3)\Field11 = 0.0
    weaponshots(local1)\Field25 = weaponshotkinds(arg3)\Field25
    weaponshots(local1)\Field7 = weaponshotkinds(arg3)\Field7
    weaponshots(local1)\Field8 = arg6
    weaponshots(local1)\Field0 = weaponshotkinds(arg3)\Field0
    weaponshots(local1)\Field1 = weaponshotkinds(arg3)\Field1
    weaponshots(local1)\Field2 = weaponshotkinds(arg3)\Field2
    weaponshots(local1)\Field3 = weaponshotkinds(arg3)\Field3
    weaponshots(local1)\Field4 = weaponshotkinds(arg3)\Field4
    weaponshots(local1)\Field5 = weaponshotkinds(arg3)\Field5
    weaponshots(local1)\Field6 = weaponshotkinds(arg3)\Field6
    weaponshots(local1)\Field26 = arg4
    weaponshots(local1)\Field27 = arg5
    weaponshots(local1)\Field20 = weaponshotkinds(arg3)\Field20
    weaponshots(local1)\Field21 = weaponshotkinds(arg3)\Field21
    weaponshots(local1)\Field33 = weaponshotkinds(arg3)\Field33
    weaponshots(local1)\Field34 = weaponshotkinds(arg3)\Field34
    weaponshots(local1)\Field48 = weaponshotkinds(arg3)\Field48
    If (0.2 > weaponshots(local1)\Field48) Then
        weaponshots(local1)\Field48 = 1.0
    EndIf
    weaponshots(local1)\Field60 = weaponshotkinds(arg3)\Field60
    weaponshots(local1)\Field66 = weaponshotkinds(arg3)\Field66
    weaponshots(local1)\Field67 = weaponshotkinds(arg3)\Field66
    weaponshots(local1)\Field65 = weaponshotkinds(arg3)\Field65
    For local0 = $01 To $0A Step $01
        weaponshots(local1)\Field64[local0] = weaponshotkinds(arg3)\Field64[local0]
    Next
    weaponshots(local1)\Field38 = weaponshotkinds(arg3)\Field38
    weaponshots(local1)\Field40 = weaponshotkinds(arg3)\Field40
    weaponshots(local1)\Field39 = weaponshotkinds(arg3)\Field39
    weaponshots(local1)\Field36 = arg4
    weaponshots(local1)\Field42 = weaponshotkinds(arg3)\Field42
    weaponshots(local1)\Field43 = weaponshotkinds(arg3)\Field43
    If (weaponshots(local1)\Field43 = $00) Then
        weaponshots(local1)\Field43 = $C8
    EndIf
    weaponshots(local1)\Field44 = weaponshotkinds(arg3)\Field44
    weaponshots(local1)\Field45 = $00
    weaponshots(local1)\Field16 = weaponshotkinds(arg3)\Field16
    weaponshots(local1)\Field17 = weaponshotkinds(arg3)\Field17
    weaponshots(local1)\Field24 = weaponshotkinds(arg3)\Field24
    weaponshots(local1)\Field47 = weaponshotkinds(arg3)\Field47
    weaponshots(local1)\Field49 = weaponshotkinds(arg3)\Field49
    weaponshots(local1)\Field50 = weaponshotkinds(arg3)\Field50
    weaponshots(local1)\Field62 = weaponshotkinds(arg3)\Field62
    weaponshots(local1)\Field63 = weaponshotkinds(arg3)\Field63
    weaponshots(local1)\Field53 = 0.0
    weaponshots(local1)\Field54 = weaponshotkinds(arg3)\Field54
    weaponshots(local1)\Field55 = weaponshotkinds(arg3)\Field55
    weaponshots(local1)\Field52 = weaponshotkinds(arg3)\Field52
    weaponshots(local1)\Field56 = weaponshotkinds(arg3)\Field56
    weaponshots(local1)\Field57 = weaponshotkinds(arg3)\Field57
    weaponshots(local1)\Field35 = weaponshotkinds(arg3)\Field35
    weaponshots(local1)\Field68 = weaponshotkinds(arg3)\Field68
    weaponshots(local1)\Field61 = weaponshotkinds(arg3)\Field61
    weaponshots(local1)\Field37 = weaponshotkinds(arg3)\Field37
    weaponshots(local1)\Field51 = weaponshotkinds(arg3)\Field51
    weaponshots(local1)\Field14 = weaponshotkinds(arg3)\Field14
    weaponshots(local1)\Field71 = $01
    weaponshots(local1)\Field22 = arg0
    weaponshots(local1)\Field23 = arg1
    weaponshots(local1)\Field28 = arg6
    local2 = (Object.sprite weaponshots(local1)\Field13)
    local2\Field24 = $01
    local2\Field12 = weaponshotkinds(arg3)\Field4
    local2\Field13 = weaponshotkinds(arg3)\Field5
    local2\Field14 = weaponshotkinds(arg3)\Field6
    weaponshots(local1)\Field29 = local2\Field6
    weaponshots(local1)\Field30 = local2\Field7
    weaponshots(local1)\Field18 = (- arg2)
    If (weaponshots(local1)\Field36 > $00) Then
        local3 = actors(weaponshots(local1)\Field36)\Field42
        If (weaponshots(local1)\Field27 > $00) Then
            If (actors(weaponshots(local1)\Field36)\Field62[weaponshots(local1)\Field27] > $00) Then
                local3 = actors(weaponshots(local1)\Field36)\Field70[weaponshots(local1)\Field27]
            EndIf
        EndIf
        If (weaponshots(local1)\Field61 = $01) Then
            makeshotflash(arg0, arg1, redflash, local3, 30.0, 0.05)
        EndIf
        If (weaponshots(local1)\Field61 = $02) Then
            makeshotflash(arg0, arg1, greenflash, local3, 30.0, 0.05)
        EndIf
        If (weaponshots(local1)\Field61 = $03) Then
            makeshotflash(arg0, arg1, blueflash, local3, 30.0, 0.05)
        EndIf
        If (weaponshots(local1)\Field61 = $04) Then
            makeshotflash(arg0, arg1, yellowflash, local3, 30.0, 0.05)
        EndIf
        If (weaponshots(local1)\Field61 = $05) Then
            makeshotflash(arg0, arg1, purpleflash, local3, 30.0, 0.05)
        EndIf
    EndIf
    If (((weaponshotkinds(arg3)\Field41 > $00) And (soundon = $01)) <> 0) Then
        playsoundoveride(sndweapon(weaponshotkinds(arg3)\Field41))
    EndIf
    Return $00
End Function
