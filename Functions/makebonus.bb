Function makebonus%(arg0#, arg1#, arg2%)
    Local local0%
    Local local1%
    Local local2%
    Local local3%
    Local local4%
    Local local5%
    If (((350.0 < arg0) Or (-350.0 > arg0)) <> 0) Then
        Return $00
    EndIf
    If (((-280.0 > arg1) Or (320.0 < arg1)) <> 0) Then
        Return $00
    EndIf
    For local0 = $01 To $0A Step $01
        If (bonus(local0)\Field0 = $00) Then
            local1 = local0
        EndIf
    Next
    If (local1 = $00) Then
        Return $00
    EndIf
    local2 = $00
    For local0 = $01 To $0A Step $01
        canhave(local0) = $00
    Next
    If ((((specialmode > $00) Or ((player($01)\Field0 = $01) And (playersetup($01)\Field4 > $00))) Or ((player($02)\Field0 = $01) And (playersetup($02)\Field4 > $00))) <> 0) Then
        canhave($01) = $01
    EndIf
    If ((((specialmode > $00) Or ((player($01)\Field0 = $01) And (playersetup($01)\Field5 > $00))) Or ((player($02)\Field0 = $01) And (playersetup($02)\Field5 > $00))) <> 0) Then
        canhave($02) = $01
    EndIf
    If ((((specialmode > $00) Or ((player($01)\Field0 = $01) And (playersetup($01)\Field6 > $00))) Or ((player($02)\Field0 = $01) And (playersetup($02)\Field6 > $00))) <> 0) Then
        canhave($03) = $01
    EndIf
    If ((((specialmode > $00) Or ((player($01)\Field0 = $01) And (playersetup($01)\Field8 > $00))) Or ((player($02)\Field0 = $01) And (playersetup($02)\Field8 > $00))) <> 0) Then
        canhave($04) = $01
    EndIf
    If ((((specialmode > $00) Or ((player($01)\Field0 = $01) And (playersetup($01)\Field7 > $00))) Or ((player($02)\Field0 = $01) And (playersetup($02)\Field7 > $00))) <> 0) Then
        canhave($05) = $01
    EndIf
    canhave($06) = $01
    canhave($07) = $01
    canhave($08) = $01
    canhave($09) = $01
    canhave($0A) = $01
    If (specialmode = $02) Then
        canhave($09) = $00
        canhave($0A) = $00
    EndIf
    If (((((playersetup($01)\Field4 + player($01)\Field28) >= levels(currentlevel, currentevent)\Field27) And (levels(currentlevel, currentevent)\Field27 > $00)) And (numplayers = $01)) <> 0) Then
        canhave($01) = $00
    EndIf
    For local0 = $01 To $0A Step $01
        If (canhave(local0) = $01) Then
            local2 = $01
        EndIf
    Next
    If (local2 = $00) Then
        Return $00
    EndIf
    Repeat
        local3 = rand($01, $64)
        If (((local3 >= $01) And (local3 <= $28)) <> 0) Then
            local4 = rand($02, $05)
        EndIf
        If (((local3 >= $29) And (local3 <= $3C)) <> 0) Then
            local4 = $01
        EndIf
        If (((local3 >= $3D) And (local3 <= $5A)) <> 0) Then
            local5 = rand($01, $02)
            If (local5 = $01) Then
                local4 = $06
            Else
                local4 = $08
            EndIf
        EndIf
        If (local3 >= $5A) Then
            local5 = rand($01, $03)
            If (local5 = $02) Then
                local4 = $07
            Else
                local4 = rand($09, $0A)
            EndIf
        EndIf
        If (canhave(local4) = $01) Then
            Exit
        EndIf
    Forever
    If (((player($01)\Field28 = $00) And (playersetup($01)\Field4 < $0A)) <> 0) Then
        local4 = $01
    EndIf
    If (((((playersetup($01)\Field4 + player($01)\Field28) < levels(currentlevel, currentevent)\Field27) And (levels(currentlevel, currentevent)\Field27 > $00)) And (numplayers = $01)) <> 0) Then
        local4 = $01
    EndIf
    If (((player($01)\Field10 = $00) And (numplayers = $01)) <> 0) Then
        If (rand($01, $02) = $02) Then
            local4 = $07
        EndIf
    EndIf
    If (arg2 > $00) Then
        local4 = arg2
    EndIf
    entitytexture(bonus(local1)\Field1, bonustex, (local4 - $01), $00)
    bonus(local1)\Field0 = $01
    bonus(local1)\Field2 = local4
    bonus(local1)\Field3 = arg0
    bonus(local1)\Field4 = arg1
    bonus(local1)\Field5 = 0.0
    bonus(local1)\Field6 = 0.0
    bonus(local1)\Field8 = $00
    bonus(local1)\Field7 = 30.0
    scalesprite(bonus(local1)\Field1, bonus(local1)\Field7, bonus(local1)\Field7)
    Return $00
End Function
