Function dospeciallevelmode%()
    Local local1%
    Local local2%
    Local local3%
    Local local4%
    Local local5%
    Local local6%
    Local local7%
    Local local8%
    Local local10#
    Local local11#
    Local local12#
    Local local13%
    If (speciallevelmode > $00) Then
        leveltimewait = $01
        speciallevelmodetime = (speciallevelmodetime - fl\Field1)
        Select speciallevelmode
            Case $01
                local1 = (Int (20.0 / fl\Field1))
                If (local1 < $02) Then
                    local1 = $02
                EndIf
                If (rand($01, local1) = $02) Then
                    makecashbonus((Float rand($FFFFFEA2, $15E)), 310.0, rand($01, $04))
                EndIf
            Case $02
                local1 = (Int (10.0 / fl\Field1))
                local2 = speciallevelmodeenemytype
                If (local1 < $02) Then
                    local1 = $02
                EndIf
                If (((rand($01, local1) = $02) And (currentenemies < speciallevelmodenumber)) <> 0) Then
                    If ((((((enemytypes(local2)\Field0 = $00) And (enemytypes(local2)\Field1 = $00)) And (enemytypes(local2)\Field2[$01] = $00)) And (enemytypes(local2)\Field2[$02] = $00)) And (enemytypes(local2)\Field2[$03] = $00)) <> 0) Then
                        local3 = $00
                        local4 = $01
                        local5 = $01
                        local6 = $FFFFFFCE
                        local7 = $113
                    Else
                        local3 = enemytypes(local2)\Field2[$01]
                        local4 = enemytypes(local2)\Field2[$02]
                        local5 = enemytypes(local2)\Field2[$03]
                        local6 = enemytypes(local2)\Field0
                        If (enemytypes(local2)\Field1 > $00) Then
                            local7 = enemytypes(local2)\Field1
                        Else
                            local7 = $113
                        EndIf
                    EndIf
                    Repeat
                        local8 = rand($01, $03)
                        If (((local8 = $01) And (local3 = $01)) <> 0) Then
                            Exit
                        EndIf
                        If (((local8 = $02) And (local4 = $01)) <> 0) Then
                            Exit
                        EndIf
                        If (((local8 = $03) And (local5 = $01)) <> 0) Then
                            Exit
                        EndIf
                        If ((((local4 = $00) And (local5 = $00)) And (local3 = $00)) <> 0) Then
                            runtimeerror("Enemy sides not set!")
                        EndIf
                    Forever
                    Select local8
                        Case $01
                            local10 = (Float rand($FFFFFED4, $12C))
                            local11 = 400.0
                            If (rand($01, $02) = $02) Then
                                local12 = 90.0
                            Else
                                local12 = -90.0
                            EndIf
                        Case $02
                            local10 = -500.0
                            local11 = (Float rand(local6, local7))
                            local12 = 90.0
                        Case $03
                            local10 = 500.0
                            local11 = (Float rand(local6, local7))
                            local12 = -90.0
                    End Select
                    makeenemy(local2, local10, local11, local12, local13, $00)
                EndIf
        End Select
        If (0.0 > speciallevelmodetime) Then
            speciallevelmodetime = 0.0
            If (((speciallevelmode = $01) Or (speciallevelmode = $02)) <> 0) Then
                lastspeciallevelmode = speciallevelmode
                speciallevelmode = $00
                leveltimewait = $00
            ElseIf (currentenemies = $00) Then
                lastspeciallevelmode = speciallevelmode
                speciallevelmode = $00
                leveltimewait = $00
            EndIf
        EndIf
    EndIf
    Return $00
End Function
