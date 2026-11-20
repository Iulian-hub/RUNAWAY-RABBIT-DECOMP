Function dodemotimestuff%()
    Local local0#
    Local local1%
    Local local2%
    If ((((demoversionon = $01) And (currentmenu = $00)) And (gamemode <> $03)) <> 0) Then
        demotimeleft = (demotimeleft - fl\Field1)
        local0 = floor((demotimeleft / 60.0))
        local1 = (Int floor((local0 / 60.0)))
        local2 = (Int (local0 - (Float (local1 * $3C))))
        If (local2 = $3C) Then
            local2 = $3B
        EndIf
        If (local2 > $09) Then
            gui_setstring(gui_demotimertext, ((("DEMO TIME: " + (Str local1)) + ":") + (Str local2)), $00)
        ElseIf (local2 > $00) Then
            gui_setstring(gui_demotimertext, ((("DEMO TIME: " + (Str local1)) + ":0") + (Str local2)), $00)
        Else
            gui_setstring(gui_demotimertext, (("DEMO TIME: " + (Str local1)) + ":00"), $00)
            savedemotime()
        EndIf
        If (0.0 > demotimeleft) Then
            gui_setstring(gui_demotimertext, "", $00)
            If (((((0.0 = getreadytime) And (willwin = $00)) And (0.0 = wintime)) And (0.0 = gameovertime)) <> 0) Then
                demotimeleft = 3600.0
                savedemotime()
                buymenutext = "Your demo time has run out. Get the full version now and play for as long as you want!"
                changemenu($1D, 0.0, 0.0)
            EndIf
        EndIf
    Else
        gui_setstring(gui_demotimertext, "", $00)
    EndIf
    Return $00
End Function
