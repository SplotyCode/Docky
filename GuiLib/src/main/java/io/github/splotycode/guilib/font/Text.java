package io.github.splotycode.guilib.font;

import io.github.splotycode.guilib.GuiEngine;
import io.github.splotycode.guilib.render.RenderLoader;
import io.github.splotycode.mosaik.util.collection.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Text {

    private String text;
    private float fontSize;

    private int textMeshVao;
    private TextMeshData data;

    @Setter private Vector2f position;
    @Setter private Vector3f colour;

    private FontFile font;

    private float aspectRatio;

    public Text(String text, float fontSize, Vector2f position, Vector3f colour, FontFile font) {
        this.text = text;
        this.fontSize = fontSize;
        this.position = position;
        this.colour = colour;
        this.font = font;
    }

    public void renderNoUpdate() {
        GuiEngine.INSTANCE.getRenderer().render(this);
    }

    public void render() {
        float current = GuiEngine.INSTANCE.getWindows().get(0).acpectRatio();
        if (aspectRatio != current) {
            load(current);
        }
        renderNoUpdate();
    }

    public void setFontSize(float fontSize) {
        if (this.fontSize != fontSize) {
            this.fontSize = fontSize;
            reload();
        }
    }

    public void setTextNoUpdate(String text) {
        this.text = text;
    }

    public void setText(String text) {
        if (!this.text.equals(text)) {
            setTextNoUpdate(text);
            reload();
        }
    }

    /* Position and color change don't need a reload*/
    public void reload() {
        load(GuiEngine.INSTANCE.getWindows().get(0).acpectRatio());
    }

    public void load(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        float cursorX = 0;
        List<Float> vertices = new ArrayList<>();
        List<Float> textureCoords = new ArrayList<>();
        for (String word : text.split(" ")) {
            for (char letter : word.toCharArray()) {
                CharData charData = font.getCharData(letter);
                //System.out.println(charData.toString());
                addVerticesForCharacter(vertices, cursorX, charData);

                float x = charData.getXTexture();
                float y = charData.getYTexture();
                float xEnd = charData.getXTextureEnd();
                float yEnd = charData.getYTextureEnd();
                textureCoords.addAll(Arrays.asList(x, y, x, yEnd, xEnd, yEnd, xEnd, yEnd, xEnd, y, x, y));

                cursorX += charData.getAdvance(font, aspectRatio) * fontSize;
            }
            cursorX += font.realSpaceWidth(aspectRatio) * fontSize;
        }
        if (data != null) {
            GuiEngine.INSTANCE.getLoader().deleteVao(textMeshVao);
        }
        data = new TextMeshData(ArrayUtil.toFloatArray(vertices), ArrayUtil.toFloatArray(textureCoords));
        textMeshVao = GuiEngine.INSTANCE.getLoader().loadToVAO(data.getVertexPositions(), data.getTextureCoords());
    }

    private void addVerticesForCharacter(List<Float> vertices, float cursorX, CharData charData) {
        float x = cursorX + 0;
        float y = charData.getYOffset() * fontSize;
        float renderX = (2 * x) - 1;
        float renderY = (-2 * y) + 1;

        float maxX = (2 * (x + (charData.getSizeX(font, aspectRatio) * fontSize))) - 1;
        float maxY = (-2 * (y + (charData.getSizeY() * fontSize))) + 1;
        vertices.addAll(Arrays.asList(renderX, renderY, renderX, maxY, maxX, maxY, maxX, maxY, maxX, renderY, renderX, renderY));
    }


    public void setPosition(float x, float y) {
        setPosition(new Vector2f(x, y));
    }
}
