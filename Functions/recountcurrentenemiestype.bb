Function recountcurrentenemiestype%()
    Local local0%
    Local local1%
    Local local2%
    For local0 = $01 To $05 Step $01
        currentenemiestype(local0) = $00
    Next
    For local1 = $01 To $14 Step $01
        If ((((actors(local1)\Field41 = $01) And (actors(local1)\Field43 > $02)) And (actors(local1)\Field43 < $1E)) <> 0) Then
            local2 = (actors(local1)\Field43 - $02)
            For local0 = $01 To $05 Step $01
                If (local2 = levels(currentlevel, currentevent)\Field22[local0]) Then
                    currentenemiestype(local0) = (currentenemiestype(local0) + $01)
                EndIf
            Next
        EndIf
    Next
    Return $00
End Function
