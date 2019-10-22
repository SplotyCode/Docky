package io.github.splotycode.guilib.font;

import lombok.Data;

@Data
public class TextMeshData {

    private final float[] vertexPositions;
    private final float[] textureCoords;

    public int getVertexCount() {
        return vertexPositions.length / 2;
    }

}
