Function stopweapons%()
    Local local0%
    Local local1.sprite
    For local0 = $01 To $64 Step $01
        If (weaponshots(local0)\Field71 = $01) Then
            local1 = (Object.sprite weaponshots(local0)\Field13)
            weaponshots(local0)\Field71 = $00
            local1\Field24 = $00
            local1\Field19 = 0.0
        EndIf
    Next
    Return $00
End Function
