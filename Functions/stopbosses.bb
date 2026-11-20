Function stopbosses%()
    Local local0%
    For local0 = $01 To $03 Step $01
        boss(local0)\Field0 = $00
        bossbarnum = $00
    Next
    Return $00
End Function
