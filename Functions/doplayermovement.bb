Function doplayermovement%(arg0.playertype)
    Local local0#
    Local local1%
    local0 = (entityx(arg0\Field2, $00) - arg0\Field4)
    If ((((0.0 <> wintime) And (240.0 > wintime)) Or (0.0 < gameovertime)) <> 0) Then
        local0 = 75.0
    EndIf
    If (((0.0 < arg0\Field33) And ((0.0 = wintime) And (0.0 = gameovertime))) <> 0) Then
        local1 = $01
        If (arg0 = player($02)) Then
            local1 = $02
        EndIf
        If (((0.0 < arg0\Field6) Or (0.0 > arg0\Field6)) <> 0) Then
            makeparticle(arg0\Field4, arg0\Field5, 0.0, 0.0, 0.5, 45.0, 45.0, ($42 + local1), 0.0, -0.033, 0.0, 0.0, 60.0, $00)
        EndIf
        arg0\Field33 = (arg0\Field33 - fl\Field1)
        If (0.0 > arg0\Field33) Then
            arg0\Field33 = 0.0
        EndIf
        If (((50.0 > local0) And (-50.0 < local0)) <> 0) Then
            local0 = 0.0
            arg0\Field6 = 0.0
        Else
            arg0\Field6 = (local0 / 3.0)
            If (50.0 < arg0\Field6) Then
                arg0\Field6 = 50.0
            EndIf
            If (-50.0 > arg0\Field6) Then
                arg0\Field6 = -50.0
            EndIf
            If (((0.0 < arg0\Field6) And (2.0 > arg0\Field6)) <> 0) Then
                arg0\Field6 = 2.0
            EndIf
            If (((0.0 > arg0\Field6) And (-2.0 < arg0\Field6)) <> 0) Then
                arg0\Field6 = -2.0
            EndIf
        EndIf
    ElseIf (((50.0 > local0) And (-50.0 < local0)) <> 0) Then
        local0 = 0.0
        arg0\Field6 = 0.0
    Else
        arg0\Field6 = (local0 / 20.0)
        If (5.0 < arg0\Field6) Then
            arg0\Field6 = 5.0
        EndIf
        If (-5.0 > arg0\Field6) Then
            arg0\Field6 = -5.0
        EndIf
        If (((0.0 < arg0\Field6) And (2.0 > arg0\Field6)) <> 0) Then
            arg0\Field6 = 2.0
        EndIf
        If (((0.0 > arg0\Field6) And (-2.0 < arg0\Field6)) <> 0) Then
            arg0\Field6 = -2.0
        EndIf
    EndIf
    arg0\Field4 = ((arg0\Field6 * fl\Field1) + arg0\Field4)
    If ((((400.0 < arg0\Field4) And (0.0 = wintime)) And (0.0 = gameovertime)) <> 0) Then
        arg0\Field4 = 400.0
    EndIf
    If (-400.0 > arg0\Field4) Then
        arg0\Field4 = -400.0
    EndIf
    animloops(actors(arg0\Field1)\Field22)\Field4 = (5.0 - (0.5 * arg0\Field6))
    Return $00
End Function
