Function loadres%()
    Local local0%
    If (filetype("res.dat") = $00) Then
        guessres()
        saveres()
    EndIf
    local0 = readfile("res.dat")
    loadresmode = readint(local0)
    closefile(local0)
    If (((loadresmode < $01) Or (loadresmode > $08)) <> 0) Then
        guessres()
        saveres()
    EndIf
    Return $00
End Function
