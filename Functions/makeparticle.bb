Function makeparticle%(arg0#, arg1#, arg2#, arg3#, arg4#, arg5#, arg6#, arg7%, arg8#, arg9#, arg10#, arg11#, arg12#, arg13%)
    Local local0%
    Local local1%
    Local local2%
    Local local3%
    Local local4.sprite
    Local local5.sprite
    Local local6%
    Local local7#
    Local local8#
    Local local9#
    local0 = $00
    local1 = $F423F
    local2 = $00
    local3 = $01
    If (arg7 > $13) Then
        local3 = $14
    EndIf
    If (arg7 > $27) Then
        local3 = $28
    EndIf
    If (arg7 > $32) Then
        local3 = $33
    EndIf
    If (arg7 > $42) Then
        local3 = arg7
    EndIf
    For local4 = Each sprite
        If (((local4\Field18 = particletypes(local3)\Field0) And (local4\Field25 = arg7)) <> 0) Then
            If ((((Float local1) > local4\Field19) And (local4\Field24 = $00)) <> 0) Then
                local1 = (Int local4\Field19)
                local0 = (Handle local4)
                local5 = (Object.sprite local0)
                local2 = $01
                local6 = (local6 + $01)
            EndIf
        EndIf
    Next
    If (local2 = $01) Then
        If (((0.0 < local5\Field19) And (particletypes(arg7)\Field3 < particletypes(arg7)\Field2)) <> 0) Then
            particletypes(arg7)\Field3 = (particletypes(arg7)\Field3 + $01)
            local0 = addsprite(particletypes(local3)\Field0, particletypes(arg7)\Field4, particletypes(arg7)\Field5, particletypes(arg7)\Field6, particletypes(arg7)\Field7)
            local5 = (Object.sprite local0)
        EndIf
    Else
        particletypes(arg7)\Field3 = (particletypes(arg7)\Field3 + $01)
        local0 = addsprite(particletypes(local3)\Field0, particletypes(arg7)\Field4, particletypes(arg7)\Field5, particletypes(arg7)\Field6, particletypes(arg7)\Field7)
        local5 = (Object.sprite local0)
    EndIf
    If (local5\Field24 > $00) Then
        Return $00
    EndIf
    local5\Field25 = arg7
    rotateentity(mathent, 0.0, 0.0, arg3, $00)
    tformnormal(0.0, 1.0, 0.0, mathent, $00)
    local7 = (arg8 * tformedx())
    local8 = (arg8 * tformedy())
    local9 = 0.0
    local5\Field24 = $00
    local5\Field23 = arg13
    local5\Field15 = 0.0
    setsprite(local0, arg12, arg0, arg1, arg2, local7, local8, local9, arg3, arg5, arg10, arg6, arg11, arg4, arg9, $01)
    Return local0
    Return $00
End Function
