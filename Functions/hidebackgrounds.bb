Function hidebackgrounds%()
    Local local0%
    Local local1%
    local0 = $01
    For local1 = $01 To $05 Step $01
        If (background(local0, local1)\Field0 = $01) Then
            hideentity(background(local0, local1)\Field7)
        EndIf
    Next
    Return $00
End Function
