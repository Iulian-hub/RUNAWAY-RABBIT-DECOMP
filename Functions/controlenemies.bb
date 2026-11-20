Function controlenemies%()
    Local local0%
    Local local1.levelevent
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
    Local local13#
    Local local14%
    bonustimer = (bonustimer + fl\Field1)
    If (((((((0.0 < bombtime) Or (leveltimewait > $00)) Or (0.0 < getreadytime)) Or (0.0 <> gameovertime)) Or (0.0 <> wintime)) Or (gamemode = $02)) <> 0) Then
        Return $00
    EndIf
    For local0 = $01 To $05 Step $01
        local1 = levels(currentlevel, currentevent)
        If ((((currentenemies < local1\Field3) And (local1\Field22[local0] > $00)) And (currentenemiestype(local0) < local1\Field24[local0])) <> 0) Then
            local2 = rand($01, (Int (local1\Field23[local0] / fl\Field1)))
            If (local2 < $02) Then
                local2 = $02
            EndIf
            If (rand($01, local2) = $02) Then
                If ((((((enemytypes(local1\Field22[local0])\Field0 = $00) And (enemytypes(local1\Field22[local0])\Field1 = $00)) And (enemytypes(local1\Field22[local0])\Field2[$01] = $00)) And (enemytypes(local1\Field22[local0])\Field2[$02] = $00)) And (enemytypes(local1\Field22[local0])\Field2[$03] = $00)) <> 0) Then
                    local3 = $00
                    local4 = $01
                    local5 = $01
                    local6 = $FFFFFFCE
                    local7 = $113
                Else
                    local3 = enemytypes(local1\Field22[local0])\Field2[$01]
                    local4 = enemytypes(local1\Field22[local0])\Field2[$02]
                    local5 = enemytypes(local1\Field22[local0])\Field2[$03]
                    local6 = enemytypes(local1\Field22[local0])\Field0
                    If (enemytypes(local1\Field22[local0])\Field1 > $00) Then
                        local7 = enemytypes(local1\Field22[local0])\Field1
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
                local13 = 700.0
                If (specialmode = $00) Then
                    local13 = 700.0
                EndIf
                If (numplayers = $02) Then
                    local13 = (local13 / 2.0)
                EndIf
                If (((local13 < bonustimer) And (local1\Field22[local0] <> $0F)) <> 0) Then
                    local14 = $02
                    bonustimer = 0.0
                EndIf
                makeenemy(local1\Field22[local0], local10, local11, local12, local14, $00)
            EndIf
        EndIf
    Next
    Return $00
End Function
