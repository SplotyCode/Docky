package io.github.splotycode.guilib.render;

import io.github.splotycode.mosaik.util.collection.MultiHashMap;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.glBindTexture;
import static org.lwjgl.opengl.GL30.GL_VERTEX_ARRAY_BINDING;
import static org.lwjgl.opengles.EXTSparseTexture.GL_TEXTURE_2D;
import static org.lwjgl.opengles.GLES20.GL_UNSIGNED_BYTE;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class RenderLoader {

    private MultiHashMap<Integer, Integer> vaos = new MultiHashMap<>();

    private List<Integer> textures = new ArrayList<>();

    public int loadToVAO(float[] positions, float[] textureCoords) {
        int vaoID = createVAO();
        storeDataInAttributeList(0, 2, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        unbindVAO();
        return vaoID;
    }

    public int loadTexture(String file) throws IOException {
        int id = glGenTextures();
        textures.add(id);

        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);
        //ByteBuffer.wrap(IOUtil.loadBytes(IOUtil.resourceToURL(file)))
        ByteBuffer data = stbi_load(file, width, height, comp, 4);
        if (data == null) {
            throw new RuntimeException(file + " " + stbi_failure_reason());
        }

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        stbi_image_free(data);

        return id;
    }

    public void deleteVao(int vao) {
        GL30.glDeleteVertexArrays(vao);
        for (int vbo : vaos.get(vao)) {
            GL15.glDeleteBuffers(vbo);
        }
        vaos.remove(vao);
    }

    public void cleanUp() {
        for (int texture : textures) {
            GL11.glDeleteTextures(texture);
        }

        for (Map.Entry<Integer, ArrayList<Integer>> vao : vaos.entrySet()) {
            GL30.glDeleteVertexArrays(vao.getKey());
            for (int vbo : vao.getValue()) {
                GL15.glDeleteBuffers(vbo);
            }
        }
    }

    private int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaos.put(vaoID, null);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        try (MemoryStack stack = stackPush()) {
            IntBuffer vao = stack.mallocInt(1);
            glGetIntegerv(GL_VERTEX_ARRAY_BINDING, vao);
            vaos.addToList(vao.get(), vboID);
        }
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}
