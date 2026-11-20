Function doactors%()
    Local local0%
    Local local1%
    If (actorsloaded = $00) Then
        Return $00
    EndIf
    For local0 = $01 To $14 Step $01
        If (actors(local0)\Field41 = $01) Then
            showentity(actors(local0)\Field42)
            For local1 = $01 To $0A Step $01
                showentity(actors(local0)\Field70[local1])
            Next
            If (actors(local0)\Field19 = $00) Then
                doactoranimations(actors(local0), local0)
                doactorweapons(actors(local0), local0)
                doactorcollisions(actors(local0), local0)
                doactordeathcheck(actors(local0), local0)
                doactormovement(actors(local0), local0)
                doactorai(actors(local0), local0)
                updateactor(actors(local0), local0)
                If (actors(local0)\Field41 = $01) Then
                    doactorendai(actors(local0), local0)
                EndIf
            Else
                hideentity(actors(local0)\Field42)
                positionentity(actors(local0)\Field42, 999999.0, 999999.0, 0.0, $00)
                For local1 = $01 To $0A Step $01
                    hideentity(actors(local0)\Field70[local1])
                    positionentity(actors(local0)\Field70[local1], 999999.0, 999999.0, 0.0, $00)
                Next
            EndIf
        EndIf
        If (actors(local0)\Field41 = $00) Then
            hideentity(actors(local0)\Field42)
            positionentity(actors(local0)\Field42, 999999.0, 999999.0, 0.0, $00)
            For local1 = $01 To $0A Step $01
                hideentity(actors(local0)\Field70[local1])
                positionentity(actors(local0)\Field70[local1], 999999.0, 999999.0, 0.0, $00)
            Next
        EndIf
    Next
    Return $00
End Function
