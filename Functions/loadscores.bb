Function loadscores%()
    Local local0%
    Local local1%
    If (filetype("scores.dat") = $00) Then
        defaultscores()
        savescores()
    EndIf
    local0 = readfile("scores.dat")
    For local1 = $01 To $0A Step $01
        highscores(local1)\Field0 = readstring(local0)
        highscores(local1)\Field1 = readint(local0)
    Next
    closefile(local0)
    Return $00
End Function
