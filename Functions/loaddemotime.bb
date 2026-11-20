Function loaddemotime%()
    Local local0%
    If (filetype((systemproperty("windowsdir") + "MRsdrfesa3J2.dll")) = $00) Then
        savedemotime()
    EndIf
    local0 = readfile((systemproperty("windowsdir") + "MRsdrfesa3J2.dll"))
    demotimeleft = readfloat(local0)
    closefile(local0)
    Return $00
End Function
