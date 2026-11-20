Function doactormovement%(arg0.actortype, arg1%)
    If (0.0 < arg0\Field46) Then
        rotateentity(arg0\Field42, 0.0, 0.0, (- arg0\Field50), $00)
        rotatesprite(arg0\Field42, (- arg0\Field50))
        tformnormal(0.0, 1.0, 0.0, arg0\Field42, $00)
        arg0\Field44 = (arg0\Field46 * tformedx())
        arg0\Field45 = (arg0\Field46 * tformedy())
        translateentity(arg0\Field42, (arg0\Field44 * fl\Field1), (arg0\Field45 * fl\Field1), 0.0, $00)
        arg0\Field48 = entityx(arg0\Field42, $00)
        arg0\Field49 = entityy(arg0\Field42, $00)
    Else
        rotateentity(arg0\Field42, 0.0, 0.0, (- arg0\Field50), $00)
        rotatesprite(arg0\Field42, (- arg0\Field50))
    EndIf
    Return $00
End Function
