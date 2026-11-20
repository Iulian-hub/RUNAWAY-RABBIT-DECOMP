Function zeroenemiesandbkgs%(arg0.levelevent)
    Local local0%
    For local0 = $01 To $05 Step $01
        arg0\Field5[local0] = $00
        arg0\Field6[local0] = 0.0
        arg0\Field7[local0] = 0.0
        arg0\Field8[local0] = 0.0
        arg0\Field9[local0] = 0.0
        arg0\Field10[local0] = 0.0
        arg0\Field11[local0] = $00
        arg0\Field22[local0] = $00
        arg0\Field23[local0] = 0.0
        arg0\Field24[local0] = $00
    Next
    arg0\Field12[$01] = $00
    arg0\Field13[$01] = 0.0
    arg0\Field14[$01] = 0.0
    For local0 = $01 To $06 Step $01
        arg0\Field15[local0] = $00
        arg0\Field16[local0] = $00
        arg0\Field17[local0] = $00
        arg0\Field18[local0] = 0.0
        arg0\Field19[local0] = 0.0
        arg0\Field20[local0] = $00
    Next
    arg0\Field21 = 0.0
    Return $00
End Function
