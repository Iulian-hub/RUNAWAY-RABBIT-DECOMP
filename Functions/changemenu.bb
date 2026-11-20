Function changemenu%(arg0%, arg1#, arg2#)
    Local local0%
    Local local1%
    Local local2%
    Local local3%
    resettextscroll()
    clearinputbuffer()
    For local0 = $01 To $0A Step $01
        radiobuttonselected(local0) = $00
    Next
    currentmouseover = $00
    If (((currentmenu > $00) And (menus(arg0)\Field13 = $00)) <> 0) Then
        menus(currentmenu)\Field8 = $FFFFFFFF
        menus(currentmenu)\Field12 = arg1
        If (0.0 > arg1) Then
            menus(currentmenu)\Field6 = 0.0
            menus(currentmenu)\Field7 = 0.0
            menus(currentmenu)\Field12 = 0.0
            For local1 = $01 To $70 Step $01
                menus(currentmenu)\Field59[local1] = 0.0
            Next
        EndIf
    EndIf
    If (((arg0 > $00) And (menus(currentmenu)\Field13 = $00)) <> 0) Then
        showentity(gui_getobjectentity(menus(arg0)\Field9))
        menus(arg0)\Field6 = 0.0
        menus(arg0)\Field7 = 0.0
        menus(arg0)\Field8 = $01
        menus(arg0)\Field12 = arg2
    EndIf
    For local2 = $01 To $64 Step $01
        If (((menus(currentmenu)\Field14[local2] And (currentmenu > $00)) And (menus(arg0)\Field13 = $00)) <> 0) Then
        EndIf
        If (((menus(arg0)\Field14[local2] And (arg0 > $00)) And (menus(currentmenu)\Field13 = $00)) <> 0) Then
            showentity(gui_getobjectentity(menus(arg0)\Field25[local2]))
            If (menus(arg0)\Field47[local2] <> 0) Then
                menus(arg0)\Field20[local2] = 150.0
                menus(arg0)\Field21[local2] = 0.0
                menus(arg0)\Field22[local2] = 0.0
            Else
                menus(arg0)\Field20[local2] = 150.0
                menus(arg0)\Field21[local2] = 220.0
                menus(arg0)\Field22[local2] = 220.0
            EndIf
            If (menus(arg0)\Field50[local2] = $01) Then
                menus(arg0)\Field20[local2] = 255.0
                menus(arg0)\Field21[local2] = 255.0
                menus(arg0)\Field22[local2] = 0.0
                menus(arg0)\Field41[local2] = 255.0
                menus(arg0)\Field42[local2] = 255.0
                menus(arg0)\Field43[local2] = 255.0
            EndIf
        EndIf
        If (((menus(arg0)\Field27[local2] And (arg0 > $00)) And (menus(currentmenu)\Field13 = $00)) <> 0) Then
            showentity(gui_getobjectentity(menus(arg0)\Field33[local2]))
        EndIf
        If (((menus(arg0)\Field34[local2] And (arg0 > $00)) And (menus(currentmenu)\Field13 = $00)) <> 0) Then
            showentity(gui_getobjectentity(menus(arg0)\Field35[local2]))
            menus(arg0)\Field41[local2] = 128.0
            menus(arg0)\Field42[local2] = 128.0
            menus(arg0)\Field43[local2] = 128.0
        EndIf
    Next
    For local2 = $01 To $70 Step $01
        If (((menus(arg0)\Field51[local2] And (arg0 > $00)) And (menus(currentmenu)\Field13 = $00)) <> 0) Then
            If (local3 = $01) Then
                menus(arg0)\Field59[local2] = 1.0
            Else
                menus(arg0)\Field59[local2] = 0.0
            EndIf
            showentity(gui_getobjectentity(menus(arg0)\Field52[local2]))
        EndIf
    Next
    If (((arg0 = $00) And (menus(currentmenu)\Field10 = $00)) <> 0) Then
    EndIf
    If (((arg0 = $00) And (menus(currentmenu)\Field10 > $00)) <> 0) Then
        showhud(60.0)
    EndIf
    If ((((arg0 > $00) And ((menus(currentmenu)\Field10 = $00) Or (currentmenu = $00))) And (menus(arg0)\Field10 = $01)) <> 0) Then
        hidehud(60.0)
    EndIf
    currentmenu = arg0
    Return $00
End Function
