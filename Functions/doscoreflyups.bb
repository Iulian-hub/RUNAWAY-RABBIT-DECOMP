Function doscoreflyups%()
    Local local0%
    Local local2#
    Local local3#
    For local0 = $01 To $0A Step $01
        If (scoreflyups(local0)\Field5 = $01) Then
            If (0.0 < scoreflyups(local0)\Field7) Then
                scoreflyups(local0)\Field7 = (scoreflyups(local0)\Field7 - fl\Field1)
            EndIf
            If (((0.0 >= scoreflyups(local0)\Field7) And (scoreflyups(local0)\Field8 <> $00)) <> 0) Then
                If (scoreflyups(local0)\Field8 = $01) Then
                    scoreflyups(local0)\Field7 = (scoreflyups(local0)\Field6 / 2.0)
                EndIf
                If (scoreflyups(local0)\Field8 = $FFFFFFFF) Then
                    scoreflyups(local0)\Field7 = scoreflyups(local0)\Field6
                EndIf
                scoreflyups(local0)\Field8 = (scoreflyups(local0)\Field8 * $FFFFFFFF)
            EndIf
            If (scoreflyups(local0)\Field12 = $00) Then
                scoreflyups(local0)\Field1 = (((scoreflyups(local0)\Field13 / 10.0) * fl\Field1) + scoreflyups(local0)\Field1)
            Else
                scoreflyups(local0)\Field2 = (scoreflyups(local0)\Field2 - (scoreflyups(local0)\Field13 * fl\Field1))
            EndIf
            scoreflyups(local0)\Field3 = (scoreflyups(local0)\Field3 - (scoreflyups(local0)\Field4 * fl\Field1))
            entityalpha(gui_getobjectentity(scoreflyupobjects(local0)), scoreflyups(local0)\Field3)
            Select scoreflyups(local0)\Field8
                Case $01
                    entitycolor(gui_getobjectentity(scoreflyupobjects(local0)), (Float scoreflyups(local0)\Field9), (Float scoreflyups(local0)\Field10), (Float scoreflyups(local0)\Field11))
                Case $FFFFFFFF
                    entitycolor(gui_getobjectentity(scoreflyupobjects(local0)), 255.0, 255.0, 255.0)
            End Select
            cameraproject(cameramain\Field0, scoreflyups(local0)\Field0, scoreflyups(local0)\Field1, 0.0)
            local2 = projectedx()
            local3 = projectedy()
            gui_positionobject2(scoreflyupobjects(local0), gui_projectedx(local2), gui_projectedy(local3), scoreflyups(local0)\Field2)
            If (0.0 >= scoreflyups(local0)\Field3) Then
                gui_freeobject(scoreflyupobjects(local0))
                scoreflyups(local0)\Field5 = $00
            EndIf
        EndIf
    Next
    Return $00
End Function
