Function makeactor%(arg0#, arg1#, arg2#, arg3%, arg4%)
    Local local0%
    Local local1%
    Local local2%
    local0 = $00
    For local1 = $01 To $14 Step $01
        If (actors(local1)\Field41 = $00) Then
            local0 = local1
        EndIf
    Next
    If (local0 = $00) Then
        If (arg4 = $01) Then
            local0 = $01
        Else
            Return $00
        EndIf
    EndIf
    actors(local0)\Field41 = $01
    actors(local0)\Field3 = $00
    actors(local0)\Field1 = $01
    actors(local0)\Field23 = actortypes(arg3)\Field5
    If (actors(local0)\Field23 = $00) Then
        actors(local0)\Field23 = $FFFFFFFF
    EndIf
    actors(local0)\Field24 = $01
    actors(local0)\Field25 = $01
    actors(local0)\Field26 = 0.0
    actors(local0)\Field16 = actortypes(arg3)\Field2
    actors(local0)\Field2 = $00
    If (actortypes(arg3)\Field3 > $00) Then
        actors(local0)\Field2 = makeactor(arg0, arg1, arg2, actortypes(arg3)\Field3, $00)
        If (actors(local0)\Field2 = $00) Then
            actors(local0)\Field41 = $00
            Return $00
        EndIf
        actors(actors(local0)\Field2)\Field4 = $00
        actors(actors(local0)\Field2)\Field3 = local0
        actors(actors(local0)\Field2)\Field47 = 0.0
        actors(actors(local0)\Field2)\Field59 = actors(actors(local0)\Field2)\Field58
    EndIf
    actors(local0)\Field4 = $01
    actors(local0)\Field30 = actortypes(arg3)\Field49
    If (actors(local0)\Field3 = $00) Then
        currentfactioninplay(actors(local0)\Field30) = (currentfactioninplay(actors(local0)\Field30) + $01)
        totalfactioncreated(actors(local0)\Field30) = (totalfactioncreated(actors(local0)\Field30) + $01)
    EndIf
    actors(local0)\Field21 = actortypes(arg3)\Field16
    actors(local0)\Field22 = actortypes(arg3)\Field17
    For local2 = $01 To $0A Step $01
        actors(local0)\Field27[local2] = actortypes(arg3)\Field19[local2]
        actors(local0)\Field28[local2] = (Float actortypes(arg3)\Field20[local2])
        actors(local0)\Field29[local2] = (Float actortypes(arg3)\Field21[local2])
    Next
    actors(local0)\Field34 = 0.0
    actors(local0)\Field19 = $00
    actors(local0)\Field35 = (actortypes(arg3)\Field45 * 1.3)
    actors(local0)\Field36 = (Float rand((Int ((- actortypes(arg3)\Field47) / 2.0)), (Int (actortypes(arg3)\Field47 / 2.0))))
    actors(local0)\Field37 = (Float rand((Int ((- actortypes(arg3)\Field48) / 2.0)), (Int (actortypes(arg3)\Field48 / 2.0))))
    actors(local0)\Field43 = arg3
    actors(local0)\Field78 = $00
    actors(local0)\Field48 = arg0
    actors(local0)\Field49 = arg1
    actors(local0)\Field44 = 0.0
    actors(local0)\Field55 = 0.0
    actors(local0)\Field45 = 0.0
    actors(local0)\Field50 = arg2
    actors(local0)\Field51 = actortypes(arg3)\Field45
    actors(local0)\Field52 = actortypes(arg3)\Field46
    actors(local0)\Field53 = actortypes(arg3)\Field47
    actors(local0)\Field54 = actortypes(arg3)\Field48
    actors(local0)\Field15 = actortypes(arg3)\Field6
    actors(local0)\Field8 = actortypes(arg3)\Field7
    actors(local0)\Field9 = actortypes(arg3)\Field8
    actors(local0)\Field10 = actortypes(arg3)\Field9
    actors(local0)\Field11 = actortypes(arg3)\Field10
    actors(local0)\Field12 = actortypes(arg3)\Field11
    actors(local0)\Field13 = actortypes(arg3)\Field12
    actors(local0)\Field14 = actortypes(arg3)\Field13
    actors(local0)\Field47 = actortypes(arg3)\Field24
    actors(local0)\Field35 = actortypes(arg3)\Field23
    actors(local0)\Field58 = actortypes(arg3)\Field25
    actors(local0)\Field20 = actortypes(arg3)\Field18
    actors(local0)\Field80 = actortypes(arg3)\Field50
    For local1 = $01 To $0A Step $01
        actors(local0)\Field18[local1] = actortypes(arg3)\Field15[local1]
        actors(local0)\Field17[local1] = actortypes(arg3)\Field14[local1]
    Next
    actors(local0)\Field59 = actors(local0)\Field58
    actors(local0)\Field60 = actortypes(arg3)\Field26
    actors(local0)\Field31 = actortypes(arg3)\Field22
    For local1 = $01 To $0A Step $01
        If (actortypes(actors(local0)\Field43)\Field27[local1] <> 0) Then
            actors(local0)\Field61[local1] = actortypes(actors(local0)\Field43)\Field40[local1]
            actors(local0)\Field62[local1] = actortypes(actors(local0)\Field43)\Field29[local1]
            actors(local0)\Field63[local1] = actortypes(actors(local0)\Field43)\Field30[local1]
            actors(local0)\Field67[local1] = actortypes(actors(local0)\Field43)\Field31[local1]
            actors(local0)\Field68[local1] = actortypes(actors(local0)\Field43)\Field32[local1]
            actors(local0)\Field64[local1] = actortypes(actors(local0)\Field43)\Field28[local1]
            actors(local0)\Field65[local1] = actortypes(actors(local0)\Field43)\Field35[local1]
            actors(local0)\Field69[local1] = actortypes(actors(local0)\Field43)\Field33[local1]
            actors(local0)\Field66[local1] = actortypes(actors(local0)\Field43)\Field34[local1]
            actors(local0)\Field74[local1] = weaponshotkinds(actors(local0)\Field61[local1])\Field70
            If (0.0 < actortypes(actors(local0)\Field43)\Field37[local1]) Then
                actors(local0)\Field76[local1] = actortypes(actors(local0)\Field43)\Field37[local1]
            Else
                actors(local0)\Field76[local1] = 1.0
            EndIf
            actors(local0)\Field75[local1] = 0.0
            actors(local0)\Field75[local1] = 0.0
            actors(local0)\Field71[local1] = actortypes(actors(local0)\Field43)\Field36[local1]
            actors(local0)\Field72[local1] = actortypes(actors(local0)\Field43)\Field38[local1]
            actors(local0)\Field73[local1] = actortypes(actors(local0)\Field43)\Field39[local1]
            entityorder(actors(local0)\Field70[local1], ((local0 * $03) - $01))
            If (actors(local0)\Field64[local1] = $01) Then
                entityorder(actors(local0)\Field70[local1], ((local0 * $03) + $01))
            EndIf
            positionentity(actors(local0)\Field70[local1], actors(local0)\Field48, actors(local0)\Field49, 0.0, $00)
            scalesprite(actors(local0)\Field70[local1], actors(local0)\Field66[local1], actors(local0)\Field66[local1])
        Else
            actors(local0)\Field61[local1] = $00
            actors(local0)\Field62[local1] = $00
            actors(local0)\Field66[local1] = 0.0
            actors(local0)\Field67[local1] = $00
            actors(local0)\Field65[local1] = 0.0
            actors(local0)\Field74[local1] = 0.0
            actors(local0)\Field75[local1] = 0.0
            actors(local0)\Field71[local1] = 0.0
            actors(local0)\Field72[local1] = 0.0
            actors(local0)\Field73[local1] = 0.0
        EndIf
    Next
    actors(local0)\Field6 = actortypes(arg3)\Field0
    actors(local0)\Field7 = actortypes(arg3)\Field1
    If (actors(local0)\Field6 > $00) Then
        showentity(actors(local0)\Field5)
        entitytexture(actors(local0)\Field5, actordecals(actors(local0)\Field6), $00, $00)
        If (actors(local0)\Field7 = $01) Then
            entityorder(actors(local0)\Field5, ((local0 * $03) + $01))
        Else
            entityorder(actors(local0)\Field5, ((local0 * $03) - $01))
        EndIf
    Else
        hideentity(actors(local0)\Field5)
    EndIf
    scalesprite(actors(local0)\Field42, actors(local0)\Field51, actors(local0)\Field52)
    entitycolor(actors(local0)\Field42, (Float actors(local0)\Field12), (Float actors(local0)\Field13), (Float actors(local0)\Field14))
    For arg0 = 1.0 To (Float countchildren(actors(local0)\Field42)) Step 1.0
        entitycolor(getchild(actors(local0)\Field42, (Int arg0)), (Float actors(local0)\Field12), (Float actors(local0)\Field13), (Float actors(local0)\Field14))
    Next
    If (maingameview = $01) Then
        For arg0 = 1.0 To 10.0 Step 1.0
            entitycolor(actors(local0)\Field70[(Int arg0)], (Float actors(local0)\Field12), (Float actors(local0)\Field13), (Float actors(local0)\Field14))
        Next
    EndIf
    positionentity(actors(local0)\Field42, actors(local0)\Field48, actors(local0)\Field49, 0.0, $00)
    rotateentity(actors(local0)\Field42, 0.0, 0.0, arg2, $00)
    rotatesprite(actors(local0)\Field42, (- arg2))
    showentity(actors(local0)\Field42)
    If (actors(local0)\Field21 > $00) Then
        entitytexture(actors(local0)\Field42, actorgraphics(actors(local0)\Field21), $00, $00)
    EndIf
    If (actors(local0)\Field22 > $00) Then
        entitytexture(actors(local0)\Field42, animloops(actors(local0)\Field22)\Field0, (actors(local0)\Field25 - $01), $00)
    EndIf
    For local2 = $01 To $0A Step $01
        If (actors(local0)\Field62[local2] > $00) Then
            entitytexture(actors(local0)\Field70[local2], turrettextures(actors(local0)\Field62[local2]), $00, $00)
            If (maingameview <> $01) Then
                positionentity(actors(local0)\Field70[local2], actors(local0)\Field71[local2], actors(local0)\Field72[local2], 0.0, $00)
            EndIf
            showentity(actors(local0)\Field70[local2])
        Else
            hideentity(actors(local0)\Field70[local2])
            scalesprite(actors(local0)\Field70[local2], 0.0, 0.0)
        EndIf
    Next
    actors(local0)\Field77 = basetimealive
    Return local0
    Return $00
End Function
