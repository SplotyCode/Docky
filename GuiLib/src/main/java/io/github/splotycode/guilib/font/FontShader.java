package io.github.splotycode.guilib.font;

import io.github.splotycode.guilib.shader.Shader;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class FontShader extends Shader {

    private int locationColor;
    private int locationTranslation;

    public FontShader() {
        super("io/github/splotycode/guilib/font/fontVertex.txt", "io/github/splotycode/guilib/font/fontFragment.txt");
    }

    @Override
    protected void getAllUniformLocations() {
        locationColor = getUniformLocation("color");
        locationTranslation = getUniformLocation("translation");
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "textureCoords");
    }

    protected void loadColor(Vector3f color) {
        loadVector(locationColor, color);
    }

    protected void loadTranslation(Vector2f translation) {
        loadVector(locationTranslation, translation);
    }
}
