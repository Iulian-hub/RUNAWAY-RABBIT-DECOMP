Function autobordermenu%()
    Local local0%
    For local0 = $01 To $32 Step $01
        If (((menus(local0)\Field45 > $00) And (menus(local0)\Field0 > $00)) <> 0) Then
            menus(local0)\Field51[$65] = $01
            menus(local0)\Field53[$65] = $33
            menus(local0)\Field54[$65] = ((menus(local0)\Field4 / 2.0) + menus(local0)\Field2)
            menus(local0)\Field55[$65] = menus(local0)\Field3
            menus(local0)\Field56[$65] = (Float menus(local0)\Field46)
            menus(local0)\Field57[$65] = ((menus(local0)\Field5 - (Float menus(local0)\Field46)) + 10.0)
            menus(local0)\Field58[$65] = 1.0
            menus(local0)\Field60[$65] = (Float menus(local0)\Field46)
            menus(local0)\Field51[$66] = $01
            menus(local0)\Field53[$66] = $3A
            menus(local0)\Field54[$66] = menus(local0)\Field2
            menus(local0)\Field55[$66] = ((menus(local0)\Field5 / 2.0) + menus(local0)\Field3)
            menus(local0)\Field56[$66] = ((menus(local0)\Field4 - (Float menus(local0)\Field46)) + 10.0)
            menus(local0)\Field57[$66] = (Float menus(local0)\Field46)
            menus(local0)\Field58[$66] = 1.0
            menus(local0)\Field60[$66] = (Float menus(local0)\Field46)
            menus(local0)\Field51[$67] = $01
            menus(local0)\Field53[$67] = $39
            menus(local0)\Field54[$67] = (menus(local0)\Field2 - (menus(local0)\Field4 / 2.0))
            menus(local0)\Field55[$67] = menus(local0)\Field3
            menus(local0)\Field56[$67] = (Float menus(local0)\Field46)
            menus(local0)\Field57[$67] = ((menus(local0)\Field5 - (Float menus(local0)\Field46)) + 10.0)
            menus(local0)\Field58[$67] = 1.0
            menus(local0)\Field60[$67] = (Float menus(local0)\Field46)
            menus(local0)\Field51[$68] = $01
            menus(local0)\Field53[$68] = $34
            menus(local0)\Field54[$68] = menus(local0)\Field2
            menus(local0)\Field55[$68] = (menus(local0)\Field3 - (menus(local0)\Field5 / 2.0))
            menus(local0)\Field56[$68] = ((menus(local0)\Field4 - (Float menus(local0)\Field46)) + 10.0)
            menus(local0)\Field57[$68] = (Float menus(local0)\Field46)
            menus(local0)\Field58[$68] = 1.0
            menus(local0)\Field60[$68] = (Float menus(local0)\Field46)
            menus(local0)\Field51[$69] = $01
            menus(local0)\Field53[$69] = $35
            menus(local0)\Field54[$69] = ((menus(local0)\Field4 / 2.0) + menus(local0)\Field2)
            menus(local0)\Field55[$69] = (menus(local0)\Field3 - (menus(local0)\Field5 / 2.0))
            menus(local0)\Field56[$69] = (Float menus(local0)\Field46)
            menus(local0)\Field57[$69] = (Float menus(local0)\Field46)
            menus(local0)\Field58[$69] = 1.0
            menus(local0)\Field60[$69] = (Float menus(local0)\Field46)
            menus(local0)\Field51[$6A] = $01
            menus(local0)\Field53[$6A] = $36
            menus(local0)\Field54[$6A] = (menus(local0)\Field2 - (menus(local0)\Field4 / 2.0))
            menus(local0)\Field55[$6A] = (menus(local0)\Field3 - (menus(local0)\Field5 / 2.0))
            menus(local0)\Field56[$6A] = (Float menus(local0)\Field46)
            menus(local0)\Field57[$6A] = (Float menus(local0)\Field46)
            menus(local0)\Field58[$6A] = 1.0
            menus(local0)\Field60[$6A] = (Float menus(local0)\Field46)
            menus(local0)\Field51[$6B] = $01
            menus(local0)\Field53[$6B] = $38
            menus(local0)\Field54[$6B] = ((menus(local0)\Field4 / 2.0) + menus(local0)\Field2)
            menus(local0)\Field55[$6B] = ((menus(local0)\Field5 / 2.0) + menus(local0)\Field3)
            menus(local0)\Field56[$6B] = (Float menus(local0)\Field46)
            menus(local0)\Field57[$6B] = (Float menus(local0)\Field46)
            menus(local0)\Field58[$6B] = 1.0
            menus(local0)\Field60[$6B] = (Float menus(local0)\Field46)
            menus(local0)\Field51[$6C] = $01
            menus(local0)\Field53[$6C] = $37
            menus(local0)\Field54[$6C] = (menus(local0)\Field2 - (menus(local0)\Field4 / 2.0))
            menus(local0)\Field55[$6C] = ((menus(local0)\Field5 / 2.0) + menus(local0)\Field3)
            menus(local0)\Field56[$6C] = (Float menus(local0)\Field46)
            menus(local0)\Field57[$6C] = (Float menus(local0)\Field46)
            menus(local0)\Field58[$6C] = 1.0
            menus(local0)\Field60[$6C] = (Float menus(local0)\Field46)
            If (140.0 < menus(local0)\Field5) Then
                menus(local0)\Field51[$6D] = $01
                menus(local0)\Field53[$6D] = $3D
                menus(local0)\Field54[$6D] = (((menus(local0)\Field4 / 2.0) + menus(local0)\Field2) + 5.0)
                menus(local0)\Field55[$6D] = menus(local0)\Field3
                menus(local0)\Field56[$6D] = (Float (menus(local0)\Field46 Shl $01))
                menus(local0)\Field57[$6D] = (Float (menus(local0)\Field46 Shl $01))
                menus(local0)\Field58[$6D] = 1.0
                menus(local0)\Field60[$6D] = (Float (menus(local0)\Field46 Shl $01))
                menus(local0)\Field51[$6E] = $01
                menus(local0)\Field53[$6E] = $3B
                menus(local0)\Field54[$6E] = ((menus(local0)\Field2 - (menus(local0)\Field4 / 2.0)) - 5.0)
                menus(local0)\Field55[$6E] = menus(local0)\Field3
                menus(local0)\Field56[$6E] = (Float (menus(local0)\Field46 Shl $01))
                menus(local0)\Field57[$6E] = (Float (menus(local0)\Field46 Shl $01))
                menus(local0)\Field58[$6E] = 1.0
                menus(local0)\Field60[$6E] = (Float (menus(local0)\Field46 Shl $01))
            EndIf
            If (140.0 < menus(local0)\Field4) Then
                menus(local0)\Field51[$6F] = $01
                menus(local0)\Field53[$6F] = $3E
                menus(local0)\Field54[$6F] = menus(local0)\Field2
                menus(local0)\Field55[$6F] = (((menus(local0)\Field5 / 2.0) + menus(local0)\Field3) + 5.0)
                menus(local0)\Field56[$6F] = (Float (menus(local0)\Field46 Shl $01))
                menus(local0)\Field57[$6F] = (Float (menus(local0)\Field46 Shl $01))
                menus(local0)\Field58[$6F] = 1.0
                menus(local0)\Field60[$6F] = (Float (menus(local0)\Field46 Shl $01))
                menus(local0)\Field51[$70] = $01
                menus(local0)\Field53[$70] = $3C
                menus(local0)\Field54[$70] = menus(local0)\Field2
                menus(local0)\Field55[$70] = ((menus(local0)\Field3 - (menus(local0)\Field5 / 2.0)) - 5.0)
                menus(local0)\Field56[$70] = (Float (menus(local0)\Field46 Shl $01))
                menus(local0)\Field57[$70] = (Float (menus(local0)\Field46 Shl $01))
                menus(local0)\Field58[$70] = 1.0
                menus(local0)\Field60[$70] = (Float (menus(local0)\Field46 Shl $01))
            EndIf
        EndIf
    Next
    Return $00
End Function
