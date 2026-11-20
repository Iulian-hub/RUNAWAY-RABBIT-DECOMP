Function gameloop%()
    Repeat
        If (((currentmenu = $00) Or (menus(currentmenu)\Field11 = $00)) <> 0) Then
            dobackgrounds()
            domouse()
            dohud()
            movedemocrosshair()
            doactors()
            dobonus()
            dongui()
            dowhiteflash()
            doblackout()
            doanimloops()
            docamera()
            doshotflashes()
            doweaponshots()
            dosoundvolumes()
            doexplosions()
            dolevels()
            controlenemies()
            dobackgroundenemies()
            handlelevelbegins()
            dobosses()
            dodemotimestuff()
        EndIf
        calckeyforce()
        domenu()
        loopstuff()
        setspeedfactor()
        renderworld(1.0)
        flip($00)
    Forever
    Return $00
End Function
