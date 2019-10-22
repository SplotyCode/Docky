package io.github.splotycode.guilib.font;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
public class CharData {

    @Getter private final int id;
    @Getter private final float xTexture, yTexture;
    private final float xOffset;
    @Getter private final float yOffset;
    private final float sizeX;
    @Getter private final float sizeY;
    @Getter private final float xTextureEnd, yTextureEnd;

    private final float advance;

    public float getAdvance(FontFile font, float aspectRatio) {
        return advance * (font.getVerticalPerPixelSize() / aspectRatio);
    }

    public float getSizeX(FontFile font, float aspectRatio) {
        return sizeX * (font.getVerticalPerPixelSize() / aspectRatio);
    }

    public float getxOffset(FontFile font, float aspectRatio) {
        return xOffset * (font.getVerticalPerPixelSize() / aspectRatio);
    }

}
