Function loopstuff%()
    
    ; Graceful Exit: Save data before force-closing
    If ((KeyDown($38) Or KeyDown($B8)) And KeyDown($3E)) Then
        saveprofiles() 
        savescores()   
        End
    EndIf

    ; Escape Key Logic
    If KeyDown(1) Then
        If escapedown = 0 Then
            escapedown = 1
            If currentmenu = 0 And gamemode <> 3 And leveltime > 0 And gameovertime = 0 And wintime = 0 Then
                changemenu(1, 0.0, 0.0)
            ElseIf currentmenu = 1 Then
                changemenu(0, 0.0, 0.0)
            EndIf
        EndIf
    Else
        escapedown = 0
    EndIf
    
    ; --- NEW IN-GAME SETTINGS TOGGLES ---
    
    ; 'F' Key ($21): Toggle Auto-Fire
    If KeyDown($21) Then
        If togglefiredown = 0 Then
            togglefiredown = 1
            forcefireon = 1 - forcefireon ; Toggles between 1 and 0
        EndIf
    Else
        togglefiredown = 0
    EndIf

    ; 'V' Key ($2F): Toggle Crosshair Visibility
    If KeyDown($2F) Then
        If togglevisdown = 0 Then
            togglevisdown = 1
            crosshairvisible = 1 - crosshairvisible
        EndIf
    Else
        togglevisdown = 0
    EndIf

    ; 'B' Key ($30): Cycle Crosshair Color (Player 1)
    If KeyDown($30) Then
        If togglecolordown = 0 Then
            togglecolordown = 1
            If p1crosshairG = 255.0 Then
                ; Switch to Red
                p1crosshairR = 255.0 : p1crosshairG = 55.0 : p1crosshairB = 55.0
            ElseIf p1crosshairR = 255.0 Then
                ; Switch to Blue
                p1crosshairR = 55.0 : p1crosshairG = 150.0 : p1crosshairB = 255.0
            Else
                ; Switch back to Default Green
                p1crosshairR = 55.0 : p1crosshairG = 255.0 : p1crosshairB = 0.0
            EndIf
        EndIf
    Else
        togglecolordown = 0
    EndIf
    
    ; --- CHEAT KEYS (Protected by Shift) ---
    If (KeyDown($2A) Or KeyDown($36)) Then
        
        If KeyDown($2E) Then ; 'C' key for Cash
            If debugcashdown = 0 Then
                debugcashdown = 1
                If currentprofile > 0 Then
                    profiles(currentprofile)\Field6 = profiles(currentprofile)\Field6 + 10000 
                    saveprofiles()
                EndIf
            EndIf
        Else
            debugcashdown = 0
        EndIf
        
        If KeyDown($26) Then ; 'L' key for Level
            If debugleveldown = 0 Then
                debugleveldown = 1
                If currentprofile > 0 Then
                    profiles(currentprofile)\Field1 = profiles(currentprofile)\Field1 + 1
                    If profiles(currentprofile)\Field1 > 29 Then 
                        profiles(currentprofile)\Field1 = 29
                    EndIf
                    saveprofiles()
                EndIf
            EndIf
        Else
            debugleveldown = 0
        EndIf

    Else
        debugcashdown = 0
        debugleveldown = 0
    EndIf

    Return 0
End Function