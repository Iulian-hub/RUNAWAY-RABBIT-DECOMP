Function makeshotflash%(arg0#, arg1#, arg2%, arg3%, arg4#, arg5#)
    Local local0#
    Local local1#
    shotflashes(currentflash)\Field3 = 1.0
    shotflashes(currentflash)\Field2 = arg5
    entitytexture(shotflashes(currentflash)\Field0, arg2, $00, $00)
    positionentity(shotflashes(currentflash)\Field0, arg0, arg1, 0.0, $00)
    local0 = entityx(arg3, $01)
    local1 = entityy(arg3, $01)
    shotflashes(currentflash)\Field6 = arg3
    shotflashes(currentflash)\Field4 = (arg0 - local0)
    shotflashes(currentflash)\Field5 = (arg1 - local1)
    shotflashes(currentflash)\Field7 = (Int entityroll(shotflashes(currentflash)\Field6, $00))
    showentity(shotflashes(currentflash)\Field0)
    entityalpha(shotflashes(currentflash)\Field0, shotflashes(currentflash)\Field3)
    scalesprite(shotflashes(currentflash)\Field0, arg4, arg4)
    entityorder(shotflashes(currentflash)\Field0, $FFFFFFFF)
    currentflash = (currentflash + $01)
    If (currentflash > $14) Then
        currentflash = $01
    EndIf
    Return $00
End Function
