Function addsprite%(arg0%, arg1#, arg2#, arg3#, arg4#)
    Local local0.layer
    Local local1%
    Local local2.sprite
    local0 = (Object.layer (Int entityname(arg0)))
    local1 = getsurface(local0\Field0, $01)
    local0\Field1 = (local0\Field1 + $01)
    resizebank(local0\Field2, (local0\Field1 Shl $02))
    local2 = (New sprite)
    local2\Field16[$00] = addvertex(local1, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0)
    local2\Field16[$01] = addvertex(local1, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
    local2\Field16[$02] = addvertex(local1, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0)
    local2\Field16[$03] = addvertex(local1, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0)
    local2\Field20 = $01
    arg1 = (arg1 + 0.01)
    arg2 = (arg2 + 0.01)
    arg3 = (arg3 - 0.01)
    arg4 = (arg4 - 0.01)
    vertextexcoords(local1, local2\Field16[$00], arg1, arg2, 1.0, $00)
    vertextexcoords(local1, local2\Field16[$01], arg3, arg2, 1.0, $00)
    vertextexcoords(local1, local2\Field16[$02], arg1, arg4, 1.0, $00)
    vertextexcoords(local1, local2\Field16[$03], arg3, arg4, 1.0, $00)
    addtriangle(local1, local2\Field16[$01], local2\Field16[$02], local2\Field16[$03])
    addtriangle(local1, local2\Field16[$02], local2\Field16[$01], local2\Field16[$00])
    local2\Field3 = 10.0
    local2\Field4 = 10.0
    local2\Field5 = 1.0
    local2\Field12 = $FF
    local2\Field13 = $FF
    local2\Field14 = $FF
    local2\Field0 = 0.0
    local2\Field1 = 0.0
    local2\Field2 = 0.0
    local2\Field17 = local1
    local2\Field18 = local0\Field0
    local2\Field24 = $00
    pokeint(local0\Field2, ((local0\Field1 Shl $02) - $04), (Handle local2))
    Return (Handle local2)
    Return $00
End Function
