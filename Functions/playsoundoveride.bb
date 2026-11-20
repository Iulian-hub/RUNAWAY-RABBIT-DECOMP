Function playsoundoveride%(arg0%)
    Local local0%
    Local local1%
    Local local2%
    local0 = $00
    For local1 = $01 To $64 Step $01
        If (gamesounds(local1)\Field0 = arg0) Then
            local0 = local1
        EndIf
    Next
    If (local0 = $00) Then
        For local1 = $01 To $64 Step $01
            If (gamesounds(local1)\Field0 = $00) Then
                local0 = local1
            EndIf
        Next
    EndIf
    If (local0 = $00) Then
        Return $00
    EndIf
    gamesounds(local0)\Field0 = arg0
    For local1 = $01 To $64 Step $01
        If (gamesounds(local0)\Field2[local1] = $00) Then
            local2 = local1
        EndIf
    Next
    If (local2 = $00) Then
        Return $00
    EndIf
    gamesounds(local0)\Field2[local2] = playsound(arg0)
    channelvolume(gamesounds(local0)\Field2[local2], 0.0)
    Return $00
End Function
