Function loadmenu%()
    Local root$ = CurrentDir$() + "\"
    Local local0%
    Local local1%
    Local local2#
    Local local3#
    Local local4%
    Local local5%
    Local local6%
    local0 = loadtexture(root$ + "gfx/menu/mouse.png", $03)
    menutex = loadtexture(root$ + "gfx/menu/menutex.bmp", $01)
    menutextures($32) = loadtexture(root$ + "gfx/blank.bmp", $04)
    menutextures($33) = loadtexture(root$ + "gfx/menu/menuborder.png", $13)
    menutextures($34) = loadtexture(root$ + "gfx/menu/menuborder.png", $03)
    rotatetexture(menutextures($34), 90.0)
    menutextures($35) = loadtexture(root$ + "gfx/menu/menucorner.png", $03)
    menutextures($36) = loadtexture(root$ + "gfx/menu/menucorner.png", $03)
    scaletexture(menutextures($36), -1.0, 1.0)
    menutextures($37) = loadtexture(root$ + "gfx/menu/menucorner.png", $03)
    scaletexture(menutextures($37), -1.0, -1.0)
    menutextures($38) = loadtexture(root$ + "gfx/menu/menucorner.png", $03)
    scaletexture(menutextures($38), 1.0, -1.0)
    menutextures($39) = loadtexture(root$ + "gfx/menu/menuborder.png", $03)
    rotatetexture(menutextures($39), 180.0)
    menutextures($3A) = loadtexture(root$ + "gfx/menu/menuborder.png", $13)
    rotatetexture(menutextures($3A), 270.0)
    menutextures($3B) = loadtexture(root$ + "gfx/menu/menuborderspecial.png", $03)
    menutextures($3C) = loadtexture(root$ + "gfx/menu/menuborderspecial.png", $03)
    rotatetexture(menutextures($3C), 90.0)
    menutextures($3D) = loadtexture(root$ + "gfx/menu/menuborderspecial.png", $03)
    rotatetexture(menutextures($3D), 180.0)
    menutextures($3E) = loadtexture(root$ + "gfx/menu/menuborderspecial.png", $03)
    rotatetexture(menutextures($3E), 270.0)
    For local1 = $01 To $64 Step $01
        If (menutexture(local1) <> "") Then
            menutextures(local1) = loadtexture(menutexture(local1), menutextureflags(local1))
        EndIf
    Next

    inject_options()

    autobordermenu()
    gui_setgroup($29A)
    menumouse = gui_createsprite(0.0, 0.0, 60.0, 60.0, 0.0, 0.0, 1.0, 1.0, $01)
    gui_objectorder(menumouse, $FFFE776A)
    hideentity(gui_getobjectentity(menumouse))
    entitytexture(gui_getobjectentity(menumouse), local0, $00, $00)
    entityfx(gui_getobjectentity(menumouse), $23)
    For local1 = $01 To $32 Step $01
        If (menus(local1)\Field0 = $01) Then
            local2 = (menus(local1)\Field4 / 20.0)
            local3 = (menus(local1)\Field5 / 20.0)
            menus(local1)\Field9 = gui_createsprite(menus(local1)\Field2, menus(local1)\Field3, menus(local1)\Field4, menus(local1)\Field5, 0.0, 0.0, local2, local3, $01)
            gui_objectorder(menus(local1)\Field9, ($FFFE7962 - (menus(local1)\Field13 * $64)))
            entitycolor(gui_getobjectentity(menus(local1)\Field9), 255.0, 255.0, 255.0)
            entitytexture(gui_getobjectentity(menus(local1)\Field9), menutex, $00, $00)
            entityfx(gui_getobjectentity(menus(local1)\Field9), $01)
            hideentity(gui_getobjectentity(menus(local1)\Field9))
            For local4 = $01 To $64 Step $01
                If (menus(local1)\Field14[local4] = $01) Then
                    gui_setfontheight((Float menus(local1)\Field48[local4]))
                    local5 = menus(local1)\Field18[local4]
                    local6 = menus(local1)\Field19[local4]
                    menus(local1)\Field25[local4] = gui_createtext((menus(local1)\Field16[local4] + menus(local1)\Field2), (menus(local1)\Field17[local4] + menus(local1)\Field3), menus(local1)\Field15[local4], local5, local6, (Float menus(local1)\Field49[local4]))
                    paintentity(gui_getobjectentity(menus(local1)\Field25[local4]), brushmain)
                    gui_objectorder(menus(local1)\Field25[local4], ($FFFE794B - (menus(local1)\Field13 * $64)))
                    If (((local1 = $0D) And (local4 = $07)) <> 0) Then
                        gui_objectorder(menus(local1)\Field25[local4], ($FFFE7961 - (menus(local1)\Field13 * $64)))
                    EndIf
                    hideentity(gui_getobjectentity(menus(local1)\Field25[local4]))
                EndIf
                If (menus(local1)\Field27[local4] = $01) Then
                    gui_setfontheight((Float menus(local1)\Field48[local4]))
                    local5 = menus(local1)\Field31[local4]
                    local6 = menus(local1)\Field32[local4]
                    menus(local1)\Field33[local4] = gui_createtext((menus(local1)\Field29[local4] + menus(local1)\Field2), (menus(local1)\Field30[local4] + menus(local1)\Field3), menus(local1)\Field28[local4], local5, local6, (Float menus(local1)\Field49[local4]))
                    paintentity(gui_getobjectentity(menus(local1)\Field33[local4]), brushmain)
                    gui_objectorder(menus(local1)\Field33[local4], ($FFFE794B - (menus(local1)\Field13 * $64)))
                    hideentity(gui_getobjectentity(menus(local1)\Field33[local4]))
                EndIf
                If (menus(local1)\Field34[local4] = $01) Then
                    local2 = 1.0
                    local3 = 1.0
                    menus(local1)\Field35[local4] = gui_createsprite((menus(local1)\Field39[local4] + menus(local1)\Field2), (menus(local1)\Field40[local4] + menus(local1)\Field3), menus(local1)\Field37[local4], menus(local1)\Field38[local4], 0.0, 0.0, local2, local3, $01)
                    gui_objectorder(menus(local1)\Field35[local4], ($FFFE794C - (menus(local1)\Field13 * $64)))
                    If (((local1 = $0D) And (local4 = $07)) <> 0) Then
                        gui_objectorder(menus(local1)\Field35[local4], ($FFFE7962 - (menus(local1)\Field13 * $0A)))
                    EndIf
                    entityblend(gui_getobjectentity(menus(local1)\Field35[local4]), $01)
                    paintentity(gui_getobjectentity(menus(local1)\Field35[local4]), brushmain)
                    entitycolor(gui_getobjectentity(menus(local1)\Field35[local4]), 128.0, 128.0, 128.0)
                    entityfx(gui_getobjectentity(menus(local1)\Field35[local4]), $21)
                    entitytexture(gui_getobjectentity(menus(local1)\Field35[local4]), menutextures(menus(local1)\Field36[local4]), $00, $00)
                    hideentity(gui_getobjectentity(menus(local1)\Field35[local4]))
                EndIf
            Next
            For local4 = $01 To $70 Step $01
                If (menus(local1)\Field51[local4] = $01) Then
                    local2 = 1.0
                    local3 = 1.0
                    If (0.0 < menus(local1)\Field60[local4]) Then
                        local2 = (menus(local1)\Field56[local4] / menus(local1)\Field60[local4])
                        local3 = (menus(local1)\Field57[local4] / menus(local1)\Field60[local4])
                    EndIf
                    menus(local1)\Field52[local4] = gui_createsprite((menus(local1)\Field54[local4] + menus(local1)\Field2), (menus(local1)\Field55[local4] + menus(local1)\Field3), menus(local1)\Field56[local4], menus(local1)\Field57[local4], 0.0, 0.0, local2, local3, $01)
                    gui_objectorder(menus(local1)\Field52[local4], (($FFFE7961 - (menus(local1)\Field13 * $64)) - (local4 Shl $00)))
                    entityblend(gui_getobjectentity(menus(local1)\Field52[local4]), $02)
                    paintentity(gui_getobjectentity(menus(local1)\Field52[local4]), brushmain)
                    entitycolor(gui_getobjectentity(menus(local1)\Field52[local4]), 255.0, 255.0, 255.0)
                    entityfx(gui_getobjectentity(menus(local1)\Field52[local4]), $31)
                    entitytexture(gui_getobjectentity(menus(local1)\Field52[local4]), menutextures(menus(local1)\Field53[local4]), $00, $00)
                    If (((local1 = $0A) And (local4 < $05)) <> 0) Then
                        rotateentity(gui_getobjectentity(menus(local1)\Field52[local4]), 0.0, 0.0, getangle(entityx(gui_getobjectentity(menus(local1)\Field35[local4]), $00), entityy(gui_getobjectentity(menus(local1)\Field35[local4]), $00), entityx(gui_getobjectentity(menus(local1)\Field35[(local4 + $01)]), $00), entityy(gui_getobjectentity(menus(local1)\Field35[(local4 + $01)]), $00)), $00)
                        entityfx(gui_getobjectentity(menus(local1)\Field52[local4]), $01)
                    EndIf
                    If (((local1 = $0A) And (local4 = $06)) <> 0) Then
                        rotateentity(gui_getobjectentity(menus(local1)\Field52[local4]), 0.0, 0.0, getangle(entityx(gui_getobjectentity(menus(local1)\Field35[$02]), $00), entityy(gui_getobjectentity(menus(local1)\Field35[$02]), $00), entityx(gui_getobjectentity(menus(local1)\Field35[local4]), $00), entityy(gui_getobjectentity(menus(local1)\Field35[local4]), $00)), $00)
                        entityfx(gui_getobjectentity(menus(local1)\Field52[local4]), $01)
                    EndIf
                    If (((local1 = $02) And (local4 = $02)) <> 0) Then
                        entityfx(gui_getobjectentity(menus(local1)\Field52[local4]), $21)
                        entityblend(gui_getobjectentity(menus(local1)\Field52[local4]), $03)
                    EndIf
                    hideentity(gui_getobjectentity(menus(local1)\Field52[local4]))
                EndIf
            Next
        EndIf
    Next
    gui_setgroup($EA)
    Return $00
End Function

Function inject_options%()
    ; This function is called from inside the main menu loop
    ; It handles the geometry and button setup safely
    
    ; 1. Expand background height
    menus(6)\Field5 = menus(6)\Field5 + 120.0 
    
    ; 2. Push "DONE" button down and dynamically grab its exact position
    Local doneY# = 120.0
    Local doneX# = 50.0 ; Default fallback
    Local i%
    
    For i = 1 To 60
        If (menus(6)\Field24[i] = "optionsback") Then
            doneX# = menus(6)\Field16[i] ; Capture the correct relative X position[cite: 1]
            doneY# = menus(6)\Field17[i] ; Capture the correct relative Y position[cite: 1]
            
            ; Push the DONE button down by 90 pixels to make room
            menus(6)\Field17[i] = doneY# + 90.0 
        EndIf
    Next
    
    ; 3. Inject new buttons (Slots 7, 8, 9) using the captured X coordinate
    ; Note: Do NOT add Field2 or Field3 here, loadmenu() does it automatically!
    
    ; AUTO-FIRE
    menus(6)\Field14[7] = 1 : menus(6)\Field16[7] = doneX# : menus(6)\Field17[7] = doneY#
    menus(6)\Field24[7] = "autofirebutton" 
    menus(6)\Field48[7] = 16 : menus(6)\Field49[7] = 1
    menus(6)\Field15[7] = "AUTO-FIRE: ON" 
    
    ; CROSSHAIR VISIBILITY
    menus(6)\Field14[8] = 1 : menus(6)\Field16[8] = doneX# : menus(6)\Field17[8] = doneY# + 30.0
    menus(6)\Field24[8] = "crossvisbutton"
    menus(6)\Field48[8] = 16 : menus(6)\Field49[8] = 1
    menus(6)\Field15[8] = "CROSSHAIR: VISIBLE"
    
    ; CROSSHAIR COLOR
    menus(6)\Field14[9] = 1 : menus(6)\Field16[9] = doneX# : menus(6)\Field17[9] = doneY# + 60.0
    menus(6)\Field24[9] = "crosscolorbutton"
    menus(6)\Field48[9] = 16 : menus(6)\Field49[9] = 1
    menus(6)\Field15[9] = "CROSS COLOR: GREEN"

    Return 0
End Function