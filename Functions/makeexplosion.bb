Function makeexplosion%(arg0#, arg1#, arg2#, arg3#)
    Local local0%
    Local local1%
    Local local2#
    Local local3.sprite
    Local local4.sprite
    If (100.0 < arg2) Then
        makeexplosion(((Float rand((Int (- (arg2 / 10.0))), (Int (arg2 / 10.0)))) + ((arg2 / 5.0) + arg0)), ((Float rand((Int (- (arg2 / 10.0))), (Int (arg2 / 10.0)))) + (arg1 - (arg2 / 5.0))), (arg2 / 2.0), arg3)
        makeexplosion(((Float rand((Int (- (arg2 / 10.0))), (Int (arg2 / 10.0)))) + ((arg2 / 5.0) + arg0)), ((Float rand((Int (- (arg2 / 10.0))), (Int (arg2 / 10.0)))) + ((arg2 / 5.0) + arg1)), (arg2 / 2.0), arg3)
        makeexplosion(((Float rand((Int (- (arg2 / 10.0))), (Int (arg2 / 10.0)))) + (arg0 - (arg2 / 5.0))), ((Float rand((Int (- (arg2 / 10.0))), (Int (arg2 / 10.0)))) + (arg1 - (arg2 / 5.0))), (arg2 / 2.0), arg3)
        makeexplosion(((Float rand((Int (- (arg2 / 10.0))), (Int (arg2 / 10.0)))) + (arg0 - (arg2 / 5.0))), ((Float rand((Int (- (arg2 / 10.0))), (Int (arg2 / 10.0)))) + ((arg2 / 5.0) + arg1)), (arg2 / 2.0), arg3)
        Return $00
    ElseIf (1.0 = arg3) Then
        cameramain\Field7 = ((arg2 / 15.0) + cameramain\Field7)
    EndIf
    local0 = $00
    For local1 = $01 To $64 Step $01
        If (0.0 = explosions(local1)\Field1) Then
            local0 = local1
        EndIf
    Next
    If (local0 = $00) Then
        Return $00
    EndIf
    If (0.0 < arg2) Then
        If (rand($01, $02) = $01) Then
            If ((((((((soundon = $01) And (0.0 = local2)) And (30.0 < arg2)) And (gamesizex > arg0)) And ((- gamesizex) < arg0)) And (gamesizey > arg1)) And ((- gamesizey) < arg1)) <> 0) Then
                playsoundoveride(sndexplo1)
            EndIf
        ElseIf ((((((((soundon = $01) And (0.0 = local2)) And (30.0 < arg2)) And (gamesizex > arg0)) And ((- gamesizex) < arg0)) And (gamesizey > arg1)) And ((- gamesizey) < arg1)) <> 0) Then
            playsoundoveride(sndexplo2)
        EndIf
    EndIf
    explosions(local0)\Field1 = 1.0
    explosions(local0)\Field3 = rand($01, $02)
    explosions(local0)\Field2 = arg2
    explosions(local0)\Field6 = $00
    explosions(local0)\Field5 = $00
    explosions(local0)\Field7 = $00
    explosions(local0)\Field5 = makeparticle(arg0, arg1, 0.0, 0.0, 1.0, explosions(local0)\Field2, explosions(local0)\Field2, $08, 0.0, 0.0, 0.0, 0.0, 60.0, $01)
    local3 = (Object.sprite explosions(local0)\Field5)
    local3\Field24 = $01
    If (30.0 > explosions(local0)\Field2) Then
    EndIf
    If (1000.0 < arg2) Then
    EndIf
    explosions(local0)\Field0 = makeparticle(arg0, arg1, 0.0, 0.0, 0.9, explosions(local0)\Field2, explosions(local0)\Field2, $28, 0.0, 0.0, 0.0, 0.0, 60.0, $00)
    local4 = (Object.sprite explosions(local0)\Field0)
    local4\Field24 = $01
    Return $00
End Function
