package org.example;
import org.example.entity.*;
import org.example.shaders.StaticShader;
import org.example.shaders.TerrainShader;
import org.example.terrains.Terrain;
import org.example.textures.*;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;


public class Main {

    public static void main(String[] args) {
        WindowManager window = new WindowManager("Engine12", 1280, 720);
        window.init();
        ObjectLoader loader = new ObjectLoader();

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("path"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));


        Model model = OBJLoader.loadObjModel("dragon", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("images"));
        TexturedModel texturedModel = new TexturedModel(model, texture);
        texturedModel.getTexture().setHasTransparency(true);
        Model model1 = OBJLoader.loadObjModel("fern", loader);
        ModelTexture texture1 = new ModelTexture(loader.loadTexture("fern"));
        TexturedModel texturedModel1 = new TexturedModel(model1, texture1);
        texturedModel1.getTexture().setHasTransparency(true);
        texturedModel1.getTexture().setUseFakeLighting(true);

        ModelTexture t = texturedModel.getTexture();
        t.setShineDamper(10);
        t.setReflectivity(1);

        List<Entity> allEntities = new ArrayList<Entity>();
        Random random = new Random();

        for(int i = 0; i < 100; i++) {
            float x = random.nextFloat() * 100 - 50;
            float y = random.nextFloat() * 100 - 50;
            float z = random.nextFloat() * -300;
            allEntities.add(new Entity(texturedModel, new Vector3f(x, y, z), random.nextFloat() * 180f,
                    random.nextFloat() * 180f, 0f, 1f));
        }

        Camera camera = new Camera(window);
        Entity entity = new Entity(texturedModel, new Vector3f(0, 5, -40),0, 0, 0, 1);
        Entity entity1 = new Entity(texturedModel1, new Vector3f(0, 0, -30),0, 0, 0, 1f);
        texturedModel1.getTexture().setHasTransparency(true);

        Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1,1,1));

        Entity[] l = new Entity[50];
        for(int j = 0; j < 50; j++) {
            Entity entity2;
            l[j] = entity2 = new Entity(texturedModel1, new Vector3f(random.nextInt(400)*-1, 0, random.nextInt(400)*-1),
                0, 0, 0, 1);
        }

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);


        MasterRenderer renderer = new MasterRenderer(window);
        while(!window.windowShouldClose()){
            //game logic
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processEntity(entity1);
            renderer.processEntity(entity);
            renderer.processEntity(entity);

            for(int i = 0; i < l.length; i++){
                renderer.processEntity(l[i]);
            }

            camera.move();

            renderer.render(light, camera);

            window.update();
        }

        renderer.cleanUp();
        loader.cleanup();
        window.cleanup();

    }
}