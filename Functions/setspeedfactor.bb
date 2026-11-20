Function setspeedfactor%()
    fl\Field4 = millisecs()
    fl\Field1 = ((Float (fl\Field4 - fl\Field5)) / ((Float fl\Field3) / fl\Field0))
    If (3.0 < fl\Field1) Then
        fl\Field1 = 3.0
    EndIf
    fl\Field2 = (fl\Field0 / fl\Field1)
    fl\Field5 = fl\Field4
    Return $00
End Function
