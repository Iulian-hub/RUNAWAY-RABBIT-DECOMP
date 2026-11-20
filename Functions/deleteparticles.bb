Function deleteparticles%()
    Local local0.sprite
    For local0 = Each sprite
        Delete local0
    Next
    freeentity(particletypes($01)\Field0)
    freeentity(particletypes($14)\Field0)
    freetexture(particletypes($01)\Field1)
    freetexture(particletypes($14)\Field1)
    freeentity(particletypes($28)\Field0)
    freetexture(particletypes($28)\Field1)
    freeentity(particletypes($33)\Field0)
    freetexture(particletypes($33)\Field1)
    Return $00
End Function
