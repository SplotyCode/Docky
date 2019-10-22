package io.github.splotycode.guilib.font;

import io.github.splotycode.guilib.GuiEngine;
import io.github.splotycode.guilib.render.RenderLoader;
import io.github.splotycode.mosaik.util.collection.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public class Text {

    private String text;
    private float fontSize;

    private int textMeshVao;
    private TextMeshData data;

    private Vector2f position;
    private Vector3f colour;

    private FontFile font;

    private float aspectRatio;

    public void load(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        float cursorX = 0;
        List<Float> vertices = new ArrayList<>();
        List<Float> textureCoords = new ArrayList<>();
        for (String word : text.split(" ")) {
            for (char letter : word.toCharArray()) {
                CharData charData = font.getCharData(letter);
                addVerticesForCharacter(vertices, cursorX, charData);

                float x = charData.getXTexture();
                float y = charData.getYTexture();
                float xEnd = charData.getXTextureEnd();
                float yEnd = charData.getYTextureEnd();
                vertices.addAll(Arrays.asList(x, y, x, yEnd, xEnd, yEnd, xEnd, yEnd, xEnd, y, x, y));

                cursorX += charData.getAdvance(font, aspectRatio) * fontSize;
            }
            cursorX += font.realSpaceWidth(aspectRatio) * fontSize;
        }
        data = new TextMeshData(ArrayUtil.toFloatArray(vertices), ArrayUtil.toFloatArray(textureCoords));
        textMeshVao = GuiEngine.INSTANCE.getLoader().loadToVAO(data.getVertexPositions(), data.getTextureCoords());
    }

    private void addVerticesForCharacter(List<Float> vertices, float cursorX, CharData charData) {
        float x = (2 * (cursorX + (charData.getxOffset(font, aspectRatio) * fontSize))) - 1;
        float y = (-2 * (charData.getYOffset() * fontSize)) + 1;
        float maxX = (2 * (x + (charData.getSizeX(font, aspectRatio) * fontSize))) - 2;
        float maxY = (-2 * (y + (charData.getSizeY() * fontSize))) + 1;
        vertices.addAll(Arrays.asList(x, y, x, maxY, maxX, maxY, maxX, maxY, maxX, y, x, y));
    }


}
