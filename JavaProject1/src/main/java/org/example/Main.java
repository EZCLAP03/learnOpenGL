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
        WindowManager window = new WindowManager("w h y", 1280, 760);
        window.init();
        window.setClearColor(0.5f, 0.5f, 0.5f, 1);
        ObjectLoader loader = new ObjectLoader();

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("default"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("default"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("default"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("default"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));


        Model model = OBJLoader.loadObjModel("Grass_Block", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("images"));
        TexturedModel texturedModel = new TexturedModel(model, texture);
        texturedModel.getTexture().setHasTransparency(true);
        Model model1 = OBJLoader.loadObjModel("fern", loader);
        ModelTexture texture1 = new ModelTexture(loader.loadTexture("fern"));
        TexturedModel texturedModel1 = new TexturedModel(model1, texture1);
        texturedModel1.getTexture().setHasTransparency(false);
        texturedModel1.getTexture().setUseFakeLighting(true);

        Model model2 = OBJLoader.loadObjModel("dragon", loader);
        ModelTexture texture2 = new ModelTexture(loader.loadTexture("default"));
        TexturedModel texturedModel2 = new TexturedModel(model2, texture2);


        ModelTexture t = texturedModel2.getTexture();
        t.setShineDamper(10);
        t.setReflectivity(0);

        ModelTexture t1 = texturedModel1.getTexture();
        t1.setShineDamper(10);
        t1.setReflectivity(1);


        List<Entity> allEntities = new ArrayList<Entity>();
        Random random = new Random();

        for(int i = 0; i < 100; i++) {
            float x = random.nextFloat() * 100 - 50;
            float y = random.nextFloat() * 100 - 50;
            float z = random.nextFloat() * -300;
            allEntities.add(new Entity(texturedModel, new Vector3f(x, y, z), random.nextFloat() * 180f,
                    random.nextFloat() * 180f, 0f, 1f));
        }

        Entity entity = new Entity(texturedModel, new Vector3f(0, 5, -40),0, 0, 0, 3);
        Entity entity1 = new Entity(texturedModel1, new Vector3f(0, 0, -30),0, 0, 0, 0.6f);
        Entity entity3 = new Entity(texturedModel2, new Vector3f(0, 2, -100),0, 0, 0, 10f);

        Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1,1,1));

        Entity[] l = new Entity[50];


        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightMap");
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightMap");
        for(int j = 0; j < 50; j++) {
            float x = random.nextInt(400)*-1;
            float z =random.nextInt(400)*-1;
            Entity entity2 = new Entity(texturedModel1, new Vector3f(x, terrain2.getHeightOfTerrain(x, z), z),
                    0, 0, 0, 1f);
            l[j] = entity2;

        }


        TexturedModel dragon = new TexturedModel(model, texture);
        Player player = new Player(dragon, new Vector3f(0, 0, -50), 0, 0, 0, 0.0001f);
        Camera camera = new Camera(window, player);

        MasterRenderer renderer = new MasterRenderer();
        while(!window.windowShouldClose()){
            //game logic
           renderer.processTerrain(terrain);
           renderer.processTerrain(terrain2);
           player.move(terrain2);
           renderer.processEntity(player);
           renderer.processEntity(entity3);


            for (Entity value : l) {
                renderer.processEntity(value);
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