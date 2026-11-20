Function gui_showgroup%(arg0%)
    Local local0.gui_object
    For local0 = Each gui_object
        If (((local0\Field6 = arg0) Or (arg0 = $FFFFFFFF)) <> 0) Then
            showentity(gui_getobjectentity(local0))
        EndIf
    Next
    Return $00
End Function
