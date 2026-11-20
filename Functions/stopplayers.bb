Function stopplayers%()
    Local local0%
    For local0 = $01 To $02 Step $01
        player(local0)\Field0 = $00
    Next
    Return $00
End Function
