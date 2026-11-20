Function loadexplosions%()
    Local local0%
    loadparticles()
    loadshotflashes()
    For local0 = $01 To $64 Step $01
        explosions(local0)\Field1 = 0.0
        explosions(local0)\Field3 = $01
        explosions(local0)\Field2 = 1.0
        If (local0 = $01) Then
        EndIf
    Next
    Return $00
End Function
