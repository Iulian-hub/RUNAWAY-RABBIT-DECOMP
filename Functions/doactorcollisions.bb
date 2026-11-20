Function doactorcollisions%(arg0.actortype, arg1%)
    Local local0%
    If (180.0 < arg0\Field77) Then
        For local0 = $01 To $14 Step $01
            If ((((actors(local0)\Field41 > $00) And (180.0 < actors(local0)\Field77)) And (actors(local0)\Field19 = $00)) <> 0) Then
                If (factioncollisions(arg0\Field30, actors(local0)\Field30) > $00) Then
                    If (anglecollisionxy(arg0\Field48, arg0\Field49, arg0\Field53, arg0\Field54, arg0\Field50, actors(local0)\Field48, actors(local0)\Field49, actors(local0)\Field53, actors(local0)\Field54, actors(local0)\Field50) = $01) Then
                        arg0\Field59 = (arg0\Field59 - (actors(local0)\Field31 / 2.0))
                        arg0\Field79 = local0
                        actors(local0)\Field59 = (actors(local0)\Field59 - (arg0\Field31 / 2.0))
                        If (100.0 > arg0\Field55) Then
                            arg0\Field55 = 255.0
                        EndIf
                        If (100.0 > actors(local0)\Field55) Then
                            actors(local0)\Field55 = 255.0
                        EndIf
                    EndIf
                EndIf
            EndIf
        Next
    EndIf
    Return $00
End Function
