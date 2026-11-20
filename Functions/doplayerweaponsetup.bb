Function doplayerweaponsetup%(arg0.playertype, arg1%)
    Local local0%
    Local local1%
    Local local2%
    Local local3%
    Local local4%
    Local local5.actortype
    Local local6%
    If (arg0\Field16 = $01) Then
        Return $00
    EndIf
    local0 = $00
    local1 = $00
    local2 = $00
    local3 = $00
    local4 = $00
    local0 = (playersetup(arg1)\Field4 + player(arg1)\Field28)
    local1 = (playersetup(arg1)\Field5 + player(arg1)\Field29)
    local3 = (playersetup(arg1)\Field6 + player(arg1)\Field30)
    local2 = (playersetup(arg1)\Field7 + player(arg1)\Field31)
    local4 = (playersetup(arg1)\Field8 + player(arg1)\Field32)
    local5 = actors(arg0\Field1)
    If (0.0 < arg0\Field13) Then
        If (((playersetup(arg1)\Field4 > $00) Or ((specialmode > $00) And (player(arg1)\Field28 > $00))) <> 0) Then
            local0 = $0A
        EndIf
        If (((playersetup(arg1)\Field5 > $00) Or ((specialmode > $00) And (player(arg1)\Field29 > $00))) <> 0) Then
            local1 = $05
        EndIf
        If (((playersetup(arg1)\Field6 > $00) Or ((specialmode > $00) And (player(arg1)\Field30 > $00))) <> 0) Then
            local3 = $05
        EndIf
        If (((playersetup(arg1)\Field7 > $00) Or ((specialmode > $00) And (player(arg1)\Field31 > $00))) <> 0) Then
            local2 = $05
        EndIf
        If (((playersetup(arg1)\Field8 > $00) Or ((specialmode > $00) And (player(arg1)\Field32 > $00))) <> 0) Then
            local4 = $05
        EndIf
    EndIf
    For local6 = $01 To $05 Step $01
        If (cannonnumber(local0) >= local6) Then
            local5\Field61[local6] = cannontype(local0, local6)
            local5\Field64[local6] = $01
            If (local6 = $01) Then
                local5\Field62[local6] = $01
            Else
                local5\Field62[local6] = $02
            EndIf
            local5\Field66[local6] = 28.0
            local5\Field67[local6] = $00
            local5\Field71[local6] = 4.5
            local5\Field72[local6] = 27.5
            local5\Field73[local6] = cannonangle(cannonnumber(local0), local6)
        Else
            local5\Field61[local6] = $00
        EndIf
    Next
    If (local1 > $00) Then
        local5\Field61[$06] = $04
        local5\Field64[$06] = $01
        local5\Field62[$06] = $02
        local5\Field66[$06] = 0.0
        local5\Field67[$06] = $00
        local5\Field71[$06] = 4.5
        local5\Field72[$06] = 27.5
        local5\Field73[$06] = 0.0
        Select local1
            Case $01
                local5\Field76[$06] = 1.0
            Case $02
                local5\Field76[$06] = 0.5
            Case $03
                local5\Field76[$06] = 0.33
            Case $04
                local5\Field76[$06] = 0.25
            Case $05
                local5\Field76[$06] = 0.2
        End Select
    Else
        local5\Field61[$06] = $00
    EndIf
    If (local2 > $00) Then
        local5\Field61[$07] = $06
        local5\Field64[$07] = $01
        local5\Field62[$07] = $02
        local5\Field66[$07] = 0.0
        local5\Field67[$07] = $00
        local5\Field71[$07] = 4.5
        local5\Field72[$07] = 27.5
        local5\Field73[$07] = 0.0
        Select local2
            Case $01
                local5\Field76[$07] = 1.0
            Case $02
                local5\Field76[$07] = 0.666
            Case $03
                local5\Field76[$07] = 0.55
            Case $04
                local5\Field76[$07] = 0.45
            Case $05
                local5\Field76[$07] = 0.333
        End Select
    Else
        local5\Field61[$07] = $00
    EndIf
    If (local4 > $00) Then
        showentity(local5\Field70[$08])
        local5\Field61[$08] = $05
        local5\Field64[$08] = $00
        local5\Field62[$08] = $03
        local5\Field66[$08] = 20.0
        local5\Field67[$08] = $01
        local5\Field68[$08] = $01
        local5\Field71[$08] = 4.5
        local5\Field72[$08] = 27.5
        local5\Field73[$08] = 0.0
        Select local4
            Case $01
                local5\Field76[$08] = 1.0
            Case $02
                local5\Field76[$08] = 0.5
            Case $03
                local5\Field76[$08] = 0.33
            Case $04
                local5\Field76[$08] = 0.25
            Case $05
                local5\Field76[$08] = 0.2
        End Select
        local5\Field69[$08] = 200.0
        local5\Field63[$08] = $01
    Else
        local5\Field61[$08] = $00
        hideentity(local5\Field70[$08])
        local5\Field62[$08] = $02
    EndIf
    entitytexture(local5\Field70[$08], turrettextures(local5\Field62[$08]), $00, $00)
    If (local3 > $00) Then
        local5\Field61[$09] = beamlasertype(local3)
        local5\Field64[$09] = $01
        local5\Field62[$09] = $02
        local5\Field66[$09] = 28.0
        local5\Field67[$09] = $00
        local5\Field71[$09] = 4.5
        local5\Field72[$09] = 27.5
        local5\Field73[$09] = 0.0
        If (local3 = $02) Then
            local5\Field76[$09] = 0.75
        Else
            local5\Field76[$09] = 1.0
        EndIf
        If (local3 = $04) Then
            local5\Field76[$09] = 0.85
        EndIf
    Else
        local5\Field61[$09] = $00
    EndIf
    For local6 = $01 To $05 Step $01
        local5\Field75[local6] = 0.0
    Next
    Return $00
End Function
