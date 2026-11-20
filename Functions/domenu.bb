Function domenu%()
    Local local0#
    Local local1%
    Local local2%
    Local local3%
    Local local4%
    Local local5%
    If (currentmenu = $00) Then
        hideentity(gui_getobjectentity(menumouse))
    Else
        showentity(gui_getobjectentity(menumouse))
        gui_positionobject(menumouse, (((Float mousex()) / (Float graphicswidth())) * 640.0), (((Float mousey()) / (Float graphicsheight())) * 480.0))
        If (((0.0 = msx) And (0.0 = msy)) <> 0) Then
            local0 = ((1.0 / 56.88889) * (Float graphicswidth()))
            movemouse((Int (((local0 * keyforcex) * fl\Field1) + (Float mousex()))), (Int (((local0 * keyforcey) * fl\Field1) + (Float mousey()))))
            movekeyboard((Float mousex()), (Float mousey()))
        EndIf
        gui_positionobject(menumouse, (((Float mousex()) / (Float graphicswidth())) * 640.0), (((Float mousey()) / (Float graphicsheight())) * 480.0))
    EndIf
    For local1 = $01 To $32 Step $01
        If (menus(local1)\Field0 <> 0) Then
            dospecialmenucode(local1)
            updatemenu(menus(local1))
        EndIf
    Next
    local2 = $00
    For local1 = $01 To $64 Step $01
        local3 = $00
        If (menus(currentmenu)\Field14[local1] = $01) Then
            If (gui_mouseover(menus(currentmenu)\Field25[local1]) <> 0) Then
                local3 = $01
            EndIf
        EndIf
        If (menus(currentmenu)\Field27[local1] = $01) Then
            If (gui_mouseover(menus(currentmenu)\Field33[local1]) <> 0) Then
                local3 = $01
            EndIf
        EndIf
        If (menus(currentmenu)\Field34[local1] = $01) Then
            If (gui_mouseover(menus(currentmenu)\Field35[local1]) <> 0) Then
                local3 = $01
            EndIf
        EndIf
        If (((((local3 = $01) And (menus(currentmenu)\Field50[local1] = $00)) And (menus(currentmenu)\Field47[local1] = $00)) And (menus(currentmenu)\Field26[local1] = $00)) <> 0) Then
            local2 = local1
            If (local1 <> currentmouseover) Then
                If (((soundon = $01) And (channelplaying(menuchannel) = $00)) <> 0) Then
                    menuchannel = playsound(local4)
                EndIf
            EndIf
        EndIf
    Next
    currentmouseover = local2
    If ((((fadingout = $00) And (menus(currentmenu)\Field8 = $00)) And ((((((mousedown($01) Or ((keydown(zkey) > $00) And (nokeys = $00))) Or ((joydown($01, $00) > $00) And (nokeys = $00))) Or ((keydown(enterkey) > $00) And (nokeys = $00))) Or ((keydown(spacekey) > $00) And (nokeys = $00))) And (mouseisdown = $00)) And (currentmouseover > $00))) <> 0) Then
        canfire = $00
        mouseisdown = $01
        menuevent(menus(currentmenu)\Field24[currentmouseover])
        If (menus(currentmenu)\Field44[currentmouseover] > $00) Then
            radiobuttonselected(menus(currentmenu)\Field44[currentmouseover]) = currentmouseover
        EndIf
        If (resetradio = $01) Then
            resetradio = $00
            For local5 = $01 To $0A Step $01
                radiobuttonselected(local5) = $00
            Next
        EndIf
        If (currentmouseover = $00) Then
            For local5 = $01 To $0A Step $01
                radiobuttonselected(local5) = $00
            Next
        EndIf
    EndIf
    If ((((((mousedown($01) = $00) And (keydown(zkey) = $00)) And (keydown(enterkey) = $00)) And (joydown($01, $00) = $00)) And (keydown(spacekey) = $00)) <> 0) Then
        mouseisdown = $00
    EndIf
    Return $00
End Function
