Function movedemocrosshair%()
    Local local0%
    If (((gamemode <> $03) And (currentprofile > $00)) <> 0) Then
        soundon = profiles(currentprofile)\Field2
        Return $00
    EndIf
    soundon = $00
    gui_hidegroup($01)
    forcep1crosshairy = 150.0
    forcep1crosshairx = (((fl\Field1 * 10.0) * (Float crosshairtargetdirection)) + forcep1crosshairx)
    local0 = (Int (300.0 / fl\Field1))
    If (local0 < $02) Then
        local0 = $02
    EndIf
    If (rand($01, local0) = $02) Then
        crosshairtargetdirection = (- crosshairtargetdirection)
    EndIf
    If (350.0 < forcep1crosshairx) Then
        crosshairtargetdirection = $FFFFFFFF
    EndIf
    If (-350.0 > forcep1crosshairx) Then
        crosshairtargetdirection = $01
    EndIf
    forcefireon = $01
    Return $00
End Function
