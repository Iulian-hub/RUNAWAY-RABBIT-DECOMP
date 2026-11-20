Function makemorelevels%()
    Local local0%
    Local local1%
    Local local2%
    Local local4%
    Local local5%
    Local local6%
    Local local7%
    Local local8%
    Local local9%
    Local local10%
    Local local11%
    For local0 = $01 To $28 Step $01
        For local1 = $01 To $3C Step $01
            copylevelevent(levels2(local0, local1), levels(local0, local1))
        Next
    Next
    For local0 = $01 To $28 Step $01
        For local1 = $01 To $3C Step $01
            levels(local0, local1)\Field2 = $00
        Next
    Next
    For local0 = $01 To $28 Step $01
        For local1 = $01 To $3C Step $01
            local2 = $00
            Select local0
                Case $01
                    local2 = $01
                Case $02
                    local2 = $02
                Case $03
                    local2 = $03
                Case $04
                    local2 = $04
                Case $05
                    local2 = $05
                Case $06
                    local2 = $07
                Case $07
                    local2 = $09
                Case $08
                    local2 = $0B
                Case $09
                    local2 = $0D
                Case $0A
                    local2 = $10
                Case $0B
                    local2 = $13
                Case $0C
                    local2 = $15
                Case $0D
                    local2 = $18
                Case $0E
                    local2 = $1B
                Case $0F
                    local2 = $1D
            End Select
            If (((local2 = $00) And (local0 > $23)) <> 0) Then
                local2 = local0
            EndIf
            If (local2 > $00) Then
                copylevelevent(levels(local2, local1), levels2(local0, local1))
            EndIf
            Delete levels2(local0, local1)
        Next
    Next
    For local0 = $01 To $1D Step $01
        local4 = (local0 + $01)
        local5 = $00
        local6 = $00
        local7 = $00
        Repeat
            If (((levels(local4, $01)\Field2 > $00) Or ((local4 - $03) > local0)) <> 0) Then
                Exit
            EndIf
            local4 = (local4 + $01)
        Forever
        local8 = (local0 - $01)
        Repeat
            If (local8 < $01) Then
                local8 = $01
            EndIf
            If (((levels(local8, $01)\Field2 > $00) Or ((local8 - $03) > local0)) <> 0) Then
                Exit
            EndIf
            local8 = (local8 - $01)
        Forever
        local9 = $00
        If (levels(local0, $01)\Field2 = $00) Then
            For local1 = $01 To $1E Step $01
                If (0.0 = levels(local8, local1)\Field0) Then
                    local6 = $01
                EndIf
                If (0.0 = levels(local4, local1)\Field0) Then
                    local5 = $01
                EndIf
                If (local7 = $00) Then
                    If (levels(local8, local1)\Field4 = $03) Then
                        local7 = $01
                    EndIf
                    If (levels(local4, local1)\Field4 = $03) Then
                        local7 = $02
                    EndIf
                EndIf
                If (((local9 = $01) Or ((local5 = $01) And (local6 = $01))) <> 0) Then
                    copylevelevent(levels(local0, local1), levels($00, $00))
                Else
                    local10 = rand($01, $02)
                    If (local7 > $00) Then
                        local10 = local7
                    EndIf
                    If (((local10 = $01) And (local6 = $01)) <> 0) Then
                        local10 = $02
                    EndIf
                    If (((local10 = $02) And (local5 = $01)) <> 0) Then
                        local10 = $01
                    EndIf
                    If (local10 = $02) Then
                        copylevelevent(levels(local0, local1), levels(local4, local1))
                        If (((levels(local4, local1)\Field4 = $03) And (levels(local4, (local1 - $01))\Field4 <> $03)) <> 0) Then
                            local7 = $02
                        EndIf
                        levels(local0, local1)\Field0 = (Float ((local1 - $01) * $4B0))
                        If (local1 = $01) Then
                            levels(local0, local1)\Field0 = 10.0
                        EndIf
                        If (levels(local0, (local1 - $01))\Field4 = $03) Then
                            levels(local0, local1)\Field0 = (Float (((local1 - $02) * $4B0) + $12C))
                        EndIf
                        If (local1 > $01) Then
                            For local11 = $01 To $06 Step $01
                                levels(local0, (local1 - $01))\Field15[local11] = levels(local4, local1)\Field15[local11]
                                levels(local0, (local1 - $01))\Field16[local11] = levels(local4, local1)\Field16[local11]
                                levels(local0, (local1 - $01))\Field17[local11] = levels(local4, local1)\Field17[local11]
                                levels(local0, (local1 - $01))\Field18[local11] = levels(local4, local1)\Field18[local11]
                                levels(local0, (local1 - $01))\Field19[local11] = levels(local4, local1)\Field19[local11]
                                levels(local0, (local1 - $01))\Field20[local11] = levels(local4, local1)\Field20[local11]
                            Next
                            levels(local0, (local1 - $01))\Field21 = levels(local4, local1)\Field21
                            If (levels(local0, (local1 - $01))\Field4 = $03) Then
                                local9 = $01
                                levels(local0, local1)\Field26 = $01
                                zeroenemiesandbkgs(levels(local0, local1))
                                setbossevent(levels(local0, local1), local0, local1)
                            EndIf
                        EndIf
                    Else
                        copylevelevent(levels(local0, local1), levels(local8, local1))
                        If (((levels(local8, local1)\Field4 = $03) And (levels(local8, (local1 - $01))\Field4 <> $03)) <> 0) Then
                            local7 = $01
                        EndIf
                        levels(local0, local1)\Field2 = levels(local4, local1)\Field2
                        If (local1 = $01) Then
                            levels(local0, local1)\Field0 = 10.0
                        EndIf
                        If (levels(local0, (local1 - $01))\Field4 = $03) Then
                            levels(local0, local1)\Field0 = (Float (((local1 - $02) * $4B0) + $12C))
                        EndIf
                        If (local1 > $01) Then
                            For local11 = $01 To $06 Step $01
                                levels(local0, (local1 - $01))\Field15[local11] = levels(local8, local1)\Field15[local11]
                                levels(local0, (local1 - $01))\Field16[local11] = levels(local8, local1)\Field16[local11]
                                levels(local0, (local1 - $01))\Field17[local11] = levels(local8, local1)\Field17[local11]
                                levels(local0, (local1 - $01))\Field18[local11] = levels(local8, local1)\Field18[local11]
                                levels(local0, (local1 - $01))\Field19[local11] = levels(local8, local1)\Field19[local11]
                                levels(local0, (local1 - $01))\Field20[local11] = levels(local8, local1)\Field20[local11]
                            Next
                            levels(local0, (local1 - $01))\Field21 = levels(local8, local1)\Field21
                            If (levels(local0, (local1 - $01))\Field4 = $03) Then
                                local9 = $01
                                levels(local0, local1)\Field26 = $01
                                zeroenemiesandbkgs(levels(local0, local1))
                                setbossevent(levels(local0, local1), local0, local1)
                            EndIf
                        EndIf
                    EndIf
                EndIf
            Next
        EndIf
    Next
    Return $00
End Function
