Function loadplayers%()
    Local local0%
    hamsterdeadtex = loadanimtexture("gfx/HamsterDead.png", $03, $80, $80, $00, $08)
    rotatetexture(hamsterdeadtex, 90.0)
    For local0 = $01 To $02 Step $01
        player(local0)\Field2 = loadsprite("gfx/crosshair.png", $33, $00)
        entityorder(player(local0)\Field2, ($FFFFFC18 - local0))
        scalesprite(player(local0)\Field2, 25.0, 25.0)
        hideentity(player(local0)\Field2)
        player(local0)\Field1 = makeactor(0.0, 0.0, 90.0, local0, $00)
        actors(player(local0)\Field1)\Field19 = $01
        doactors()
        player(local0)\Field18 = loadsprite("gfx/shield.png", $32, $00)
        scalesprite(player(local0)\Field18, 70.0, 50.0)
        entitycolor(player(local0)\Field18, 0.0, 200.0, 0.0)
        entityalpha(player(local0)\Field18, 1.0)
        player(local0)\Field19 = 1.0
        player(local0)\Field20 = $FFFFFFFF
        hideentity(player(local0)\Field18)
        actors(player(local0)\Field1)\Field20 = $01
        player(local0)\Field3 = $00
    Next
    Return $00
End Function
