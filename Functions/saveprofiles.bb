Function saveprofiles%()
    Local local0%
    Local local1%
    local0 = writefile("profiles.dat")
    writeint(local0, currentprofile)
    For local1 = $01 To $0A Step $01
        writestring(local0, profiles(local1)\Field0)
        writeint(local0, profiles(local1)\Field4)
        writeint(local0, profiles(local1)\Field2)
        writeint(local0, profiles(local1)\Field3)
        writeint(local0, profiles(local1)\Field1)
        writeint(local0, profiles(local1)\Field5)
        writeint(local0, profiles(local1)\Field6)
        writeint(local0, profiles(local1)\Field7)
        writeint(local0, profiles(local1)\Field8)
        writeint(local0, profiles(local1)\Field9)
        writeint(local0, profiles(local1)\Field10)
        writeint(local0, profiles(local1)\Field11)
        writeint(local0, profiles(local1)\Field12)
        writeint(local0, profiles(local1)\Field13)
        writeint(local0, profiles(local1)\Field14)
        writeint(local0, profiles(local1)\Field15)
        writeint(local0, profiles(local1)\Field16)
    Next
    closefile(local0)
    Return $00
End Function
