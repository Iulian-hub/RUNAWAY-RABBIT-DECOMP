Function defaultscores%()
    Local local0%
    For local0 = $01 To $0A Step $01
        highscores(local0)\Field0 = "JAGGED BLADE"
        highscores(local0)\Field1 = (($0B - local0) * $C350)
    Next
    highscores($01)\Field0 = "NEIL YATES"
    highscores($02)\Field0 = "MARTIN REBAS"
    highscores($03)\Field0 = "STAFFAN MELIN"
    highscores($04)\Field0 = "KYRI SHERIDAN"
    highscores($05)\Field0 = "IAN YATES"
    highscores($06)\Field0 = "KEVIN PHELAN"
    highscores($07)\Field0 = "LAURIE GLOVER"
    highscores($08)\Field0 = "GARY CAMPBELL"
    highscores($09)\Field0 = "JOHN SMITH"
    highscores($0A)\Field0 = "JOE BLOGGS"
    highscores($01)\Field1 = $8F7B4
    highscores($02)\Field1 = $6FA54
    highscores($03)\Field1 = $70CB0
    highscores($04)\Field1 = $5FF8C
    highscores($05)\Field1 = $3BE34
    highscores($06)\Field1 = $30638
    highscores($07)\Field1 = $205F8
    highscores($08)\Field1 = $16ECC
    highscores($09)\Field1 = $1110C
    highscores($0A)\Field1 = $A604
    Return $00
End Function
