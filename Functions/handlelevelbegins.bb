Function handlelevelbegins%()
    If (0.0 < timeuntilbeginlevel) Then
        dohudvisibility()
        timeuntilbeginlevel = (timeuntilbeginlevel - fl\Field1)
        If (0.0 >= timeuntilbeginlevel) Then
            Select leveltobegintype
                Case $01
                    startlevel(leveltobegin, $01, $00)
                    gui_showgroup($01)
                Case $02
                    startmenubkg(leveltobegin, $01)
            End Select
        EndIf
    EndIf
    Return $00
End Function
