Function actordeath%(arg0.actortype, arg1%)
    Local local0%
    Local local1%
    Local local2%
    Local local3%
    Local local4%
    Local local5#
    Local local6#
    Local local7%
    If (arg0\Field43 = $11) Then
        makeexplosion(actors(arg0\Field2)\Field48, actors(arg0\Field2)\Field49, (((actors(arg0\Field2)\Field53 + actors(arg0\Field2)\Field54) / 2.0) * 2.0), 1.0)
    EndIf
    If (((arg0\Field43 > $02) And (arg0\Field43 < $1E)) <> 0) Then
        local0 = rand($01, $02)
        local1 = rand($01, $03)
        If ((((arg0\Field43 = $08) Or (arg0\Field43 = $13)) Or (arg0\Field43 = $14)) <> 0) Then
            makeexplosion(arg0\Field48, arg0\Field49, 100.0, 1.0)
        EndIf
        If (((local0 = $01) And (soundon = $01)) <> 0) Then
            playsoundoveride(sndexplo21)
        EndIf
        If (((local0 = $02) And (soundon = $01)) <> 0) Then
            playsoundoveride(sndexplo22)
        EndIf
        currentenemies = (currentenemies - $01)
        currentenemiestype(getenemiestype((arg0\Field43 - $02))) = (currentenemiestype(getenemiestype((arg0\Field43 - $02))) - $01)
        local2 = enemytypes((arg0\Field43 - $02))\Field3
        If (enemies(arg1)\Field6 = $01) Then
            local2 = (local2 * $0A)
        EndIf
        local3 = local2
        coinenemycount = (coinenemycount + $01)
        If (coinenemycount > $03) Then
            coinenemycount = $01
        EndIf
        If (((enemies(arg1)\Field6 = $00) And (bossbarnum = $00)) <> 0) Then
            local3 = (Int ((Float local2) / 1.5))
            local4 = (local3 / $32)
            If (local4 = $00) Then
                local4 = $01
            EndIf
            local3 = (local4 * $32)
        EndIf
        If (player($01)\Field1 = arg0\Field79) Then
            player($01)\Field7 = (player($01)\Field7 + $01)
            player($01)\Field11 = (player($01)\Field11 + local3)
            makescoreflyup(arg0\Field48, arg0\Field49, ("$" + (Str local3)), $64, $FF, $64, $0F, $00, 0.0, $00, 0.0075, 1.0, 7.0, $00)
            If (0.0 < player($01)\Field24) Then
                local5 = (((arg0\Field48 - player($01)\Field25) / 2.0) + player($01)\Field25)
                local6 = (((arg0\Field49 - player($01)\Field26) / 2.0) + player($01)\Field26)
                player($01)\Field25 = local5
                player($01)\Field26 = local6
                player($01)\Field23 = (player($01)\Field23 + $01)
                player($01)\Field24 = 30.0
            EndIf
            If (0.0 = player($01)\Field24) Then
                player($01)\Field25 = arg0\Field48
                player($01)\Field26 = arg0\Field49
                player($01)\Field23 = $01
                player($01)\Field24 = 30.0
            EndIf
        EndIf
        If (player($02)\Field1 = arg0\Field79) Then
            player($02)\Field7 = (player($02)\Field7 + $01)
            player($02)\Field11 = (player($02)\Field11 + local3)
            makescoreflyup(arg0\Field48, arg0\Field49, ("$" + (Str local3)), $FF, $64, $64, $0F, $00, 0.0, $00, 0.0075, 1.0, 7.0, $00)
            If (0.0 < player($02)\Field24) Then
                local5 = (((arg0\Field48 - player($02)\Field25) / 2.0) + player($02)\Field25)
                local6 = (((arg0\Field49 - player($02)\Field26) / 2.0) + player($02)\Field26)
                player($02)\Field25 = local5
                player($02)\Field26 = local6
                player($02)\Field23 = (player($02)\Field23 + $01)
                player($02)\Field24 = 30.0
            EndIf
            If (0.0 = player($02)\Field24) Then
                player($02)\Field25 = arg0\Field48
                player($02)\Field26 = arg0\Field49
                player($02)\Field23 = $01
                player($02)\Field24 = 30.0
            EndIf
        EndIf
        local7 = $01
        If (local2 >= $C8) Then
            local7 = $02
        EndIf
        If (local2 >= $190) Then
            local7 = $03
        EndIf
        If (local2 >= $320) Then
            local7 = $04
        EndIf
        If ((((coinenemycount = $01) And (((arg0\Field12 <> $FF) Or (arg0\Field13 <> $FF)) Or (arg0\Field14 <> $00))) And ((arg0\Field79 = player($01)\Field1) Or (arg0\Field79 = player($02)\Field1))) <> 0) Then
            makecashbonus(arg0\Field48, arg0\Field49, local7)
        EndIf
        If (((((arg0\Field12 = $FF) And (arg0\Field13 = $FF)) And (arg0\Field14 = $00)) And ((arg0\Field79 = player($01)\Field1) Or (arg0\Field79 = player($02)\Field1))) <> 0) Then
            makebonus(arg0\Field48, arg0\Field49, $00)
        EndIf
    EndIf
    Return $00
End Function
