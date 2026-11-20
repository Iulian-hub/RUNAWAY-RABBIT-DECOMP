Function stopexplosions%()
    Local local0%
    Local local1.sprite
    Local local2.sprite
    Local local3.sprite
    stopparticles()
    For local0 = $01 To $64 Step $01
        If (0.0 < explosions(local0)\Field1) Then
            local1 = (Object.sprite explosions(local0)\Field0)
            local2 = (Object.sprite explosions(local0)\Field5)
            If (explosions(local0)\Field7 = $01) Then
                local3 = (Object.sprite explosions(local0)\Field6)
            EndIf
            local1\Field24 = $00
            local1\Field19 = 0.0
            local2\Field24 = $00
            local2\Field19 = 0.0
            If (explosions(local0)\Field7 = $01) Then
                explosions(local0)\Field7 = $00
                local3\Field24 = $00
                local3\Field19 = 0.0
            EndIf
            explosions(local0)\Field1 = 0.0
            explosions(local0)\Field2 = 1.0
        EndIf
    Next
    Return $00
End Function
