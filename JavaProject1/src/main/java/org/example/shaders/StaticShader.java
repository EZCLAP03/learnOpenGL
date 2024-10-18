package org.example.shaders;

import org.example.entity.Camera;
import org.example.entity.Light;
import org.example.utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class StaticShader extends ShaderProgram {
    private static final String VERTEX_FILE = "C:/Users/haris/Desktop/OpenGL-Experiments/opengl game engine/JavaProject1/src/main/java/org/example/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "C:/Users/haris/Desktop/OpenGL-Experiments/opengl game engine/JavaProject1/src/main/java/org/example/shaders/fragmentShader.txt";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColour;
    private int location_useFakeLighting;
    private int location_numberOfRows;
    private int location_offset;

    public StaticShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightColour = super.getUniformLocation("lightColour");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_skyColour = super.getUniformLocation("skyColour");
        location_useFakeLighting = super.getUniformLocation("useFakeLighting");
        location_offset = super.getUniformLocation("offset");
        location_numberOfRows = super.getUniformLocation("numberOfRows");


    }

    public void loadNumberOfRows(int numberOfRows){
        super.loadFloat(location_numberOfRows, numberOfRows);
    }

    public void loadOffset(float x, float y){
        super.load2DVector(location_offset, new Vector2f(x,y));
    }



    public void loadUseFakeLighting(boolean useFakeLighting){
        super.loadBoolean(location_useFakeLighting, useFakeLighting);
    }
    public void loadSkyColour(float r, float g, float b){
        super.loadVector(location_skyColour, new Vector3f(r, g, b));
    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadLight(Light light){
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColour, light.getColor());
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public void loadviewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttributes(0, "position");
        super.bindAttributes(1, "textureCoords");
        super.bindAttributes(2, "normal");
    }
}
