Function saveres%()
    Local local0%
    local0 = writefile("res.dat")
    writeint(local0, loadresmode)
    closefile(local0)
    Return $00
End Function
