Function calckeyforce%()
    Local local0%
    Local local1%
    local0 = (keydown(rightkey) - keydown(leftkey))
    local1 = (keydown(downkey) - keydown(upkey))
    If (nokeys = $00) Then
        local0 = (local0 + keydown(dkey))
        local1 = (local1 - keydown(wkey))
        local0 = (local0 - keydown(akey))
        local1 = (local1 + keydown(skey))
    EndIf
    If (((local0 = $00) And (local1 = $00)) <> 0) Then
        local0 = joyxdir($00)
        local1 = (- joyydir($00))
    EndIf
    If (local0 > $01) Then
        local0 = $01
    EndIf
    If (local0 < $FFFFFFFF) Then
        local0 = $FFFFFFFF
    EndIf
    If (local1 > $01) Then
        local1 = $01
    EndIf
    If (local1 < $FFFFFFFF) Then
        local1 = $FFFFFFFF
    EndIf
    If (((local0 > $00) And (0.0 > keyforcex)) <> 0) Then
        keyforcex = 0.0
    EndIf
    If (((local0 < $00) And (0.0 < keyforcex)) <> 0) Then
        keyforcex = 0.0
    EndIf
    If (local0 = $00) Then
        keyforcex = 0.0
    EndIf
    If (((local1 > $00) And (0.0 > keyforcey)) <> 0) Then
        keyforcey = 0.0
    EndIf
    If (((local1 < $00) And (0.0 < keyforcey)) <> 0) Then
        keyforcey = 0.0
    EndIf
    If (local1 = $00) Then
        keyforcey = 0.0
    EndIf
    If (((0.0 = keyforcex) And (local0 <> $00)) <> 0) Then
        keyforcex = ((Float local0) * 0.5)
    EndIf
    If (((0.0 = keyforcey) And (local1 <> $00)) <> 0) Then
        keyforcey = ((Float local1) * 0.5)
    EndIf
    keyforcex = ((((Float local0) * fl\Field1) * 0.05) + keyforcex)
    keyforcey = ((((Float local1) * fl\Field1) * 0.05) + keyforcey)
    If (2.0 < keyforcex) Then
        keyforcex = 2.0
    EndIf
    If (-2.0 > keyforcex) Then
        keyforcex = -2.0
    EndIf
    If (2.0 < keyforcey) Then
        keyforcey = 2.0
    EndIf
    If (-2.0 > keyforcey) Then
        keyforcey = -2.0
    EndIf
    Return $00
End Function
