Function loadanimloops%()
    Local local0%
    For local0 = $01 To $28 Step $01
        If (animloopgraphics(local0) > "0") Then
            animloops(local0)\Field0 = loadanimtexture(animloopgraphics(local0), $02, animloops(local0)\Field8, animloops(local0)\Field7, $00, animloops(local0)\Field5)
            rotatetexture(animloops(local0)\Field0, (Float globalactorrotation))
            scaletexture(animloops(local0)\Field0, -1.0, 1.0)
            If (((maingameview = $01) And (animloops(local0)\Field6 = $01)) <> 0) Then
                animloops(local0)\Field1 = loadanimtexture(animloopgraphics(local0), $02, animloops(local0)\Field8, animloops(local0)\Field7, $00, animloops(local0)\Field5)
                rotatetexture(animloops(local0)\Field1, (Float globalactorrotation))
                scaletexture(animloops(local0)\Field1, -1.0, -1.0)
            EndIf
        EndIf
    Next
    Return $00
End Function
