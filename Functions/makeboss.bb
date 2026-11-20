Function makeboss%(arg0#, arg1#, arg2%)
    Local local0%
    Local local1%
    Local local2.bosstype
    Local local3#
    Local local4#
    For local0 = $01 To $03 Step $01
        If (boss(local0)\Field0 = $00) Then
            local1 = local0
        EndIf
    Next
    If (local1 = $00) Then
        Return $00
    EndIf
    If (bossbarnum = $00) Then
        bossbarnum = local1
    EndIf
    local2 = boss(local1)
    For local0 = $01 To $14 Step $01
        local2\Field21[local0] = 0.0
    Next
    local2\Field0 = $01
    local2\Field3 = arg0
    local2\Field4 = arg1
    local2\Field2 = arg2
    local2\Field6 = (Float rand($FFFFFF06, $FA))
    local2\Field7 = 200.0
    local2\Field9 = bosstypes(arg2)\Field9
    local2\Field1 = bosstypes(arg2)\Field1
    local2\Field8 = bosstypes(arg2)\Field8
    local2\Field5 = ((- getangle(local2\Field3, local2\Field4, local2\Field6, local2\Field7)) + 90.0)
    If (((0.0 <= wrapangle(local2\Field5)) And (180.0 >= wrapangle(local2\Field5))) <> 0) Then
        local3 = 90.0
    Else
        local3 = -90.0
    EndIf
    If (0.0 < local2\Field1) Then
        local3 = local2\Field1
    EndIf
    For local0 = $01 To $0A Step $01
        If (bosstypes(arg2)\Field12[local0] = $01) Then
            local2\Field12[local0] = $01
            local2\Field14[local0] = bosstypes(arg2)\Field14[local0]
            local2\Field15[local0] = bosstypes(arg2)\Field15[local0]
            local2\Field16[local0] = bosstypes(arg2)\Field16[local0]
            local2\Field18[local0] = bosstypes(arg2)\Field18[local0]
            local2\Field19[local0] = bosstypes(arg2)\Field19[local0]
            local2\Field17[local0] = bosstypes(arg2)\Field17[local0]
            local2\Field20[local0] = bosstypes(arg2)\Field20[local0]
            local4 = local3
            If (local2\Field17[local0] = $01) Then
                local4 = (- local3)
            EndIf
            If (-90.0 = local3) Then
                local2\Field15[local0] = (- local2\Field15[local0])
            EndIf
            local4 = wrapangle(local4)
            local2\Field13[local0] = makeactor((local2\Field15[local0] + arg0), (local2\Field16[local0] + arg1), local4, local2\Field14[local0], $00)
            If (numplayers = $02) Then
                actors(local2\Field13[local0])\Field58 = (actors(local2\Field13[local0])\Field58 * 2.0)
            EndIf
            actors(local2\Field13[local0])\Field1 = $00
            entityorder(actors(local2\Field13[local0])\Field42, (((local1 * $0C) + $3C) + local0))
            local2\Field10 = (actors(local2\Field13[local0])\Field58 + local2\Field10)
        EndIf
    Next
    Return $00
End Function
