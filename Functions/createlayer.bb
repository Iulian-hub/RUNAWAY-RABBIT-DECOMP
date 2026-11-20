Function createlayer%()
    Local local0.layer
    Local local1%
    local0 = (New layer)
    local0\Field0 = createmesh($00)
    local1 = createsurface(local0\Field0, $00)
    local0\Field2 = createbank($00)
    nameentity(local0\Field0, (Str (Handle local0)))
    entitycolor(local0\Field0, 255.0, 255.0, 255.0)
    entityfx(local0\Field0, $33)
    entityblend(local0\Field0, $01)
    Return local0\Field0
    Return $00
End Function
