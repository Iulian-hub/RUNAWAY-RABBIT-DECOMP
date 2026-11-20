Function dongui%()
    Local local0%
    doscoreflyups()
    For local0 = $01 To $64 Step $01
        If (nguiquads(local0)\Field4 = $01) Then
            showentity(nguiquads(local0)\Field0)
            nguiquads(local0)\Field1 = ((nguiquads(local0)\Field2 * fl\Field1) + nguiquads(local0)\Field1)
            If ((((0.0 > nguiquads(local0)\Field2) And (nguiquads(local0)\Field3 > nguiquads(local0)\Field1)) Or ((0.0 < nguiquads(local0)\Field2) And (nguiquads(local0)\Field3 < nguiquads(local0)\Field1))) <> 0) Then
                nguiquads(local0)\Field4 = $00
                nguiquads(local0)\Field1 = nguiquads(local0)\Field3
                If (0.0 >= nguiquads(local0)\Field3) Then
                    hideentity(nguiquads(local0)\Field0)
                EndIf
            EndIf
            If (0.0 >= nguiquads(local0)\Field1) Then
                hideentity(nguiquads(local0)\Field0)
            EndIf
            entityalpha(nguiquads(local0)\Field0, nguiquads(local0)\Field1)
        EndIf
    Next
    Return $00
End Function
