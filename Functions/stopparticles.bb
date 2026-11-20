Function stopparticles%()
    Local local0.sprite
    For local0 = Each sprite
        local0\Field19 = 0.0
        local0\Field20 = $00
        hidesprite((Handle local0))
    Next
    Return $00
End Function
