Function loadactorgraphics%()
    Local local0%
    For local0 = $01 To $14 Step $01
        actorgraphics(local0) = loadtexture(actorgraphicpath(local0), $02)
        rotatetexture(actorgraphics(local0), (Float globalactorrotation))
        scaletexture(actorgraphics(local0), -1.0, 1.0)
        If (actorgraphicsflipon(local0) = $01) Then
            actorgraphicsflipped(local0) = loadtexture(actorgraphicpath(local0), $02)
            rotatetexture(actorgraphicsflipped(local0), (Float globalactorrotation))
            scaletexture(actorgraphicsflipped(local0), -1.0, -1.0)
        EndIf
    Next
    For local0 = $01 To $0A Step $01
        turrettextures(local0) = loadtexture(turretgraphicpath(local0), $02)
        actordecals(local0) = loadtexture(actordecalspath(local0), $33)
    Next
    For local0 = $01 To $0A Step $01
        turrettexturesflipped(local0) = loadtexture(turretgraphicpath(local0), $02)
        scaletexture(turrettexturesflipped(local0), -1.0, 1.0)
    Next
    Return $00
End Function
