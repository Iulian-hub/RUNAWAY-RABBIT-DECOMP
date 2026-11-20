Function gui_getobjectentity%(arg0.gui_object)
    Local local0%
    Local local1%
    Local local3.gui_databar
    Local local4%
    Local local5.gui_dataarc
    Local local6.gui_text
    Local local7.gui_sprite
    local0 = arg0\Field0
    local1 = arg0\Field1
    Select local0
        Case $03
            local3 = (Object.gui_databar local1)
            local4 = local3\Field0
        Case $04
            local5 = (Object.gui_dataarc local1)
            local4 = local5\Field0
        Case $02
            local6 = (Object.gui_text local1)
            local4 = local6\Field0
        Case $01
            local7 = (Object.gui_sprite local1)
            local4 = local7\Field0
    End Select
    Return local4
    Return $00
End Function
