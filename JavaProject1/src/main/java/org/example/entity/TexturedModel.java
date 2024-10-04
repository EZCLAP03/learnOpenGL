package org.example.entity;

import org.example.textures.ModelTexture;

public class TexturedModel {
    private Model rawModel;
    private ModelTexture texture;

    public TexturedModel(Model rawModel, ModelTexture texture) {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public Model getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }
}
