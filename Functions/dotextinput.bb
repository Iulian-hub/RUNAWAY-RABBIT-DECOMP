Function dotextinput$(arg0%, arg1%)
    Local local0$
    Local local1$
    Local local2%
    Local local3%
    local0 = ""
    local1 = ""
    If (((keydown($0E) And (deldown = $00)) And (textbufferchar > $01)) <> 0) Then
        deldown = $01
        textbufferchar = (textbufferchar - $01)
        textbuffer(textbufferchar) = ""
    Else
        local2 = getkey()
        local0 = upper(chr(local2))
        If (((local2 > $1F) And (((((((((((((((((((((((((((local0 = "A") Or (local0 = "B")) Or (local0 = "C")) Or (local0 = "D")) Or (local0 = "E")) Or (local0 = "F")) Or (local0 = "G")) Or (local0 = "H")) Or (local0 = "I")) Or (local0 = "J")) Or (local0 = "K")) Or (local0 = "L")) Or (local0 = "M")) Or (local0 = "N")) Or (local0 = "O")) Or (local0 = "P")) Or (local0 = "Q")) Or (local0 = "R")) Or (local0 = "S")) Or (local0 = "T")) Or (local0 = "U")) Or (local0 = "V")) Or (local0 = "W")) Or (local0 = "X")) Or (local0 = "Y")) Or (local0 = "Z")) Or (local0 = " "))) <> 0) Then
            If ((((local0 = lastkey) = $00) And (textbufferchar < (arg0 + $01))) <> 0) Then
                textbuffer(textbufferchar) = local0
                textbufferchar = (textbufferchar + $01)
            EndIf
        EndIf
        lastkey = local0
    EndIf
    If (keydown($0E) = $00) Then
        deldown = $00
    EndIf
    local1 = ""
    For local3 = $01 To arg0 Step $01
        local1 = (local1 + textbuffer(local3))
    Next
    If (arg1 = $01) Then
        local1 = (local1 + "|")
    EndIf
    local1 = trim(local1)
    flushkeys()
    Return local1
    Return ""
End Function
