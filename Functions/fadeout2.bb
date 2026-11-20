Function fadeout2%(arg0%, arg1%, arg2%, arg3%, arg4%)
    Local local0#
    fadeout()
    local0 = 0.0
    Repeat
        local0 = (local0 + 1.0)
        If (arg3 = $01) Then
            dohud()
        EndIf
        If (arg1 = $00) Then
            domenu()
        EndIf
        If (arg2 = $00) Then
            dobackgrounds()
        EndIf
        If (arg0 = $01) Then
            hideentity(gui_getobjectentity(menumouse))
        EndIf
        If (arg3 = $01) Then
            domouse()
            doactors()
            dobonus()
            dongui()
            dowhiteflash()
            doanimloops()
            docamera()
            doshotflashes()
            doweaponshots()
            dosoundvolumes()
            doexplosions()
            dolevels()
            controlenemies()
            calckeyforce()
            movedemocrosshair()
        EndIf
        If (arg4 = $01) Then
            gui_hidegroup($01)
        EndIf
        doblackout()
        renderworld(1.0)
        setspeedfactor()
        flip($01)
    Until (local0 > (60.0 / fl\Field1))
    entityalpha(gui_getobjectentity(gui_blackout), 1.0)
    renderworld(1.0)
    setspeedfactor()
    flip($01)
    Return $00
End Function
