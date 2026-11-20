Function dobackgrounds%()
    Local local0%
    actors(backgroundactor)\Field59 = 150000000.0
    For local0 = $01 To $05 Step $01
        If (oldbackground <> currentbackground) Then
            entitytexture(background($01, local0)\Field7, background(currentbackground, local0)\Field8, $00, $00)
        EndIf
        scalesprite(background($01, local0)\Field7, background(currentbackground, local0)\Field9, background(currentbackground, local0)\Field10)
        positionentity(background($01, local0)\Field7, background(currentbackground, local0)\Field1, background(currentbackground, local0)\Field2, 0.0, $00)
        background(currentbackground, local0)\Field3 = ((background(currentbackground, local0)\Field5 * fl\Field1) + background(currentbackground, local0)\Field3)
        background(currentbackground, local0)\Field4 = ((background(currentbackground, local0)\Field6 * fl\Field1) + background(currentbackground, local0)\Field4)
        positiontexture(background(currentbackground, local0)\Field8, background(currentbackground, local0)\Field3, background(currentbackground, local0)\Field4)
        showentity(background($01, local0)\Field7)
    Next
    oldbackground = currentbackground
    Return $00
End Function
