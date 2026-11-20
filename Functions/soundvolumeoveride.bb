Function soundvolumeoveride%(arg0%, arg1#)
    Local local0%
    Local local1%
    arg1 = (arg1 * globalsoundvolume)
    For local0 = $01 To $64 Step $01
        If (gamesounds(local0)\Field0 = arg0) Then
            local1 = local0
        EndIf
    Next
    If (local1 = $00) Then
        For local0 = $01 To $64 Step $01
            If (gamesounds(local0)\Field0 = $00) Then
                local1 = local0
            EndIf
        Next
    EndIf
    If (local1 = $00) Then
        Return $00
    EndIf
    gamesounds(local1)\Field0 = arg0
    gamesounds(local1)\Field1 = arg1
    soundvolume(arg0, arg1)
    Return $00
End Function
