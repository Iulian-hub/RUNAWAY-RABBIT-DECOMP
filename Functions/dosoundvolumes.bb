Function dosoundvolumes%()
    Local local0%
    Local local1%
    Local local2%
    Local local3#
    For local0 = $01 To $64 Step $01
        If (gamesounds(local0)\Field0 > $00) Then
            local1 = $00
            For local2 = $01 To $64 Step $01
                If (gamesounds(local0)\Field2[local2] <> $00) Then
                    local1 = (local1 + $01)
                EndIf
            Next
            If (local1 > $00) Then
                local3 = (1.0 / (Float local1))
                For local2 = $01 To $64 Step $01
                    If (gamesounds(local0)\Field2[local2] <> $00) Then
                        channelvolume(gamesounds(local0)\Field2[local2], (local3 * gamesounds(local0)\Field1))
                        gamesounds(local0)\Field2[local2] = $00
                    EndIf
                Next
            Else
                For local2 = $01 To $64 Step $01
                    If (gamesounds(local0)\Field2[local2] <> $00) Then
                        gamesounds(local0)\Field2[local2] = $00
                    EndIf
                Next
            EndIf
        EndIf
    Next
    Return $00
End Function
