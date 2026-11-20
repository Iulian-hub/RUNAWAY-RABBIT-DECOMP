Function makecashbonus%(arg0#, arg1#, arg2%)
    Local local0%
    Local local1%
    Local local2%
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
    local2 = ($0A + arg2)
    entitytexture(bonus(local1)\Field1, bonustex, (local2 - $01), $00)
    bonus(local1)\Field0 = $01
    bonus(local1)\Field2 = local2
    bonus(local1)\Field3 = arg0
    bonus(local1)\Field4 = arg1
    bonus(local1)\Field5 = -3.0
    bonus(local1)\Field6 = 0.0
    bonus(local1)\Field8 = $00
    bonus(local1)\Field7 = 30.0
    scalesprite(bonus(local1)\Field1, bonus(local1)\Field7, bonus(local1)\Field7)
    Return $00
End Function
