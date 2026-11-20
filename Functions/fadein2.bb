Function fadein2%(arg0%, arg1%, arg2%, arg3%, arg4%, arg5%)
    Local local0#
    fadein()
    local0 = 0.0
    Repeat
        local0 = (local0 + 1.0)
        doblackout()
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
            dohud()
            doshotflashes()
            doweaponshots()
            dosoundvolumes()
            doexplosions()
            dolevels()
            controlenemies()
            calckeyforce()
            movedemocrosshair()
        EndIf
        If (arg5 = $01) Then
            gui_hidegroup($01)
        EndIf
        renderworld(1.0)
        setspeedfactor()
        flip($01)
    Until (local0 > (60.0 / fl\Field1))
    If (arg4 = $00) Then
        entityalpha(gui_getobjectentity(gui_blackout), 0.0)
        renderworld(1.0)
        setspeedfactor()
        flip($01)
    EndIf
    Return $00
End Function
