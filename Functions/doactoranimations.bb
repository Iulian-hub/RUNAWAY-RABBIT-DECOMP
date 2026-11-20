Function doactoranimations%(arg0.actortype, arg1%)
    If (arg0\Field22 > $00) Then
        If (((arg0\Field24 = $01) And (arg0\Field23 <> $00)) <> 0) Then
            arg0\Field26 = (arg0\Field26 + fl\Field1)
        EndIf
        If (animloops(arg0\Field22)\Field4 < arg0\Field26) Then
            arg0\Field26 = 0.0
            arg0\Field25 = (arg0\Field25 + $01)
            If (arg0\Field25 > animloops(arg0\Field22)\Field5) Then
                arg0\Field25 = $01
                If (arg0\Field23 > $00) Then
                    arg0\Field23 = (arg0\Field23 - $01)
                EndIf
            EndIf
        EndIf
    EndIf
    Return $00
End Function
