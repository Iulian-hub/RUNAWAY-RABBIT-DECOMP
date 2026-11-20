Function stopngui%()
    Local local0%
    For local0 = $01 To $64 Step $01
        nguiquads(local0)\Field4 = $00
    Next
    Return $00
End Function
