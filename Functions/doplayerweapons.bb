Function doplayerweapons%(arg0.playertype)
    Local local0.actortype
    Local local1#
    Local local2%
    local0 = actors(arg0\Field1)
    local1 = (- (getangle((local0\Field48 + local0\Field71[$01]), (local0\Field49 + local0\Field72[$01]), (entityx(arg0\Field2, $00) + 12.5), entityy(arg0\Field2, $00)) - 90.0))
    local1 = wrapangle(local1)
    For local2 = $01 To $0A Step $01
        If (local0\Field67[local2] = $00) Then
            local0\Field65[local2] = local1
        EndIf
        If (((80.0 < local0\Field65[local2]) And (181.0 > local0\Field65[local2])) <> 0) Then
            local0\Field65[local2] = 80.0
        EndIf
        If (((280.0 > local0\Field65[local2]) And (180.0 < local0\Field65[local2])) <> 0) Then
            local0\Field65[local2] = 280.0
        EndIf
    Next
    If (((currentenemies = $00) And (bossbarnum = $00)) <> 0) Then
        local0\Field68[$08] = $00
        local0\Field75[$08] = 0.0
    Else
        local0\Field68[$08] = $01
    EndIf
    If (arg0\Field3 = $00) Then
        If (((keydown(spacekey) Or keydown(zkey)) Or mousedown($01)) <> 0) Then
            local0\Field32 = $01
        Else
            local0\Field32 = $00
        EndIf
        If (((((((((mousedown($02) Or keydown(xkey)) Or keydown(enterkey)) And (arg0\Field21 > $00)) And (0.0 = bombtime)) And (0.0 = gameovertime)) And (0.0 = wintime)) And (0.0 = getreadytime)) And (0.0 = arg0\Field33)) <> 0) Then
            If (canfire = $01) Then
                firebomb(arg0)
            EndIf
        EndIf
    EndIf
    If (arg0\Field3 = $01) Then
        If (mousedown($01) <> 0) Then
            local0\Field32 = $01
        Else
            local0\Field32 = $00
        EndIf
        If (((((((mousedown($02) And (arg0\Field21 > $00)) And (0.0 = bombtime)) And (0.0 = gameovertime)) And (0.0 = wintime)) And (0.0 = getreadytime)) And (0.0 = arg0\Field33)) <> 0) Then
            If (canfire = $01) Then
                firebomb(arg0)
            EndIf
        EndIf
    EndIf
    If (arg0\Field3 = $02) Then
        If (((keydown(spacekey) Or keydown(zkey)) Or joydown($01, $00)) <> 0) Then
            local0\Field32 = $01
        Else
            local0\Field32 = $00
        EndIf
        If (((((((((keydown(xkey) Or keydown(enterkey)) Or joydown($02, $00)) And (arg0\Field21 > $00)) And (0.0 = bombtime)) And (0.0 = arg0\Field33)) And (0.0 = gameovertime)) And (0.0 = wintime)) And (0.0 = getreadytime)) <> 0) Then
            If (canfire = $01) Then
                firebomb(arg0)
            EndIf
        EndIf
    EndIf
    If (canfire = $00) Then
        local0\Field32 = $00
    EndIf
    If ((((((((((joydown($01, $00) = $00) And (joydown($02, $00) = $00)) And (keydown(xkey) = $00)) And (keydown(zkey) = $00)) And (keydown(zkey) = $00)) And (keydown(enterkey) = $00)) And (keydown(spacekey) = $00)) And (mousedown($01) = $00)) And (mousedown($02) = $00)) <> 0) Then
        canfire = $01
    Else
        mouseisdown = $01
    EndIf
    If (0.0 < arg0\Field27) Then
        arg0\Field27 = (arg0\Field27 - fl\Field1)
        If (0.0 > arg0\Field27) Then
            arg0\Field27 = 0.0
        EndIf
        local0\Field75[$09] = 0.0
    EndIf
    If (forcefireon = $01) Then
        local0\Field32 = $01
    EndIf
    weaponshotkinds($07)\Field21 = 2.0
    weaponshotkinds($08)\Field21 = 3.0
    weaponshotkinds($09)\Field21 = 4.0
    If (currentlevel < $03) Then
        weaponshotkinds($07)\Field21 = 1.0
    EndIf
    If (local0\Field32 = $01) Then
        If (local0\Field75[$09] < (local0\Field74[$09] * local0\Field76[$09])) Then
            local0\Field75[$09] = 0.0
        Else
            If (((0.0 = gameovertime) And (0.0 = wintime)) <> 0) Then
                whiteflash(0.3, 0.01, 2.0, $01)
            EndIf
            arg0\Field27 = 40.0
        EndIf
    EndIf
    If (((local0\Field75[$01] >= (local0\Field74[$01] - fl\Field1)) And (local0\Field32 = $01)) <> 0) Then
        local0\Field62[$01] = (local0\Field62[$01] + $01)
        If (local0\Field62[$01] = $02) Then
            local0\Field62[$01] = $09
        EndIf
        If (local0\Field62[$01] > $09) Then
            local0\Field62[$01] = $01
        EndIf
        entitytexture(local0\Field70[$01], turrettextures(local0\Field62[$01]), $00, $00)
    EndIf
    If (((0.0 <> gameovertime) Or (0.0 <> wintime)) <> 0) Then
        local0\Field32 = $00
    EndIf
    Return $00
End Function
