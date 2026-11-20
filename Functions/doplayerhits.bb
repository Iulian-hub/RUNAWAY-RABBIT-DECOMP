Function doplayerhits%(arg0.playertype)
    Local local0%
    Local local2%
    Local local3%
    arg0\Field19 = (((0.025 * fl\Field1) * (Float arg0\Field20)) + arg0\Field19)
    If (((0.6 > arg0\Field19) And (arg0\Field9 > $00)) <> 0) Then
        arg0\Field19 = 0.6
        arg0\Field20 = $01
    EndIf
    If (((1.0 < arg0\Field19) And (1.0 > arg0\Field12)) <> 0) Then
        arg0\Field19 = 1.0
        arg0\Field20 = $FFFFFFFF
    EndIf
    If (0.0 < (arg0\Field12 - 30.0)) Then
        arg0\Field19 = 2.0
        arg0\Field20 = $FFFFFFFF
    EndIf
    If (arg0\Field9 = $00) Then
        arg0\Field20 = $FFFFFFFF
    EndIf
    If (0.0 < arg0\Field12) Then
        actors(arg0\Field1)\Field59 = 1500000.0
        arg0\Field14 = ((((Float arg0\Field15) * fl\Field1) * 0.12) + arg0\Field14)
        If (((0.0 > arg0\Field14) And (arg0\Field15 = $FFFFFFFF)) <> 0) Then
            arg0\Field15 = $01
        EndIf
        If (((1.0 < arg0\Field14) And (arg0\Field15 = $01)) <> 0) Then
            arg0\Field15 = $FFFFFFFF
        EndIf
        arg0\Field12 = (arg0\Field12 - fl\Field1)
        If (0.0 > arg0\Field12) Then
            arg0\Field12 = 0.0
            arg0\Field15 = $00
            arg0\Field14 = 1.0
            actors(arg0\Field1)\Field59 = 1.0
        EndIf
    EndIf
    If (arg0\Field16 = $01) Then
        actors(arg0\Field1)\Field0 = $01
        actors(arg0\Field1)\Field59 = 15000000.0
        actors(arg0\Field1)\Field32 = $00
        actors(arg0\Field1)\Field68[$08] = $00
        arg0\Field17 = (arg0\Field17 - (fl\Field1 * 0.15))
        arg0\Field5 = ((fl\Field1 * arg0\Field17) + arg0\Field5)
        animloops(actors(arg0\Field1)\Field22)\Field4 = 15000000.0
        For local0 = $01 To $0A Step $01
            hideentity(actors(arg0\Field1)\Field70[local0])
            positionentity(actors(arg0\Field1)\Field70[local0], 99999.0, 99999.0, 0.0, $00)
        Next
        actors(arg0\Field1)\Field30 = $0A
        If (-1000.0 > arg0\Field5) Then
            If (arg0\Field10 > $00) Then
                arg0\Field10 = (arg0\Field10 - $01)
                arg0\Field0 = $00
                Select profiles(currentprofile)\Field5
                    Case $01
                        local2 = $04
                        local3 = $03
                    Case $02
                        local2 = $03
                        local3 = $02
                    Case $03
                        local2 = $02
                        local3 = $01
                    Case $04
                        local2 = $01
                        local3 = $01
                End Select
                If (specialmode <> $00) Then
                    local2 = playersetup($01)\Field0
                    local3 = playersetup($01)\Field3
                EndIf
                If (arg0 = player($01)) Then
                    If (numplayers = $01) Then
                        addplayer($01, -400.0, -250.0, $00, arg0\Field10, local2, local3, $00)
                    Else
                        addplayer($01, -400.0, -245.0, playersetup($01)\Field2, arg0\Field10, local2, local3, $00)
                    EndIf
                EndIf
                If (arg0 = player($02)) Then
                    addplayer($02, -400.0, -255.0, playersetup($02)\Field2, arg0\Field10, local2, local3, $00)
                EndIf
            Else
                arg0\Field0 = $00
            EndIf
        EndIf
    EndIf
    If (1.0 > actors(arg0\Field1)\Field59) Then
        actors(arg0\Field1)\Field59 = 1.0
        arg0\Field8 = (arg0\Field8 + $01)
        If (((0.0 <> gameovertime) Or (0.0 <> wintime)) <> 0) Then
            Return $00
        EndIf
        If (arg0\Field9 > $00) Then
            cameramain\Field7 = (cameramain\Field7 + 6.0)
            whiteflash(0.5, 0.05, 0.0, $01)
            arg0\Field9 = (arg0\Field9 - $01)
            If (soundon = $01) Then
                playsoundoveride(sndshield)
            EndIf
            arg0\Field12 = 120.0
            arg0\Field15 = $FFFFFFFF
        Else
            whiteflash(0.6, 0.05, 0.0, $01)
            arg0\Field16 = $01
            arg0\Field22 = rand($00, $07)
            arg0\Field17 = 7.0
            For local0 = $01 To $07 Step $01
                makeexplosion(((Float rand($FFFFFFD8, $28)) + arg0\Field4), ((Float rand($FFFFFFD8, $28)) + arg0\Field5), (Float rand($28, $50)), 0.0)
            Next
            makedust(arg0\Field4, arg0\Field5, $05)
            If (soundon = $01) Then
                playsoundoveride(snddie)
            EndIf
        EndIf
    EndIf
    Return $00
End Function
