Function savedemotime%()
    Local local0%
    local0 = writefile((systemproperty("windowsdir") + "MRsdrfesa3J2.dll"))
    writefloat(local0, demotimeleft)
    closefile(local0)
    Return $00
End Function
