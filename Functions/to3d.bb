Function to3d%(arg0#, arg1#, arg2%, arg3%)
    Local local0.t2di3d
    local0 = (First t2di3d)
    arg0 = (((arg0 / local0\Field3) * (local0\Field0 * 2.0)) + (- local0\Field0))
    arg1 = (local0\Field1 - ((arg1 / local0\Field4) * (local0\Field1 * 2.0)))
    If (arg2 = $00) Then
        tformpoint(arg0, arg1, local0\Field2, local0\Field5, arg3)
        x3d = tformedx()
        y3d = tformedy()
        z3d = tformedz()
        Return $00
    EndIf
    x3d = arg0
    y3d = arg1
    z3d = local0\Field2
    Return $00
    Return $00
End Function
