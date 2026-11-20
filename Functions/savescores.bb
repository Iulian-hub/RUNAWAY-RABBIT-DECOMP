Function savescores%()
    Local local0%
    Local local1%
    local0 = writefile("scores.dat")
    For local1 = $01 To $0A Step $01
        writestring(local0, highscores(local1)\Field0)
        writeint(local0, highscores(local1)\Field1)
    Next
    closefile(local0)
    Return $00
End Function
