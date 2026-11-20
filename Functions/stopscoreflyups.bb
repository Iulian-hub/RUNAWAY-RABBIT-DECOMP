Function stopscoreflyups%()
    Local local0%
    For local0 = $01 To $0A Step $01
        If (scoreflyups(local0)\Field5 = $01) Then
            gui_freeobject(scoreflyupobjects(local0))
        EndIf
        scoreflyups(local0)\Field5 = $00
    Next
    Return $00
End Function
