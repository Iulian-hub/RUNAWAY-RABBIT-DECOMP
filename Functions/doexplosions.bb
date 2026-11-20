Function doexplosions%()
    Local local0%
    Local local1.sprite
    Local local2.sprite
    Local local3.sprite
    Local local4%
    Local local5%
    Local local6#
    doparticles()
    For local0 = $01 To $64 Step $01
        If (0.0 < explosions(local0)\Field1) Then
            local1 = (Object.sprite explosions(local0)\Field0)
            local2 = (Object.sprite explosions(local0)\Field5)
            If (explosions(local0)\Field7 = $01) Then
                local3 = (Object.sprite explosions(local0)\Field6)
            EndIf
            local4 = (Int ((Float (Int explosions(local0)\Field1)) Mod 4.0))
            If (local4 = $00) Then
                local4 = $04
            EndIf
            local5 = (Int ceil(((Float (Int explosions(local0)\Field1)) / 4.0)))
            spritetex(local1\Field18, local1, ((Float (local4 - $01)) * 0.25), ((Float (local5 - $01)) * 0.25), ((Float local4) * 0.25), ((Float local5) * 0.25))
            local6 = explosions(local0)\Field1
            local6 = ((0.4 * fl\Field1) + local6)
            explosions(local0)\Field1 = local6
            setsprite((Handle local2), local2\Field19, local2\Field0, local2\Field1, 0.0, 0.0, 0.0, 0.0, 0.0, (((explosions(local0)\Field2 * 2.0) / 16.0) * explosions(local0)\Field1), 0.0, (((explosions(local0)\Field2 * 2.0) / 16.0) * explosions(local0)\Field1), 0.0, 0.2, 0.0, local2\Field20)
            If (explosions(local0)\Field7 = $01) Then
                setsprite((Handle local3), local3\Field19, local3\Field0, local3\Field1, 0.0, 0.0, 0.0, 0.0, 0.0, ((explosions(local0)\Field2 / 16.0) * explosions(local0)\Field1), 0.0, ((explosions(local0)\Field2 / 16.0) * explosions(local0)\Field1), 0.0, 0.0, 0.0, local3\Field20)
            EndIf
            If (16.0 < explosions(local0)\Field1) Then
                local1\Field24 = $00
                local1\Field19 = 0.0
                local2\Field24 = $00
                local2\Field19 = 0.0
                If (explosions(local0)\Field7 = $01) Then
                    local3\Field24 = $00
                    local3\Field19 = 0.0
                EndIf
                explosions(local0)\Field1 = 0.0
                explosions(local0)\Field2 = 1.0
            EndIf
        EndIf
    Next
    Return $00
End Function
