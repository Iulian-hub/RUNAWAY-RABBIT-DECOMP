Function doblackout%()
    Local local0%
    local0 = (fadingout - fadingin)
    If (local0 <> $00) Then
        whiteflashdirection = $00
        whiteflashalpha = 0.0
        showentity(gui_getobjectentity(gui_blackout))
        blackoutalpha = (((fl\Field1 * 0.02) * (Float local0)) + blackoutalpha)
        If (0.0 > blackoutalpha) Then
            fadingin = $00
            blackoutalpha = 0.0
        EndIf
        If (1.0 < blackoutalpha) Then
            fadingout = $00
            blackoutalpha = 1.0
        EndIf
        entityalpha(gui_getobjectentity(gui_blackout), blackoutalpha)
    EndIf
    If (0.0 = blackoutalpha) Then
        hideentity(gui_getobjectentity(gui_blackout))
    EndIf
    Return $00
End Function
