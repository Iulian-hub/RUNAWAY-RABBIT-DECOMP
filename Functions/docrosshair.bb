Function docrosshair%(arg0.playertype)
    Local local0#
    
    ; 1. Handle Special Game Mode 3
    If (gamemode = 3) Then
        positionentity(arg0\Field2, forcep1crosshairx, forcep1crosshairy, 0.0, 0)
        
        If (crosshairvisible = 1) Then
            If (arg0 = player(1)) Then
                entitycolor(arg0\Field2, p1crosshairR, p1crosshairG, p1crosshairB)
            EndIf
            If (arg0 = player(2)) Then
                entitycolor(arg0\Field2, p2crosshairR, p2crosshairG, p2crosshairB)
            EndIf
            showentity(arg0\Field2)
        Else
            hideentity(arg0\Field2)
        EndIf
        Return 0
    EndIf
    
    ; 2. Handle standard mouse/keyboard movement
    If (arg0\Field3 = 0 Or arg0\Field3 = 2) Then
        local0 = ((1.0 / 56.88889) * (Float graphicswidth()))
        If (arg0\Field3 = 0) Then
            movemouse((Int (((local0 * keyforcex) * fl\Field1) + (Float mousex()))), (Int (((local0 * keyforcey) * fl\Field1) + (Float mousey()))))
            to3d((Float mousex()), (Float mousey()), 0.0, 0.0)
            positionentity(arg0\Field2, x3d, y3d, 0.0, 0)
        Else
            movekeyboard((((local0 * keyforcex) * fl\Field1) + keyboardx), (((local0 * keyforcey) * fl\Field1) + keyboardy))
            to3d(keyboardx, keyboardy, 0.0, 0.0)
            positionentity(arg0\Field2, x3d, y3d, 0.0, 0)
        EndIf
    EndIf
    
    If ((arg0\Field3 = 0 Or arg0\Field3 = 1) And (msx <> 0.0 Or msy <> 0.0)) Then
        to3d((Float mousex()), (Float mousey()), 0.0, 0.0)
        positionentity(arg0\Field2, x3d, y3d, 0.0, 0)
    EndIf
    
    ; 3. Apply Visibility and Custom Colors
    If (crosshairvisible = 1) Then
        If (arg0 = player(1)) Then
            entitycolor(arg0\Field2, p1crosshairR, p1crosshairG, p1crosshairB)
        EndIf
        If (arg0 = player(2)) Then
            entitycolor(arg0\Field2, p2crosshairR, p2crosshairG, p2crosshairB)
        EndIf
        showentity(arg0\Field2)
    Else
        hideentity(arg0\Field2)
    EndIf

    ; Ensure crosshair hides during level transitions/game over
    If (gameovertime <> 0.0 Or wintime <> 0.0) Then
        hideentity(arg0\Field2)
    EndIf
    
    Return 0
End Function
