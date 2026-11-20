Function dobackgroundenemies%()
    Local local0%
    Local local1#
    Local local2#
    Local local3#
    Local local4#
    Local local5#
    Local local6%
    Local local7.sprite
    local0 = (Int (levels(currentlevel, currentevent)\Field21 / fl\Field1))
    If (local0 < $02) Then
        local0 = ($02 And ((levels(currentlevel, currentevent)\Field20[$01] > $00) Or (levels(currentlevel, currentevent)\Field20[$02] > $00)))
    EndIf
    If (rand($01, local0) = $02) Then
        local1 = -650.0
        local2 = (Float rand($32, $E6))
        local3 = (Float rand($FFFFFDA8, $384))
        local4 = 16.0
        local5 = ((1.0 - (local3 / 2000.0)) * 1.0)
        local6 = makeparticle(local1, local2, local3, -90.0, 1.0, local4, (- local4), (levels(currentlevel, currentevent)\Field20[rand($01, $02)] + $32), local5, 0.0, 0.0, 0.0, (1400.0 / local5), $00)
        setsprite(local6, (1400.0 / local5), local1, local2, local3, local5, 0.0, 0.0, 180.0, local4, 0.0, (- local4), 0.0, 1.0, 0.0, $01)
        local7 = (Object.sprite local6)
        local7\Field12 = $64
        local7\Field13 = $CD
        local7\Field14 = $CD
    EndIf
    Return $00
End Function
