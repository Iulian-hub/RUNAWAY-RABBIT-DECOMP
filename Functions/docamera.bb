Function docamera%()
    cameramain\Field1 = 0.0
    cameramain\Field2 = 0.0
    cameramain\Field3 = -1800.0
    positionentity(cameramain\Field0, cameramain\Field1, cameramain\Field2, cameramain\Field3, $00)
    cameramain\Field6 = (cameramain\Field6 - (0.2 * fl\Field1))
    If (4.5 > cameramain\Field6) Then
        cameramain\Field6 = 4.5
    EndIf
    camerazoom(cameramain\Field0, cameramain\Field6)
    docamerashake()
    Return $00
End Function
