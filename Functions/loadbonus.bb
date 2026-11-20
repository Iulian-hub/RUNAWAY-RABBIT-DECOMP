Function loadbonus%()
    Local local0%
    bonustex = loadanimtexture("gfx/bonus.png", $02, $80, $80, $00, $0E)
    For local0 = $01 To $0A Step $01
        bonus(local0)\Field1 = createsprite($00)
        entityorder(bonus(local0)\Field1, ($FFFFFFEC - local0))
    Next
    Return $00
End Function
