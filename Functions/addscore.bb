Function addscore%(arg0%)
    Local local0%
    Local local1%
    local0 = $00
    local1 = $0A
    While (local1 >= $01)
        If (arg0 > highscores(local1)\Field1) Then
            local0 = local1
        EndIf
        local1 = (local1 + $FFFFFFFF)
    Wend
    If (local0 > $00) Then
        local1 = $0A
        While (local1 >= local0)
            If (local1 < $0A) Then
                highscores((local1 + $01))\Field1 = highscores(local1)\Field1
                highscores((local1 + $01))\Field0 = highscores(local1)\Field0
            EndIf
            local1 = (local1 + $FFFFFFFF)
        Wend
        highscores(local0)\Field1 = arg0
        highscores(local0)\Field0 = profiles(currentprofile)\Field0
    EndIf
    savescores()
    Return local0
    Return $00
End Function
