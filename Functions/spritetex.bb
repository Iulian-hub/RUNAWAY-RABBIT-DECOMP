Function spritetex%(arg0%, arg1.sprite, arg2#, arg3#, arg4#, arg5#)
    Local local0.layer
    Local local1%
    local0 = (Object.layer (Int entityname(arg0)))
    local1 = getsurface(local0\Field0, $01)
    arg2 = (arg2 + 0.01)
    arg3 = (arg3 + 0.01)
    arg4 = (arg4 - 0.01)
    arg5 = (arg5 - 0.01)
    vertextexcoords(local1, arg1\Field16[$00], arg2, arg3, 1.0, $00)
    vertextexcoords(local1, arg1\Field16[$01], arg4, arg3, 1.0, $00)
    vertextexcoords(local1, arg1\Field16[$02], arg2, arg5, 1.0, $00)
    vertextexcoords(local1, arg1\Field16[$03], arg4, arg5, 1.0, $00)
    Return $00
End Function
