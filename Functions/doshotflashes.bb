Function doshotflashes%()
    Local local0%
    Local local1#
    Local local2#
    Local local3#
    Local local4#
    Local local5#
    Local local6#
    For local0 = $01 To $14 Step $01
        If (0.0 < shotflashes(local0)\Field3) Then
            local1 = entityx(shotflashes(local0)\Field6, $01)
            local2 = entityy(shotflashes(local0)\Field6, $01)
            rotateentity(mathent, entitypitch(shotflashes(local0)\Field6, $00), entityyaw(shotflashes(local0)\Field6, $00), ((- entityroll(shotflashes(local0)\Field6, $00)) + (Float shotflashes(local0)\Field7)), $00)
            tformnormal(0.0, 1.0, 0.0, mathent, $00)
            local3 = (shotflashes(local0)\Field5 * tformedy())
            local4 = (shotflashes(local0)\Field5 * tformedx())
            tformnormal(1.0, 0.0, 0.0, mathent, $00)
            local5 = (shotflashes(local0)\Field4 * tformedy())
            local6 = (shotflashes(local0)\Field4 * tformedx())
            positionentity(shotflashes(local0)\Field0, ((local1 + local4) + local6), ((local2 + local3) + local5), 0.0, $00)
            shotflashes(local0)\Field3 = (shotflashes(local0)\Field3 - ((fl\Field1 * shotflashes(local0)\Field2) * 3.0))
            entityalpha(shotflashes(local0)\Field0, shotflashes(local0)\Field3)
            If (0.1 > shotflashes(local0)\Field3) Then
                shotflashes(local0)\Field3 = 0.0
                hideentity(shotflashes(local0)\Field0)
            EndIf
        EndIf
    Next
    Return $00
End Function
