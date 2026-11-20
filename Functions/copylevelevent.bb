Function copylevelevent%(arg0.levelevent, arg1.levelevent)
    Local local0%
    arg0\Field0 = arg1\Field0
    arg0\Field1 = arg1\Field1
    arg0\Field2 = arg1\Field2
    arg0\Field3 = arg1\Field3
    arg0\Field4 = arg1\Field4
    For local0 = $01 To $05 Step $01
        arg0\Field5[local0] = arg1\Field5[local0]
        arg0\Field6[local0] = arg1\Field6[local0]
        arg0\Field7[local0] = arg1\Field7[local0]
        arg0\Field8[local0] = arg1\Field8[local0]
        arg0\Field9[local0] = arg1\Field9[local0]
        arg0\Field10[local0] = arg1\Field10[local0]
        arg0\Field11[local0] = arg1\Field11[local0]
        arg0\Field22[local0] = arg1\Field22[local0]
        arg0\Field23[local0] = arg1\Field23[local0]
        arg0\Field24[local0] = arg1\Field24[local0]
    Next
    arg0\Field12[$01] = arg1\Field12[$01]
    arg0\Field13[$01] = arg1\Field13[$01]
    arg0\Field14[$01] = arg1\Field14[$01]
    For local0 = $01 To $06 Step $01
        arg0\Field15[local0] = arg1\Field15[local0]
        arg0\Field16[local0] = arg1\Field16[local0]
        arg0\Field17[local0] = arg1\Field17[local0]
        arg0\Field18[local0] = arg1\Field18[local0]
        arg0\Field19[local0] = arg1\Field19[local0]
        arg0\Field20[local0] = arg1\Field20[local0]
    Next
    arg0\Field21 = arg1\Field21
    arg0\Field25 = arg1\Field25
    arg0\Field26 = arg1\Field26
    arg0\Field27 = arg1\Field27
    arg0\Field28 = arg1\Field28
    arg0\Field29 = arg1\Field29
    arg0\Field30 = arg1\Field29
    Return $00
End Function
