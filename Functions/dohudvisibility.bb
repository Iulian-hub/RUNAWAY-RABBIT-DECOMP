Function dohudvisibility%()
    If (0.0 < timetoshow) Then
        timetoshow = (timetoshow - fl\Field1)
        If (0.0 >= timetoshow) Then
            gui_showgroup($01)
        EndIf
    EndIf
    If (0.0 < timetohide) Then
        timetohide = (timetohide - fl\Field1)
        If (0.0 >= timetohide) Then
            gui_hidegroup($01)
        EndIf
    EndIf
    Return $00
End Function
