Function stopactors%()
    Local local0%
    Local local1%
    For local0 = $01 To $14 Step $01
        actors(local0)\Field41 = $00
        hideentity(actors(local0)\Field42)
        positionentity(actors(local0)\Field42, 999999.0, 999999.0, 0.0, $00)
        If (maingameview = $01) Then
            For local1 = $01 To $0A Step $01
                hideentity(actors(local0)\Field70[local1])
                positionentity(actors(local0)\Field70[local1], 999999.0, 999999.0, 0.0, $00)
            Next
        EndIf
    Next
    Return $00
End Function
