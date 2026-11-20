Function gui_initialize%()
    gui_camera = createcamera($00)
    cameraviewport(gui_camera, $00, $00, graphicswidth(), graphicsheight())
    camerazoom(gui_camera, gui_camerazoom)
    cameraclsmode(gui_camera, $00, $01)
    camerarange(gui_camera, 1.0, (1024.0 * gui_camerazoom))
    camerafogmode(gui_camera, $00)
    positionentity(gui_camera, gui_camera_position_x, gui_camera_position_y, gui_camera_position_z, $01)
    gui_correctcamerafov(gui_camera)
    Return $00
End Function
