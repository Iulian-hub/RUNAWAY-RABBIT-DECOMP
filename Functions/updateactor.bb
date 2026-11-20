Function updateactor%(arg0.actortype, arg1%)
    Local local0%
    Local local1#
    Local local2#
    Local local3#
    Local local4#
    Local local5%
    If (arg0\Field1 = $01) Then
        entityorder(arg0\Field42, (arg1 * $03))
    EndIf
    If ((((0.0 < wrapangle(arg0\Field50)) And (181.0 > wrapangle(arg0\Field50))) And (maingameview = $01)) <> 0) Then
        If (arg0\Field22 > $00) Then
            entitytexture(arg0\Field42, animloops(arg0\Field22)\Field1, (arg0\Field25 - $01), $00)
        ElseIf (((arg0\Field21 > $00) And actorgraphicsflipon(arg0\Field21)) <> 0) Then
            entitytexture(arg0\Field42, actorgraphicsflipped(arg0\Field21), $00, $00)
        EndIf
        For local0 = $01 To $0A Step $01
            If (arg0\Field62[local0] > $00) Then
                entitytexture(arg0\Field70[local0], turrettexturesflipped(arg0\Field62[local0]), $00, $00)
            EndIf
        Next
    Else
        If (arg0\Field21 > $00) Then
            entitytexture(arg0\Field42, actorgraphics(arg0\Field21), $00, $00)
        EndIf
        If (arg0\Field22 > $00) Then
            entitytexture(arg0\Field42, animloops(arg0\Field22)\Field0, (arg0\Field25 - $01), $00)
        EndIf
        If (maingameview = $01) Then
            For local0 = $01 To $0A Step $01
                If (arg0\Field62[local0] > $00) Then
                    entitytexture(arg0\Field70[local0], turrettextures(arg0\Field62[local0]), $00, $00)
                EndIf
            Next
        EndIf
    EndIf
    arg0\Field77 = (arg0\Field77 + fl\Field1)
    If (-1.0 < arg0\Field55) Then
        local1 = (255.0 / arg0\Field8)
        arg0\Field55 = (arg0\Field55 - (local1 * fl\Field1))
        If (0.0 > arg0\Field55) Then
            arg0\Field55 = -1.0
        EndIf
        local2 = ((Float (arg0\Field9 - arg0\Field12)) / 255.0)
        local3 = ((Float (arg0\Field10 - arg0\Field13)) / 255.0)
        local4 = ((Float (arg0\Field11 - arg0\Field14)) / 255.0)
        entitycolor(arg0\Field42, ((local2 * arg0\Field55) + (Float arg0\Field12)), ((local3 * arg0\Field55) + (Float arg0\Field13)), ((local4 * arg0\Field55) + (Float arg0\Field14)))
        For local0 = $01 To countchildren(arg0\Field42) Step $01
            entitycolor(getchild(arg0\Field42, local0), ((local2 * arg0\Field55) + (Float arg0\Field12)), ((local3 * arg0\Field55) + (Float arg0\Field13)), ((local4 * arg0\Field55) + (Float arg0\Field14)))
        Next
        If (maingameview = $01) Then
            For local0 = $01 To $0A Step $01
                entitycolor(arg0\Field70[local0], ((local2 * arg0\Field55) + (Float arg0\Field12)), ((local3 * arg0\Field55) + (Float arg0\Field13)), ((local4 * arg0\Field55) + (Float arg0\Field14)))
            Next
        EndIf
    EndIf
    arg0\Field59 = ((((arg0\Field58 / 6000.0) * arg0\Field60) * fl\Field1) + arg0\Field59)
    If (arg0\Field58 < arg0\Field59) Then
        arg0\Field59 = arg0\Field58
    EndIf
    positionentity(arg0\Field42, arg0\Field48, arg0\Field49, 0.0, $00)
    If (maingameview = $01) Then
        For local5 = $01 To $0A Step $01
            If (arg0\Field62[local5] > $00) Then
                If (((0.0 < wrapangle(arg0\Field50)) And (181.0 > wrapangle(arg0\Field50))) <> 0) Then
                    positionentity(arg0\Field70[local5], (arg0\Field48 - arg0\Field71[local5]), (arg0\Field72[local5] + arg0\Field49), 0.0, $00)
                Else
                    positionentity(arg0\Field70[local5], (arg0\Field71[local5] + arg0\Field48), (arg0\Field72[local5] + arg0\Field49), 0.0, $00)
                EndIf
            EndIf
        Next
    EndIf
    Return $00
End Function
