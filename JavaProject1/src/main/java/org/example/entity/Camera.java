package org.example.entity;

import org.example.terrains.Terrain;
import org.example.utils.MouseHandler;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

import org.example.WindowManager;
import org.lwjgl.system.MemoryUtil;

import java.nio.DoubleBuffer;

public class Camera {

    private float distanceFromPlayer = 4;
    private float angleAroundPlayer = 0;
    private Vector3f position = new Vector3f(0, 10f, 10f);
    private float pitch = 20;
    private static float yaw = 0;
    private float roll;
    private Player player;
    private DoubleBuffer y = MemoryUtil.memAllocDouble(1);
    private DoubleBuffer x = MemoryUtil.memAllocDouble(1);;

    private WindowManager windowManager;

    public Camera(WindowManager windowManager, Player player) {
        this.player = player;
        this.windowManager = windowManager;
    }

    public void move() {
        glfwSetInputMode(WindowManager.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        boolean debug = false;
        if (glfwGetKey(windowManager.getWindowID(), GLFW_KEY_M) == 1 && !debug) {

            debug = true;
            if ((glfwGetKey(windowManager.getWindowID(), GLFW_KEY_R) == 1) && debug) {
                position.y += 0.02f;
            }
            if ((glfwGetKey(windowManager.getWindowID(), GLFW_KEY_F) == 1) && debug) {
                position.y -= 0.02f;
            }

        }
        if (glfwGetKey(windowManager.getWindowID(), GLFW_KEY_N) == 1) {
            debug = false;
        }

        glfwGetCursorPos(WindowManager.getWindowID(), x, y);
        calculatePitch(y);
        calculateYaw(x);
        calculateCameraPosition();
        x.clear();
        y.clear();

    }

    private void calculateCameraPosition() {
        position.x = player.getPosition().x;
        position.z = player.getPosition().z;
        position.y = player.getPosition().y + 10;

    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public static float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }


    private void calculatePitch(DoubleBuffer y) {

        System.out.println(pitch);
        float temp = (float) (y.get() * 0.1);
        pitch = Math.max(-90, Math.min(temp, 90));


    }

    private void calculateYaw(DoubleBuffer x) {
            double hi = x.get();
            yaw = (float)(hi * 0.1);
    }

    private void terrainCollider(Terrain terrain){

    }

}
