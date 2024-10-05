package org.example;

import org.example.entity.Camera;
import org.example.entity.Entity;
import org.example.entity.Light;
import org.example.entity.TexturedModel;
import org.example.shaders.StaticShader;
import org.example.shaders.TerrainShader;
import org.example.terrains.Terrain;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {


    private static final float FOV = 100;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1100.0f;
    private static final float RED = 0f;
    private static final float GREEN = 0f;
    private static final float BLUE = 0f;
    private static final float aspectRatio = (float) 1.8;




    private StaticShader shader = new StaticShader();
    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();

    private Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
    private List<Terrain> terrains = new ArrayList<Terrain>();
    private RenderManager renderer;

    public MasterRenderer() {
        enableCulling();
        Matrix4f projectionMatrix = createProjectionMatrix();
        System.out.println(projectionMatrix);
        renderer = new RenderManager(shader, projectionMatrix);
        Matrix4f projectionMatrix1 = createProjectionMatrix();
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix1);
    }

    public void render(Light sun, Camera camera){
        init();
        shader.start();
        shader.loadSkyColour(RED, GREEN, BLUE);
        shader.loadLight(sun);
        shader.loadviewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        terrainShader.start();
        terrainShader.loadSkyColour(RED, GREEN, BLUE);
        terrainShader.loadLight(sun);
        terrainShader.loadviewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        entities.clear();
        terrains.clear();

    }

    public void processTerrain(Terrain terrain){
        terrains.add(terrain);
    }

    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch != null){
            batch.add(entity);
        }
        else{
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }
    public void cleanUp(){
        shader.cleanUp();
        terrainShader.cleanUp();
    }

    public void init(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(RED, GREEN, BLUE, 1);
    }

    private Matrix4f createProjectionMatrix() {
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;


        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix.m33(0);

        return projectionMatrix;

    }

    public static void enableCulling(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling(){
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

}
