Function internal_createtextmesh%(arg0.gui_font, arg1$, arg2%, arg3%, arg4#, arg5%, arg6#)
    Local local0#
    Local local1%
    Local local2%
    Local local3%
    Local local4#
    Local local5#
    Local local6#
    Local local7%
    Local local8$
    Local local9#
    Local local10$
    Local local11%
    Local local12%
    Local local13%
    Local local14#
    Local local15#
    Local local16#
    Local local17#
    Local local18#
    Local local19%
    Local local20%
    Local local21%
    Local local22%
    Local local23%
    Local local25#
    Local local26#
    local0 = 0.3
    If (arg5 = $00) Then
        local1 = createmesh($00)
        For local2 = $00 To (arg0\Field1 - $01) Step $01
            local3 = createsurface(local1, $00)
            paintsurface(local3, arg0\Field3[local2])
        Next
    Else
        local1 = arg5
        For local2 = $00 To (arg0\Field1 - $01) Step $01
            local3 = getsurface(local1, (local2 + $01))
            clearsurface(local3, $01, $01)
            paintsurface(local3, arg0\Field3[local2])
        Next
    EndIf
    local4 = 0.0
    local5 = 0.0
    local6 = 0.0
    local7 = $01
    local8 = ""
    local9 = 0.0
    arg1 = (arg1 + chr($0D))
    Repeat
        local10 = mid(arg1, local7, $01)
        If (((local10 = chr($20)) Or (local10 = chr($0D))) <> 0) Then
            If (len(local8) > $00) Then
                If (arg4 < (local4 + local9)) Then
                    local5 = 0.0
                    local6 = (local6 + 1.0)
                    local4 = 0.0
                EndIf
                For local11 = $01 To len(local8) Step $01
                    local12 = asc(mid(local8, local11, $01))
                    local13 = getsurface(local1, (arg0\Field5[local12] + $01))
                    local14 = arg0\Field6[local12]
                    local15 = arg0\Field7[local12]
                    local16 = arg0\Field8[local12]
                    local17 = arg0\Field9[local12]
                    local18 = arg0\Field10[local12]
                    local19 = addvertex(local13, local5, (- local6), 0.0, local14, local16, 1.0)
                    local20 = addvertex(local13, (local5 + local18), (- local6), 0.0, local15, local16, 1.0)
                    local21 = addvertex(local13, local5, (- (local6 + 1.0)), 0.0, local14, local17, 1.0)
                    local22 = addvertex(local13, (local5 + local18), (- (local6 + 1.0)), 0.0, local15, local17, 1.0)
                    addtriangle(local13, local19, local20, local21)
                    addtriangle(local13, local20, local22, local21)
                    local5 = ((local5 + local18) + arg6)
                    local23 = (local23 + $01)
                Next
                local4 = (local4 + local9)
                local8 = ""
                local9 = 0.0
            EndIf
        EndIf
        Select local10
            Case chr($20)
                local5 = (local5 + local0)
                local4 = (local4 + local0)
            Case chr($0D)
                local5 = 0.0
                local6 = (local6 + 1.0)
                local4 = 0.0
            Default
                If (instr(arg0\Field0, local10, $01) > $00) Then
                    local8 = (local8 + local10)
                    local9 = ((arg0\Field10[asc(local10)] + local9) + arg6)
                EndIf
        End Select
        local7 = (local7 + $01)
    Until (local7 > len(arg1))
    If (local23 > $00) Then
        local25 = vertexx(local13, local22)
        local26 = (- vertexy(local13, local22))
        If (arg2 <> 0) Then
            If (arg3 <> 0) Then
                positionmesh(local1, (- (local25 / 2.0)), (local26 / 2.0), 0.0)
            Else
                positionmesh(local1, (- (local25 / 2.0)), 0.0, 0.0)
            EndIf
        ElseIf (arg3 <> 0) Then
            positionmesh(local1, 0.0, (local26 / 2.0), 0.0)
        EndIf
    EndIf
    Return local1
    Return $00
End Function
