Function loadcamera%()
    Local local0#
    cameramain\Field0 = createcamera($00)
    cameramain\Field5 = createpivot(cameramain\Field0)
    cameramain\Field6 = 4.5
    positionentity(cameramain\Field0, 0.0, 0.0, -1500.0, $00)
    gui_camera_position_x = 0.0
    gui_camera_position_y = 0.0
    gui_camera_position_z = -1800.0
    local0 = cameramain\Field6
    positionentity(mathent, 0.0, 0.0, 0.0, $00)
    pointentity(cameramain\Field0, mathent, 0.0)
    cameraviewport(cameramain\Field0, $00, $00, graphicswidth(), graphicsheight())
    camerarange(cameramain\Field0, 1000.0, 60000.0)
    camerazoom(cameramain\Field0, 4.5)
    init2din3d(cameramain\Field0, 1800.0, $00, $00)
    camerazoom(cameramain\Field0, cameramain\Field6)
    gui_correctcamerafov(cameramain\Field0)
    docamera()
    Return $00
End Function
