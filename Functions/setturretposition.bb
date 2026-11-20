Function setturretposition%(arg0.actortype, arg1%)
    Local local0%
    For local0 = $01 To $0A Step $01
        Select arg0\Field25
            Case $01
                arg0\Field71[local0] = ((1.0 / 4.285714) * arg0\Field51)
                arg0\Field72[local0] = ((1.0 / 2.045455) * arg0\Field52)
            Case $02
                arg0\Field71[local0] = ((1.0 / 4.285714) * arg0\Field51)
                arg0\Field72[local0] = ((1.0 / 2.045455) * arg0\Field52)
            Case $03
                arg0\Field71[local0] = ((1.0 / 6.428571) * arg0\Field51)
                arg0\Field72[local0] = ((1.0 / 1.956522) * arg0\Field52)
            Case $04
                arg0\Field71[local0] = ((1.0 / 15.0) * arg0\Field51)
                arg0\Field72[local0] = ((1.0 / 1.875) * arg0\Field52)
            Case $05
                arg0\Field71[local0] = ((1.0 / 6.923077) * arg0\Field51)
                arg0\Field72[local0] = ((1.0 / 1.875) * arg0\Field52)
            Case $06
                arg0\Field71[local0] = ((1.0 / 6.923077) * arg0\Field51)
                arg0\Field72[local0] = ((1.0 / 1.764706) * arg0\Field52)
            Case $07
                arg0\Field71[local0] = ((1.0 / 8.181818) * arg0\Field51)
                arg0\Field72[local0] = ((1.0 / 1.698113) * arg0\Field52)
            Case $08
                arg0\Field71[local0] = (0.1 * arg0\Field51)
                arg0\Field72[local0] = ((1.0 / 1.836735) * arg0\Field52)
        End Select
        If (((arg1 = player($01)\Field1) And (player($01)\Field16 = $01)) <> 0) Then
            arg0\Field71[local0] = ((1.0 / 6.428571) * arg0\Field51)
            arg0\Field72[local0] = ((1.0 / 1.956522) * arg0\Field52)
        EndIf
        If (((arg1 = player($02)\Field1) And (player($02)\Field16 = $01)) <> 0) Then
            arg0\Field71[local0] = ((1.0 / 6.428571) * arg0\Field51)
            arg0\Field72[local0] = ((1.0 / 1.956522) * arg0\Field52)
        EndIf
    Next
    Return $00
End Function
