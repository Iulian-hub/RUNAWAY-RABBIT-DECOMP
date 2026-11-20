Function gui_loadfont.gui_font(arg0$, arg1$, arg2$, arg3%, arg4%)
    Local local0.gui_font
    Local local1%
    Local local2%
    Local local3$
    Local local4$
    Local local5%
    Local local6%
    Local local7%
    Local local8%
    Local local9%
    Local local10%
    Local local11%
    Local local12%
    Local local13%
    Local local14%
    Local local15%
    Local local16%
    Local local17%
    Local local18%
    Local local19%
    Local local20%
    Local local21%
    Local local22%
    Local local23%
    Local local24%
    Local local25#
    Local local26#
    Local local27#
    Local local28#
    Local local29#
    local0 = (New gui_font)
    local0\Field4 = arg3
    local0\Field0 = arg2
    If (instr(arg2, " ", $01) > $00) Then
        arg2 = left(arg2, (len(arg2) - $01))
    EndIf
    local1 = (Int ((Float arg3) ^ 2.0))
    local0\Field1 = (Int ceil(((Float len(arg2)) / (Float local1))))
    For local2 = $00 To (local0\Field1 - $01) Step $01
        local3 = right(("0" + (Str (local2 + $01))), $02)
        local4 = ((arg0 + local3) + arg1)
        local0\Field2[local2] = loadtexture(local4, arg4)
        If (local0\Field2[local2] = $00) Then
            runtimeerror((("Error: File '" + local4) + "' not found."))
        EndIf
    Next
    For local2 = $00 To (local0\Field1 - $01) Step $01
        local0\Field3[local2] = createbrush(255.0, 255.0, 255.0)
        brushtexture(local0\Field3[local2], local0\Field2[local2], $00, $00)
    Next
    For local5 = $00 To (len(arg2) - $01) Step $01
        local6 = (local5 / local1)
        local7 = (local5 Mod local1)
        local8 = local0\Field2[local6]
        local9 = texturewidth(local8)
        local10 = textureheight(local8)
        local11 = (local7 Mod arg3)
        local12 = (local7 / arg3)
        local13 = (local9 / arg3)
        local14 = (local13 * local11)
        local15 = (local13 * local12)
        lockbuffer(texturebuffer(local8, $00))
        local16 = local14
        local17 = ((local13 - $01) + local14)
        For local18 = $00 To (local13 - $01) Step $01
            For local19 = $00 To (local13 - $01) Step $01
                local20 = (local14 + local18)
                local21 = (local15 + local19)
                local22 = readpixelfast(local20, local21, texturebuffer(local8, $00))
                local23 = (local22 And $FF000000)
                If (local23 <> $00) Then
                    local16 = local20
                    Exit
                EndIf
            Next
            If (local23 <> $00) Then
                Exit
            EndIf
        Next
        local18 = (local13 - $01)
        While (local18 >= $00)
            For local19 = $00 To (local13 - $01) Step $01
                local20 = (local14 + local18)
                local21 = (local15 + local19)
                local22 = readpixelfast(local20, local21, texturebuffer(local8, $00))
                local23 = (local22 And $FF000000)
                If (local23 <> $00) Then
                    local17 = local20
                    Exit
                EndIf
            Next
            If (local23 <> $00) Then
                Exit
            EndIf
            local18 = (local18 + $FFFFFFFF)
        Wend
        unlockbuffer(texturebuffer(local8, $00))
        local24 = asc(mid(arg2, (local5 + $01), $01))
        local0\Field5[local24] = local6
        local25 = (1.0 / (Float local9))
        local26 = (((Float local16) / (Float local9)) - (local25 / 2.0))
        local27 = (((Float local17) / (Float local9)) + (local25 / 2.0))
        local28 = (((Float local15) / (Float local10)) + (local25 * 2.0))
        local29 = (((Float (local15 + local13)) / (Float local10)) - (local25 * 2.0))
        local0\Field6[local24] = local26
        local0\Field7[local24] = local27
        local0\Field8[local24] = local28
        local0\Field9[local24] = local29
        local0\Field10[local24] = ((local27 - local26) * (Float arg3))
    Next
    Return local0
    Return Null
End Function
