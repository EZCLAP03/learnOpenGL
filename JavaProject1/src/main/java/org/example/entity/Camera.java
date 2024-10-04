package org.example.entity;

import org.joml.Vector3f;

import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;
import org.example.WindowManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Camera {
    private Vector3f position = new Vector3f(0, 10f, 10f);
    private float pitch;
    private float yaw;
    private float roll;

    private WindowManager windowManager;
    public Camera(WindowManager windowManager) {

        this.windowManager = windowManager;
    }

    public void move(){
        if(glfwGetKey(windowManager.getWindowID(), GLFW_KEY_W) == 1){
            position.z-=0.02f;
        }

        if(glfwGetKey(windowManager.getWindowID(), GLFW_KEY_D) == 1){
            position.x+=0.02f;
        }

        if(glfwGetKey(windowManager.getWindowID(), GLFW_KEY_A) == 1){
            position.x-=0.02f;
        }
        if(glfwGetKey(windowManager.getWindowID(), GLFW_KEY_S) == 1){
            position.z+=0.02f;
        }

        boolean debug = false;
        if(glfwGetKey(windowManager.getWindowID(), GLFW_KEY_M) == 1 && !debug){

            debug = true;
            if((glfwGetKey(windowManager.getWindowID(), GLFW_KEY_R) == 1) && debug){
                position.y+=0.02f;
            }
            if((glfwGetKey(windowManager.getWindowID(), GLFW_KEY_F) == 1) && debug){
                position.y-=0.02f;
            }


        }
        if(glfwGetKey(windowManager.getWindowID(), GLFW_KEY_N) == 1){
            debug = false;
        }



    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
