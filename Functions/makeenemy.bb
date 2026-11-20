Function makeenemy%(arg0%, arg1#, arg2#, arg3#, arg4%, arg5%)
    Local local0%
    Local local1%
    Local local3#
    Local local4%
    local0 = makeactor(arg1, arg2, arg3, (arg0 + $02), arg5)
    If (local0 = $00) Then
        Return $00
    EndIf
    actors(local0)\Field47 = (rnd(0.75, 1.5) * actors(local0)\Field47)
    enemies(local0)\Field0 = (Float rand($FFFFFF38, $C8))
    enemies(local0)\Field1 = (Float rand($32, $C8))
    enemies(local0)\Field2 = (getangle(arg1, arg2, enemies(local0)\Field0, enemies(local0)\Field1) - 90.0)
    enemies(local0)\Field3 = 0.0
    enemies(local0)\Field4 = actors(local0)\Field47
    enemies(local0)\Field6 = arg5
    For local1 = $01 To $64 Step $01
        enemies(local0)\Field5[local1] = 0.0
    Next
    If (arg4 = $02) Then
        actors(local0)\Field12 = $FF
        actors(local0)\Field13 = $FF
        actors(local0)\Field14 = $00
        actors(local0)\Field9 = $FF
        actors(local0)\Field10 = $C8
        actors(local0)\Field11 = $00
        actors(local0)\Field6 = $01
        actors(local0)\Field7 = $01
        entitytexture(actors(local0)\Field5, actordecals(actors(local0)\Field6), $00, $00)
        scalesprite(actors(local0)\Field5, (actors(local0)\Field53 * 1.25), (actors(local0)\Field54 * 1.25))
        entityorder(actors(local0)\Field5, ((local0 * $03) + $01))
        showentity(actors(local0)\Field5)
        positionentity(actors(local0)\Field5, 0.0, 0.0, 0.0, $00)
    EndIf
    If (arg4 = $03) Then
    EndIf
    If (arg5 = $01) Then
        actors(local0)\Field12 = $32
        actors(local0)\Field13 = $32
        actors(local0)\Field14 = $FF
        actors(local0)\Field9 = $FF
        actors(local0)\Field10 = $32
        actors(local0)\Field11 = $32
    EndIf
    If (numplayers = $02) Then
        actors(local0)\Field58 = (actors(local0)\Field58 * 2.0)
    EndIf
    actors(local0)\Field59 = actors(local0)\Field58
    If (specialmode = $00) Then
        Select profiles(currentprofile)\Field5
            Case $01
                local3 = 1.75
            Case $02
                local3 = 1.45
            Case $03
                local3 = 1.15
            Case $04
                local3 = 0.85
        End Select
    ElseIf (specialmode = $02) Then
        local3 = 2.0
    Else
        local3 = 1.3
    EndIf
    For local4 = $01 To $0A Step $01
        actors(local0)\Field76[local4] = (actors(local0)\Field76[local4] * local3)
    Next
    currentenemies = (currentenemies + $01)
    totalenemies = (totalenemies + $01)
    For local1 = $01 To $05 Step $01
        If (levels(currentlevel, currentevent)\Field22[local1] = arg0) Then
            currentenemiestype(local1) = (currentenemiestype(local1) + $01)
        EndIf
    Next
    Return local0
    Return $00
End Function
