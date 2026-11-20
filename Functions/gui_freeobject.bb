Function gui_freeobject%(arg0.gui_object)
    Local local0%
    Local local1%
    Local local2%
    Local local4.gui_databar
    Local local5.gui_dataarc
    Local local6.gui_text
    Local local7.gui_sprite
    local0 = arg0\Field0
    local1 = arg0\Field1
    local2 = gui_getobjectentity(arg0)
    freeentity(local2)
    Select local0
        Case $03
            local4 = (Object.gui_databar local1)
            Delete local4
        Case $04
            local5 = (Object.gui_dataarc local1)
            Delete local5
        Case $02
            local6 = (Object.gui_text local1)
            Delete local6
        Case $01
            local7 = (Object.gui_sprite local1)
            Delete local7
    End Select
    Delete arg0
    Return $00
End Function
