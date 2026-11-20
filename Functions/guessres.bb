Function guessres%()
    loadresmode = $01
    If (totalvidmem() > $17D7840) Then
        loadresmode = $02
    EndIf
    If (totalvidmem() > $2FAF080) Then
        loadresmode = $05
    EndIf
    If (totalvidmem() > $5F5E100) Then
        loadresmode = $06
    EndIf
    Return $00
End Function
