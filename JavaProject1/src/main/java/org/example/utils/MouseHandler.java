package org.example.utils;

import org.example.WindowManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class MouseHandler {
    private static double prevX = 0;
    private static double prevY = 0;

    private static double newX = 0;
    private static double newY = 0;

    private static double deltaX = 0;
    private static double deltaY = 0;

    public static void handleMouseEvents(){
        //Handle mouse movements
        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(WindowManager.getWindowID(), x, y);
        x.rewind();
        y.rewind();

        MouseHandler.newX = x.get();
        MouseHandler.newY = y.get();

        MouseHandler.deltaX = MouseHandler.newX - MouseHandler.prevX;
        MouseHandler.deltaY = MouseHandler.newY - MouseHandler.prevY;

        MouseHandler.prevX = MouseHandler.newX;
        MouseHandler.prevY = MouseHandler.newY;

        //Handle mouse scrolling

    }

    public static double getDeltaX() {
        return deltaX;
    }

    public static double getDeltaY() {
        return deltaY;
    }
}
