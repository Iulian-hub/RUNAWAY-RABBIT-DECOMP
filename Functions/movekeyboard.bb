Function movekeyboard%(arg0#, arg1#)
    keyboardx = arg0
    keyboardy = arg1
    If ((Float graphicswidth()) < keyboardx) Then
        keyboardx = (Float graphicswidth())
    EndIf
    If (0.0 > keyboardx) Then
        keyboardx = 0.0
    EndIf
    If ((Float graphicsheight()) < keyboardy) Then
        keyboardy = (Float graphicsheight())
    EndIf
    If (0.0 > keyboardy) Then
        keyboardy = 0.0
    EndIf
    Return $00
End Function
