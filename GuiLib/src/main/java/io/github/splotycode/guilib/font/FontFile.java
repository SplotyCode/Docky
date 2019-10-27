package io.github.splotycode.guilib.font;

import io.github.splotycode.mosaik.util.ExceptionUtil;
import io.github.splotycode.mosaik.util.io.IOUtil;
import lombok.Getter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Getter
public class FontFile {

    private Map<Integer, CharData> characters = new HashMap<>();

    private BufferedReader reader;
    private String[] currentLine;

    private float verticalPerPixelSize;

    private int spaceWidth;

    private int textureAtlas;

    public FontFile(int textureAtlas, String file) {
        this.textureAtlas = textureAtlas;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(IOUtil.resourceToURL(file).openStream()))) {
            this.reader = reader;

            next();
            int[] padding = getIntArrayVariable("padding");
            int paddingWidth = padding[1] + padding[3];
            int paddingHeight = padding[0] + padding[2];

            next();
            verticalPerPixelSize = 0.03f / (float) (getIntVariable("lineHeight") - paddingHeight);
            int imageSize = getIntVariable("scaleW");

            next();
            next();
            while (doNext()) {
                int id = getIntVariable("id");
                if (id == 32) {
                    spaceWidth = getIntVariable("xadvance") - paddingWidth;
                    continue;
                }
                float xTex = ((float) getIntVariable("x") + (padding[1] - getPadding())) / imageSize;
                float yTex = ((float) getIntVariable("y") + (padding[0] - getPadding())) / imageSize;
                float width = getIntVariable("width") - (paddingWidth - (2 * 3));
                float height = getIntVariable("height") - ((paddingHeight) - (2 * getPadding()));
                float xTexSize = width / imageSize;
                float yTexSize = height / imageSize;
                float xOffset = (getIntVariable("xoffset") + padding[1] - getPadding()); /* Don't know aspect ratio */
                float yOffset = (getIntVariable("yoffset") + (padding[0] - getPadding())) * verticalPerPixelSize;
                float xAdvance = (getIntVariable("xadvance") - paddingWidth); /* Don't know aspect ratio */
                characters.put(id, new CharData(id,
                        xTex, yTex, /* Texture start */
                        xOffset, yOffset, /* Offset */
                        width, height * verticalPerPixelSize, /* Texture size | Don't know aspect ratio */
                        xTex + xTexSize, yTex + yTexSize, /* Texture end */
                        xAdvance));
            }

        } catch (IOException e) {
            ExceptionUtil.throwRuntime(e);
        }
        //System.out.println("my: " + characters);
    }

    private boolean doNext() throws IOException {
        next();
        return currentLine != null;
    }

    private String getVariable(String name) {
        for (int i = 0; i < currentLine.length; i++) {
            String var = currentLine[i];
            if (var.startsWith(name)) {
                return var.substring(name.length() + 1);
            }
        }
        return null;
    }

    private int getIntVariable(String name) {
        return Integer.parseInt(getVariable(name));
    }

    private int[] getIntArrayVariable(String name) {
        String[] numbers = getVariable(name).split(",");
        int[] ints = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            ints[i] = Integer.parseInt(numbers[i]);
        }
        return ints;
    }

    /*public FontFile removeEdges() {
        float minY = Integer.MAX_VALUE;
        for (CharData data : characters.values()) {
            minY = Math.min(minY, data.getYOffset());
        }
        for (CharData data : characters.values()) {
            data.yOffset -= minY;
        }
        return this;
    }*/

    private float getPadding() {
        return 3;
    }

    private void next() throws IOException {
        String line = reader.readLine();
        currentLine = line == null ? null : line.split(" ");
    }

    public float realSpaceWidth(float aspectRatio) {
        return spaceWidth * (verticalPerPixelSize / aspectRatio);
    }

    public CharData getCharData(char letter) {
        return characters.get((int) letter);
    }

}
