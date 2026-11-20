Function loadfullscreen%()
    Local local0%
    Local local1%
    fullscreentexture = loadanimtexture("gfx/blank.bmp", $01, $01, $01, $00, $10)
    For local0 = $01 To $04 Step $01
        For local1 = $01 To $04 Step $01
            fullscreentiles(local1, local0) = gui_createsprite((Float ((local1 - $01) * $A0)), (Float ((local0 - $01) * $78)), 160.0, 120.0, 0.0, 0.0, 1.0, 1.0, $00)
            entityfx(gui_getobjectentity(fullscreentiles(local1, local0)), $21)
            hideentity(gui_getobjectentity(fullscreentiles(local1, local0)))
        Next
    Next
    Return $00
End Function
