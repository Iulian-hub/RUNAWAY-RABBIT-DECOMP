Function makebackgroundenemy%(arg0#, arg1#, arg2%, arg3#, arg4#)
    Local local0%
    actortypes($27)\Field45 = arg3
    actortypes($27)\Field46 = arg3
    local0 = makeactor(arg0, arg1, 90.0, $27, $00)
    If (local0 = $00) Then
        Return $00
    EndIf
    actors(local0)\Field22 = arg2
    actors(local0)\Field47 = arg4
    actors(local0)\Field15 = $01
    actors(local0)\Field12 = $64
    actors(local0)\Field13 = $CD
    actors(local0)\Field14 = $CD
    entityorder(actors(local0)\Field42, $270E)
    actors(local0)\Field1 = $00
    Return $00
End Function
