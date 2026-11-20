Function docamerashake%()
    Local local0%
    cameramain\Field7 = (cameramain\Field7 - (1.0 * fl\Field1))
    If (0.0 > cameramain\Field7) Then
        cameramain\Field7 = 0.0
    EndIf
    If (6.0 < cameramain\Field7) Then
        cameramain\Field7 = 6.0
    EndIf
    If (1.0 = cameramain\Field8) Then
        cameramain\Field8 = -1.0
    Else
        cameramain\Field8 = 1.0
    EndIf
    local0 = (Int rnd(-1.0, 1.0))
    positionentity(cameramain\Field0, ((cameramain\Field7 * cameramain\Field8) + cameramain\Field1), ((cameramain\Field7 * (Float local0)) + cameramain\Field2), cameramain\Field3, $00)
    Return $00
End Function
