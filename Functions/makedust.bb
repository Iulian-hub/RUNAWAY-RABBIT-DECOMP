Function makedust%(arg0#, arg1#, arg2%)
    Local local0%
    For local0 = $01 To arg2 Step $01
        makeweaponshot(arg0, arg1, (Float rand($FFFFFFB0, $50)), $28, $00, $00, 0.0)
    Next
    Return $00
End Function
