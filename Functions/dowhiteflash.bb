Function dowhiteflash%()
    If (whiteflashdirection <> $00) Then
        showentity(gui_getobjectentity(gui_whiteout))
        whiteflashalpha = (((fl\Field1 * whiteflashspeed) * (Float whiteflashdirection)) + whiteflashalpha)
        If (whiteflashtargetalpha < whiteflashalpha) Then
            whiteflashdirection = $FFFFFFFF
            whiteflashalpha = whiteflashtargetalpha
        EndIf
        If (0.0 > whiteflashalpha) Then
            whiteflashdirection = $00
            whiteflashalpha = 0.0
        EndIf
        entityalpha(gui_getobjectentity(gui_whiteout), whiteflashalpha)
    EndIf
    If (0.0 = whiteflashalpha) Then
        hideentity(gui_getobjectentity(gui_whiteout))
    EndIf
    Return $00
End Function
