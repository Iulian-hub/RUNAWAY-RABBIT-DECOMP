Function makescoreflyup%(arg0#, arg1#, arg2$, arg3%, arg4%, arg5%, arg6%, arg7%, arg8#, arg9%, arg10#, arg11#, arg12#, arg13%)
    Local local0%
    Local local1%
    Local local2#
    Local local3#
    If (((((350.0 < arg0) Or (-350.0 > arg0)) Or (270.0 < arg1)) Or (-270.0 > arg1)) <> 0) Then
        Return $00
    EndIf
    For local0 = $01 To $0A Step $01
        If (scoreflyups(local0)\Field5 = $00) Then
            local1 = local0
        EndIf
    Next
    If (local1 = $00) Then
        If (arg13 = $00) Then
            Return $00
        EndIf
        local1 = rand($01, $0A)
        scoreflyups(local1)\Field5 = $00
        gui_freeobject(scoreflyupobjects(local1))
    EndIf
    gui_setfontheight((Float arg6))
    scoreflyupobjects(local1) = gui_createtext(0.0, 0.0, arg2, $01, $01, 65535.0)
    paintentity(gui_getobjectentity(scoreflyupobjects(local1)), brushmain)
    entitycolor(gui_getobjectentity(scoreflyupobjects(local1)), (Float arg3), (Float arg4), (Float arg5))
    scoreflyups(local1)\Field5 = $01
    scoreflyups(local1)\Field3 = arg11
    scoreflyups(local1)\Field4 = arg10
    scoreflyups(local1)\Field0 = arg0
    scoreflyups(local1)\Field12 = arg9
    scoreflyups(local1)\Field13 = arg12
    scoreflyups(local1)\Field9 = arg3
    scoreflyups(local1)\Field10 = arg4
    scoreflyups(local1)\Field11 = arg5
    scoreflyups(local1)\Field1 = arg1
    scoreflyups(local1)\Field2 = 0.0
    scoreflyups(local1)\Field6 = arg8
    scoreflyups(local1)\Field7 = arg8
    scoreflyups(local1)\Field8 = arg7
    cameraproject(cameramain\Field0, scoreflyups(local1)\Field0, scoreflyups(local1)\Field1, 0.0)
    local2 = projectedx()
    local3 = projectedy()
    gui_positionobject2(scoreflyupobjects(local1), gui_projectedx(local2), gui_projectedy(local3), scoreflyups(local1)\Field2)
    Return $00
End Function
