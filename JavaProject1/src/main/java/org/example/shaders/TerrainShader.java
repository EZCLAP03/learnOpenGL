package org.example.shaders;

import org.example.entity.Camera;
import org.example.entity.Light;
import org.example.utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TerrainShader extends ShaderProgram {

    private static final String VERTEX_FILE = "C:/Users/haris/Desktop/OpenGL-Experiments/learnOpenGL/JavaProject1/src/main/java/org/example/shaders/terrainVertexShader.txt";
    private static final String FRAGMENT_FILE = "C:/Users/haris/Desktop/OpenGL-Experiments/learnOpenGL/JavaProject1/src/main/java/org/example/shaders/terrainFragmentShader.txt";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColour;

    public TerrainShader() {
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


    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadSkyColour(float r, float g, float b){
        super.loadVector(location_skyColour, new Vector3f(r, g, b));
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
