Function hidesprite%(arg0%)
    Local local0.sprite
    Local local1%
    local0 = (Object.sprite arg0)
    If (local0\Field20 = $00) Then
        Return $00
    EndIf
    For local1 = $00 To $03 Step $01
        vertexcoords(local0\Field17, local0\Field16[local1], ss_large, ss_large, ss_large)
        vertexcolor(local0\Field17, local0\Field16[local1], 0.0, 0.0, 0.0, 0.0)
    Next
    local0\Field20 = $00
    Return $00
End Function
