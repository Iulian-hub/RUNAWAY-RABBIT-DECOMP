Function gui_setstring%(arg0.gui_object, arg1$, arg2%)
    Local local0.gui_text
    local0 = (Object.gui_text arg0\Field1)
    If (((local0\Field3 <> arg1) Or (arg2 = $01)) <> 0) Then
        local0\Field3 = arg1
        local0\Field0 = internal_createtextmesh(local0\Field2, arg1, local0\Field6, local0\Field7, (local0\Field8 / local0\Field9), local0\Field0, local0\Field10)
        gui_positionobject(arg0, local0\Field4, local0\Field5)
        gui_scaleobject(arg0, local0\Field9, local0\Field9)
        If (local0\Field1 <> $00) Then
            paintentity(local0\Field0, local0\Field1)
        EndIf
    EndIf
    Return $00
End Function
