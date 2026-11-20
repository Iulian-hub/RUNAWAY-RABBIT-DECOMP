Function showfullscreen%(arg0$)
    Local local0%
    Local local1%
    fullscreentexture = loadanimtexture(arg0, $30, $100, $100, $00, $10)
    For local0 = $01 To $04 Step $01
        For local1 = $01 To $04 Step $01
            entitytexture(gui_getobjectentity(fullscreentiles(local1, local0)), fullscreentexture, (((local0 - $01) Shl $02) + (local1 - $01)), $00)
            showentity(gui_getobjectentity(fullscreentiles(local1, local0)))
        Next
    Next
    Return $00
End Function
