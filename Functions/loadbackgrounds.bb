Function loadbackgrounds%()
    Local local0%
    Local local1%
    backgroundactor = makeactor(0.0, -1275.0, 90.0, $28, $00)
    For local0 = $01 To $05 Step $01
        For local1 = $01 To $05 Step $01
            If (background(local0, local1)\Field0 = $01) Then
                If (local0 = $01) Then
                    background(local0, local1)\Field7 = createsprite($00)
                EndIf
                background(local0, local1)\Field8 = loadtexture(background(local0, local1)\Field13, background(local0, local1)\Field14)
                entityorder(background($01, local1)\Field7, ($2710 + local1))
                scaletexture(background(local0, local1)\Field8, background(local0, local1)\Field11, background(local0, local1)\Field12)
                background(local0, local1)\Field3 = rnd(0.0, 1.0)
                hideentity(background($01, local1)\Field7)
            EndIf
        Next
    Next
    Return $00
End Function
