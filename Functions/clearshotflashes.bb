Function clearshotflashes%()
    Local local0%
    For local0 = $01 To $64 Step $01
        If (shotflashes(local0)\Field0 <> $00) Then
            hideentity(shotflashes(local0)\Field0)
        EndIf
        shotflashes(local0)\Field3 = 0.0
    Next
    Return $00
End Function
