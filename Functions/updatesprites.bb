Function updatesprites%()
    Local local0.sprite
    Local local1%
    For local0 = Each sprite
        If (((0.0 < local0\Field19) And (local0\Field20 > $00)) <> 0) Then
            If (local0\Field23 = $00) Then
                local0\Field19 = (local0\Field19 - (1.0 * fl\Field1))
                local0\Field0 = ((local0\Field6 * fl\Field1) + local0\Field0)
                local0\Field1 = ((local0\Field7 * fl\Field1) + local0\Field1)
                local0\Field2 = ((local0\Field8 * fl\Field1) + local0\Field2)
                local0\Field3 = ((local0\Field9 * fl\Field1) + local0\Field3)
                local0\Field4 = ((local0\Field10 * fl\Field1) + local0\Field4)
                local0\Field5 = ((local0\Field11 * fl\Field1) + local0\Field5)
                local0\Field21 = ((local0\Field15 * fl\Field1) + local0\Field21)
            EndIf
            rotateentity(cameramain\Field5, 0.0, 0.0, ((- local0\Field21) + 180.0), $01)
            tformpoint(local0\Field0, local0\Field1, local0\Field2, $00, cameramain\Field5)
            local1 = (local1 + $01)
            If (0.0 < tformedz()) Then
                tformvector((- local0\Field3), (- local0\Field4), 0.0, cameramain\Field5, $00)
                vertexcoords(local0\Field17, local0\Field16[$00], (local0\Field0 + tformedx()), (local0\Field1 + tformedy()), (local0\Field2 + tformedz()))
                tformvector(local0\Field3, (- local0\Field4), 0.0, cameramain\Field5, $00)
                vertexcoords(local0\Field17, local0\Field16[$01], (local0\Field0 + tformedx()), (local0\Field1 + tformedy()), (local0\Field2 + tformedz()))
                tformvector((- local0\Field3), local0\Field4, 0.0, cameramain\Field5, $00)
                vertexcoords(local0\Field17, local0\Field16[$02], (local0\Field0 + tformedx()), (local0\Field1 + tformedy()), (local0\Field2 + tformedz()))
                local1 = (local1 + $01)
                tformvector(local0\Field3, local0\Field4, 0.0, cameramain\Field5, $00)
                vertexcoords(local0\Field17, local0\Field16[$03], (local0\Field0 + tformedx()), (local0\Field1 + tformedy()), (local0\Field2 + tformedz()))
                vertexcolor(local0\Field17, local0\Field16[$00], (Float local0\Field12), (Float local0\Field13), (Float local0\Field14), local0\Field5)
                vertexcolor(local0\Field17, local0\Field16[$01], (Float local0\Field12), (Float local0\Field13), (Float local0\Field14), local0\Field5)
                vertexcolor(local0\Field17, local0\Field16[$02], (Float local0\Field12), (Float local0\Field13), (Float local0\Field14), local0\Field5)
                vertexcolor(local0\Field17, local0\Field16[$03], (Float local0\Field12), (Float local0\Field13), (Float local0\Field14), local0\Field5)
            EndIf
        EndIf
        If (0.0 >= local0\Field19) Then
            local0\Field24 = $00
            hidesprite((Handle local0))
        EndIf
    Next
    Return $00
End Function
