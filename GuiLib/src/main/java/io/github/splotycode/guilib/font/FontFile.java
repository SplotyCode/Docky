package io.github.splotycode.guilib.font;

import io.github.splotycode.mosaik.util.ExceptionUtil;
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

    public FontFile(int textureAtlas, File file) {
        this.textureAtlas = textureAtlas;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
                float xTex = ((float) getIntVariable("x") + (padding[1] - 3)) / imageSize;
                float yTex = ((float) getIntVariable("y") + (padding[0] - 3)) / imageSize;
                int width = getIntVariable("width") - (paddingWidth - (2 * 3));
                int height = getIntVariable("height") - ((paddingHeight) - (2 * 3));
                float xTexSize = (float) width / imageSize;
                float yTexSize = (float) height / imageSize;
                float xOffset = (getIntVariable("xoffset") + padding[1] - 3); /* Don't know aspect ratio */
                float yOffset = (getIntVariable("yoffset") + (padding[0] - 3)) * verticalPerPixelSize;
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
        String numbers[] = getVariable(name).split(",");
        int[] ints = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            ints[i] = Integer.parseInt(numbers[i]);
        }
        return ints;
    }

    private void next() throws IOException {
        currentLine = reader.readLine().split(" ");
    }

    public float realSpaceWidth(float aspectRatio) {
        return spaceWidth * (verticalPerPixelSize / aspectRatio);
    }

    public CharData getCharData(char letter) {
        return characters.get((int) letter);
    }

}
