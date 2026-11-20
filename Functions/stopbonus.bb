Function stopbonus%()
    Local local0%
    For local0 = $01 To $0A Step $01
        bonus(local0)\Field0 = $00
        hideentity(bonus(local0)\Field1)
    Next
    Return $00
End Function
