Function hidefullscreen%()
    Local local0%
    Local local1%
    For local0 = $01 To $04 Step $01
        For local1 = $01 To $04 Step $01
            hideentity(gui_getobjectentity(fullscreentiles(local0, local1)))
        Next
    Next
    Return $00
End Function
