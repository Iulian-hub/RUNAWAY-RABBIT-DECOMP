Function whiteflash%(arg0#, arg1#, arg2#, arg3%)
    If (((whiteflashdirection <> $00) And (arg3 < whiteflashpriority)) <> 0) Then
        Return $00
    EndIf
    whiteflashpriority = arg3
    whiteflashtargetalpha = arg0
    whiteflashalpha = arg2
    whiteflashdirection = $01
    whiteflashspeed = arg1
    Return $00
End Function
