Function init2din3d%(arg0%, arg1#, arg2%, arg3%)
    Local local0%
    Local local1#
    Local local2#
    Local local3.t2di3d
    If (arg2 <= $00) Then
        arg2 = graphicswidth()
    EndIf
    If (arg3 <= $00) Then
        arg3 = graphicsheight()
    EndIf
    local0 = createplane($01, $00)
    entitypickmode(local0, $02, $01)
    positionentity(local0, entityx(arg0, $01), entityy(arg0, $01), entityz(arg0, $01), $01)
    rotateentity(local0, entitypitch(arg0, $01), entityyaw(arg0, $01), entityroll(arg0, $01), $01)
    moveentity(local0, 0.0, 0.0, arg1)
    turnentity(local0, -90.0, 0.0, 0.0, $00)
    camerapick(arg0, 0.0, 0.0)
    While (((pickedentity() <> local0) And (1.0 < arg1)) <> 0)
        arg1 = (arg1 - 0.1)
        moveentity(local0, 0.0, 0.1, 0.0)
        camerapick(arg0, 0.0, 0.0)
    Wend
    If (((1.0 > arg1) Or (pickedentity() <> local0)) <> 0) Then
        runtimeerror("Unable to initialize 2d in 3d module")
    EndIf
    tformpoint(pickedx(), pickedy(), arg1, $00, arg0)
    local1 = (Abs tformedx())
    local2 = (Abs tformedy())
    local3 = (New t2di3d)
    local3\Field0 = local1
    local3\Field1 = local2
    local3\Field2 = arg1
    local3\Field3 = (Float arg2)
    local3\Field4 = (Float arg3)
    local3\Field5 = arg0
    freeentity(local0)
    Return $00
End Function
