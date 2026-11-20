Function clearinputbuffer%()
    Local local0%
    For local0 = $01 To $64 Step $01
        textbuffer(local0) = ""
        textbufferchar = $01
    Next
    Return $00
End Function
