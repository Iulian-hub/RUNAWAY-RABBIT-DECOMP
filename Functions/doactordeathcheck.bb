Function doactordeathcheck%(arg0.actortype, arg1%)
    doactordeath(arg0, arg1)
    If (arg0\Field15 = $00) Then
        If (((arg0\Field59 < ((arg0\Field58 / 100.0) * 35.0)) And (rand($01, (Int (5.0 / fl\Field1))) = $02)) <> 0) Then
            makeparticle(((arg0\Field48 + arg0\Field36) + (Float rand($FFFFFFF6, $0A))), ((arg0\Field49 + arg0\Field37) + (Float rand($FFFFFFF6, $0A))), 0.0, 0.0, 1.0, ((arg0\Field51 / 3.0) + (Float rand($FFFFFFFB, $05))), ((arg0\Field51 / 3.0) + (Float rand($FFFFFFFB, $05))), $20, 0.0, -0.03, 0.2, 0.2, 60.0, $00)
        EndIf
        If (((0.0 >= arg0\Field59) And (0.0 = arg0\Field34)) <> 0) Then
            arg0\Field34 = 1.0
        EndIf
    EndIf
    Return $00
End Function
