Function loadactors%()
    Local local0%
    Local local1%
    actorsloaded = $01
    loadactorgraphics()
    For local0 = $01 To $14 Step $01
        If (local0 = $01) Then
            actors(local0)\Field42 = loadsprite(actorgraphicpath($01), $04, $00)
            actors(local0)\Field5 = loadsprite(actordecalspath($01), $33, $00)
        Else
            actors(local0)\Field42 = copyentity(actors($01)\Field42, $00)
            actors(local0)\Field5 = copyentity(actors($01)\Field5, $00)
        EndIf
        entityparent(actors(local0)\Field5, actors(local0)\Field42, $01)
        hideentity(actors(local0)\Field5)
        entityfx(actors(local0)\Field42, $21)
        entityorder(actors(local0)\Field42, (local0 * $03))
        hideentity(actors(local0)\Field42)
        For local1 = $01 To $0A Step $01
            If (((local0 = $01) And (local1 = $01)) <> 0) Then
                If (maingameview = $01) Then
                    actors(local0)\Field70[local1] = loadsprite(turretgraphicpath($01), $02, $00)
                Else
                    actors(local0)\Field70[local1] = loadsprite(turretgraphicpath($01), $02, actors(local0)\Field42)
                EndIf
                entityfx(actors(local0)\Field70[local1], $21)
            Else
                actors(local0)\Field70[local1] = copyentity(actors($01)\Field70[$01], $00)
            EndIf
            scalesprite(actors(local0)\Field70[local1], actors(local0)\Field66[local1], actors(local0)\Field66[local1])
            entityorder(actors(local0)\Field70[local1], ((local0 * $03) - $01))
            If (actors(local0)\Field64[local1] = $01) Then
                entityorder(actors(local0)\Field70[local1], ((local0 * $03) + $01))
            EndIf
            hideentity(actors(local0)\Field70[local1])
            positionentity(actors(local0)\Field70[local1], 999999.0, 999999.0, 0.0, $00)
        Next
    Next
    Return $00
End Function
