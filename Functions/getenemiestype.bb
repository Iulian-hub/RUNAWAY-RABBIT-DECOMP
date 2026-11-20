Function getenemiestype%(arg0%)
    Local local0%
    For local0 = $01 To $05 Step $01
        If (levels(currentlevel, currentevent)\Field22[local0] = arg0) Then
            Return local0
        EndIf
    Next
    Return $00
    Return $00
End Function
