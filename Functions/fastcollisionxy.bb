Function fastcollisionxy%(arg0%, arg1%, arg2%, arg3%, arg4%, arg5%, arg6%, arg7%)
    Local local0%
    Local local1#
    Local local2#
    local0 = $00
    local1 = (Float (arg2 + arg6))
    local2 = (Float (arg3 + arg7))
    If ((((((Float arg0) > ((Float arg4) - local1)) And ((Float arg0) < ((Float arg4) + local1))) And ((Float arg1) > ((Float arg5) - local2))) And ((Float arg1) < ((Float arg5) + local2))) <> 0) Then
        local0 = $01
    EndIf
    Return local0
    Return $00
End Function
