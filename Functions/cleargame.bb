Function cleargame%()
    stopngui()
    fadengui(gui_getobjectentity(gui_bigtext), 1.0, 0.0, -10.0)
    entityalpha(gui_getobjectentity(gui_bigtext), 0.0)
    getreadytime = 0.0
    wintime = 0.0
    gameovertime = 0.0
    leveltime = 0.0
    stopbosses()
    stopplayers()
    stopbonus()
    hidebackgrounds()
    clearshotflashes()
    stopexplosions()
    stopweapons()
    stopactors()
    actors(player($01)\Field1)\Field41 = $01
    actors(player($02)\Field1)\Field41 = $01
    actors(player($01)\Field1)\Field19 = $01
    actors(player($02)\Field1)\Field19 = $01
    hideentity(player($01)\Field2)
    hideentity(player($01)\Field18)
    hideentity(player($02)\Field2)
    hideentity(player($02)\Field18)
    actors(backgroundactor)\Field41 = $01
    stopscoreflyups()
    stophudmessages()
    stopwhiteflash()
    stopparticles()
    deleteparticles()
    loadparticles()
    Return $00
End Function
