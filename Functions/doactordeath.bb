Function doactordeath%(arg0.actortype, arg1%)
    Local local0%
    Local local1%
    If (0.0 < arg0\Field34) Then
        If (rand($01, (Int (500.0 / fl\Field1))) < $C8) Then
            makeexplosion(((Float rand((Int (- arg0\Field53)), (Int arg0\Field53))) + arg0\Field48), ((Float rand((Int (- arg0\Field54)), (Int arg0\Field54))) + arg0\Field49), (((arg0\Field53 + arg0\Field54) / 2.0) / 2.0), 1.0)
        EndIf
        arg0\Field34 = (arg0\Field34 + fl\Field1)
        If (arg0\Field35 < arg0\Field34) Then
            arg0\Field41 = $00
            If (arg0\Field2 > $00) Then
                actors(arg0\Field2)\Field41 = $00
            EndIf
            makeexplosion(arg0\Field48, arg0\Field49, (((arg0\Field53 + arg0\Field54) / 2.0) * 2.0), 1.0)
            For local0 = $01 To $0A Step $01
                If (arg0\Field18[local0] > $00) Then
                    makeweaponshot(arg0\Field48, arg0\Field49, arg0\Field17[local0], arg0\Field18[local0], arg1, $00, 0.0)
                EndIf
            Next
            currentfactioninplay(arg0\Field30) = (currentfactioninplay(arg0\Field30) - $01)
            totalfactiondead(arg0\Field30) = (totalfactiondead(arg0\Field30) + $01)
            If (maingameview = $01) Then
                For local1 = $01 To $0A Step $01
                    hideentity(arg0\Field70[local1])
                    positionentity(arg0\Field70[local1], 999999.0, 999999.0, 0.0, $00)
                Next
            EndIf
            actordeath(arg0, arg1)
        EndIf
    EndIf
    Return $00
End Function
