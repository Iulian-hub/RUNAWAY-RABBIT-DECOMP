Function updatemenu%(arg0.menu)
    Local local0%
    Local local1%
    Local local2%
    Local local3%
    Local local4#
    Local local5#
    Local local6#
    Local local7#
    Local local8#
    Local local9#
    arg0\Field12 = (arg0\Field12 - fl\Field1)
    If (0.0 >= arg0\Field12) Then
        arg0\Field6 = ((((Float arg0\Field8) * fl\Field1) * 0.0375) + arg0\Field6)
        arg0\Field7 = ((((Float arg0\Field8) * fl\Field1) * 0.05) + arg0\Field7)
        If (((0.0 > arg0\Field6) And (arg0\Field8 < $00)) <> 0) Then
            arg0\Field6 = 0.0
            arg0\Field7 = 0.0
            arg0\Field8 = $00
            hideentity(gui_getobjectentity(arg0\Field9))
            For local0 = $01 To $70 Step $01
                arg0\Field59[local0] = 0.0
            Next
            For local1 = $01 To $64 Step $01
                If (arg0\Field14[local1] <> 0) Then
                    hideentity(gui_getobjectentity(arg0\Field25[local1]))
                EndIf
                If (arg0\Field34[local1] <> 0) Then
                    hideentity(gui_getobjectentity(arg0\Field35[local1]))
                EndIf
            Next
        EndIf
        If (((0.75 < arg0\Field6) And (arg0\Field8 > $00)) <> 0) Then
            arg0\Field6 = 0.75
            arg0\Field7 = 1.0
            For local0 = $01 To $70 Step $01
                arg0\Field59[local0] = arg0\Field58[local0]
            Next
            arg0\Field8 = $00
        EndIf
        For local0 = $01 To $70 Step $01
            If (arg0\Field51[local0] = $01) Then
                arg0\Field59[local0] = ((((Float arg0\Field8) * fl\Field1) * (arg0\Field58[local0] / 20.0)) + arg0\Field59[local0])
                If (((0.1 > arg0\Field59[local0]) And (arg0\Field8 < $00)) <> 0) Then
                    arg0\Field59[local0] = 0.0
                    hideentity(gui_getobjectentity(arg0\Field52[local0]))
                EndIf
                If (((arg0\Field58[local0] < arg0\Field59[local0]) And (arg0\Field8 > $00)) <> 0) Then
                    arg0\Field59[local0] = arg0\Field58[local0]
                EndIf
                entityalpha(gui_getobjectentity(arg0\Field52[local0]), arg0\Field59[local0])
                gui_positionobject(arg0\Field52[local0], arg0\Field54[local0], arg0\Field55[local0])
                gui_scaleobject(arg0\Field52[local0], arg0\Field56[local0], arg0\Field57[local0])
            EndIf
        Next
    EndIf
    entityalpha(gui_getobjectentity(arg0\Field9), arg0\Field6)
    gui_positionobject(arg0\Field9, arg0\Field2, arg0\Field3)
    gui_scaleobject(arg0\Field9, arg0\Field4, arg0\Field5)
    For local1 = $01 To $64 Step $01
        local2 = $00
        For local3 = $01 To $0A Step $01
            If (radiobuttonselected(local3) = local1) Then
                local2 = $01
            EndIf
        Next
        If (arg0\Field14[local1] <> 0) Then
            If (((arg0\Field50[local1] = $00) And (arg0\Field47[local1] = $00)) <> 0) Then
                If ((((currentmouseover = local1) Or (local2 = $01)) And (arg0 = menus(currentmenu))) <> 0) Then
                    local4 = 105.0
                    local5 = (local4 / 20.0)
                    arg0\Field20[local1] = (arg0\Field20[local1] + (local5 * fl\Field1))
                    local6 = 35.0
                    local7 = (local6 / 20.0)
                    arg0\Field21[local1] = (arg0\Field21[local1] + (local7 * fl\Field1))
                    local8 = -70.0
                    local9 = (local8 / 20.0)
                    arg0\Field22[local1] = (arg0\Field22[local1] + (local9 * fl\Field1))
                    If ((((0.0 > local4) And (255.0 > arg0\Field20[local1])) Or ((0.0 < local4) And (255.0 < arg0\Field20[local1]))) <> 0) Then
                        arg0\Field20[local1] = 255.0
                    EndIf
                    If ((((0.0 > local6) And (255.0 > arg0\Field21[local1])) Or ((0.0 < local6) And (255.0 < arg0\Field21[local1]))) <> 0) Then
                        arg0\Field21[local1] = 255.0
                    EndIf
                    If ((((0.0 > local8) And (150.0 > arg0\Field22[local1])) Or ((0.0 < local8) And (150.0 < arg0\Field22[local1]))) <> 0) Then
                        arg0\Field22[local1] = 150.0
                    EndIf
                Else
                    local4 = -105.0
                    local5 = (local4 / 20.0)
                    arg0\Field20[local1] = (arg0\Field20[local1] + (local5 * fl\Field1))
                    local6 = -35.0
                    local7 = (local6 / 20.0)
                    arg0\Field21[local1] = (arg0\Field21[local1] + (local7 * fl\Field1))
                    local8 = 70.0
                    local9 = (local8 / 20.0)
                    arg0\Field22[local1] = (arg0\Field22[local1] + (local9 * fl\Field1))
                    If ((((0.0 > local4) And (150.0 > arg0\Field20[local1])) Or ((0.0 < local4) And (150.0 < arg0\Field20[local1]))) <> 0) Then
                        arg0\Field20[local1] = 150.0
                    EndIf
                    If ((((0.0 > local6) And (220.0 > arg0\Field21[local1])) Or ((0.0 < local6) And (220.0 < arg0\Field21[local1]))) <> 0) Then
                        arg0\Field21[local1] = 220.0
                    EndIf
                    If ((((0.0 > local8) And (220.0 > arg0\Field22[local1])) Or ((0.0 < local8) And (220.0 < arg0\Field22[local1]))) <> 0) Then
                        arg0\Field22[local1] = 220.0
                    EndIf
                EndIf
            EndIf
            If (arg0\Field47[local1] <> 0) Then
                arg0\Field20[local1] = 150.0
                arg0\Field21[local1] = 0.0
                arg0\Field22[local1] = 0.0
            EndIf
            entityalpha(gui_getobjectentity(arg0\Field25[local1]), arg0\Field7)
            entitycolor(gui_getobjectentity(arg0\Field25[local1]), arg0\Field20[local1], arg0\Field21[local1], arg0\Field22[local1])
            gui_setstring(arg0\Field25[local1], arg0\Field15[local1], $00)
            gui_positionobject(arg0\Field25[local1], (arg0\Field16[local1] + arg0\Field2), (arg0\Field17[local1] + arg0\Field3))
            If (arg0\Field27[local1] <> 0) Then
                entityalpha(gui_getobjectentity(arg0\Field33[local1]), arg0\Field7)
                entitycolor(gui_getobjectentity(arg0\Field33[local1]), arg0\Field20[local1], arg0\Field21[local1], arg0\Field22[local1])
                gui_setstring(arg0\Field33[local1], arg0\Field28[local1], $00)
                gui_positionobject(arg0\Field33[local1], (arg0\Field29[local1] + arg0\Field2), (arg0\Field30[local1] + arg0\Field3))
            EndIf
        EndIf
        If (arg0\Field34[local1] <> 0) Then
            If (((arg0\Field50[local1] = $00) And (arg0\Field47[local1] = $00)) <> 0) Then
                If ((((currentmouseover = local1) Or (local2 = $01)) And (arg0 = menus(currentmenu))) <> 0) Then
                    local4 = 128.0
                    local5 = (local4 / 20.0)
                    arg0\Field41[local1] = (arg0\Field41[local1] + (local5 * fl\Field1))
                    local6 = 128.0
                    local7 = (local6 / 20.0)
                    arg0\Field42[local1] = (arg0\Field42[local1] + (local7 * fl\Field1))
                    local8 = 128.0
                    local9 = (local8 / 20.0)
                    arg0\Field43[local1] = (arg0\Field43[local1] + (local9 * fl\Field1))
                    If ((((0.0 > local4) And (255.0 > arg0\Field41[local1])) Or ((0.0 < local4) And (255.0 < arg0\Field41[local1]))) <> 0) Then
                        arg0\Field41[local1] = 255.0
                    EndIf
                    If ((((0.0 > local6) And (255.0 > arg0\Field42[local1])) Or ((0.0 < local6) And (255.0 < arg0\Field42[local1]))) <> 0) Then
                        arg0\Field42[local1] = 255.0
                    EndIf
                    If ((((0.0 > local8) And (255.0 > arg0\Field43[local1])) Or ((0.0 < local8) And (255.0 < arg0\Field43[local1]))) <> 0) Then
                        arg0\Field43[local1] = 255.0
                    EndIf
                Else
                    local4 = -128.0
                    local5 = (local4 / 20.0)
                    arg0\Field41[local1] = (arg0\Field41[local1] + (local5 * fl\Field1))
                    local6 = -128.0
                    local7 = (local6 / 20.0)
                    arg0\Field42[local1] = (arg0\Field42[local1] + (local7 * fl\Field1))
                    local8 = -128.0
                    local9 = (local8 / 20.0)
                    arg0\Field43[local1] = (arg0\Field43[local1] + (local9 * fl\Field1))
                    If ((((0.0 > local4) And (128.0 > arg0\Field41[local1])) Or ((0.0 < local4) And (128.0 < arg0\Field41[local1]))) <> 0) Then
                        arg0\Field41[local1] = 128.0
                    EndIf
                    If ((((0.0 > local6) And (128.0 > arg0\Field42[local1])) Or ((0.0 < local6) And (128.0 < arg0\Field42[local1]))) <> 0) Then
                        arg0\Field42[local1] = 128.0
                    EndIf
                    If ((((0.0 > local8) And (128.0 > arg0\Field43[local1])) Or ((0.0 < local8) And (128.0 < arg0\Field43[local1]))) <> 0) Then
                        arg0\Field43[local1] = 128.0
                    EndIf
                EndIf
            EndIf
            If (arg0\Field47[local1] <> 0) Then
                arg0\Field41[local1] = 128.0
                arg0\Field42[local1] = 128.0
                arg0\Field43[local1] = 128.0
            EndIf
            entitytexture(gui_getobjectentity(arg0\Field35[local1]), menutextures(arg0\Field36[local1]), $00, $00)
            entityalpha(gui_getobjectentity(arg0\Field35[local1]), arg0\Field7)
            entitycolor(gui_getobjectentity(arg0\Field35[local1]), arg0\Field41[local1], arg0\Field42[local1], arg0\Field43[local1])
            gui_positionobject(arg0\Field35[local1], (arg0\Field39[local1] + arg0\Field2), (arg0\Field40[local1] + arg0\Field3))
            gui_scaleobject(arg0\Field35[local1], arg0\Field37[local1], arg0\Field38[local1])
        EndIf
    Next
    Return $00
End Function
