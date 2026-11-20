Function fadengui%(arg0%, arg1#, arg2#, arg3#)
    Local local0%
    Local local1%
    For local0 = $01 To $64 Step $01
        If (nguiquads(local0)\Field4 = $00) Then
            local1 = local0
        EndIf
    Next
    If (local1 = $00) Then
        local1 = $01
    EndIf
    For local0 = $01 To $64 Step $01
        If (nguiquads(local0)\Field0 = arg0) Then
            nguiquads(local0)\Field4 = $00
        EndIf
    Next
    nguiquads(local1)\Field4 = $01
    nguiquads(local1)\Field0 = arg0
    nguiquads(local1)\Field1 = arg1
    nguiquads(local1)\Field3 = arg2
    nguiquads(local1)\Field2 = arg3
    Return $00
End Function
