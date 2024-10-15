package org.example.entity;

import org.example.WindowManager;
import org.example.terrains.Terrain;
import org.example.utils.Maths;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity{

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private static final float GRAVITY = -50f;
    private static final float JUMP_POWER = 30;

    private static final float TERRAIN_HEIGHT = 0;
    private boolean isInAir = false ;

    private float upwardsSpeed = 0;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        glfwSetInputMode(WindowManager.getWindowID(), GLFW_STICKY_KEYS, GLFW_TRUE);
    }

    public void move(Terrain terrain){

        checkInputs();
        float distance = currentSpeed * WindowManager.getFrameTimeSeconds();
        float dz = -(float)Math.cos(Math.toRadians(Camera.getYaw())) * distance;
        float dx = (float)Math.sin(Math.toRadians(Camera.getYaw())) * distance;
        super.increasePosition(dx, 0, dz);




        upwardsSpeed += GRAVITY * WindowManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * WindowManager.getFrameTimeSeconds(), 0);
        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        if(super.getPosition().y<terrainHeight){
            upwardsSpeed = 0;
            isInAir = false;
            getPosition().y = terrainHeight;
        }

    }

    private void jump(){
        if(!isInAir){this.upwardsSpeed = JUMP_POWER;}
        isInAir = true;
    }

    public void checkInputs(){

        if(glfwGetKey(WindowManager.getWindowID(), GLFW_KEY_W) == GLFW_PRESS){
            this.currentSpeed = RUN_SPEED;
        }
        else if(glfwGetKey(WindowManager.getWindowID(), GLFW_KEY_S) == GLFW_PRESS){
            this.currentSpeed = -RUN_SPEED;
        }
        else {
            this.currentSpeed = 0;
        }

        if(glfwGetKey(WindowManager.getWindowID(), GLFW_KEY_D) == GLFW_PRESS){
            super.increasePosition((float)Math.cos(Math.toRadians(Camera.getYaw())) * RUN_SPEED * WindowManager.getFrameTimeSeconds()
                    , 0, (float)Math.sin(Math.toRadians(Camera.getYaw())) * RUN_SPEED * WindowManager.getFrameTimeSeconds());
        }

        else if(glfwGetKey(WindowManager.getWindowID(), GLFW_KEY_A) == GLFW_PRESS){
            super.increasePosition(-(float)Math.cos(Math.toRadians(Camera.getYaw())) * RUN_SPEED * WindowManager.getFrameTimeSeconds()
                    , 0, -(float)Math.sin(Math.toRadians(Camera.getYaw())) * RUN_SPEED * WindowManager.getFrameTimeSeconds());
        }

        else{
            this.currentTurnSpeed = 0;
        }

        if(glfwGetKey(WindowManager.getWindowID(), GLFW_KEY_SPACE) == GLFW_PRESS){
            jump();
        }



    }

}
