package io.github.splotycode.guilib.font;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class FontRenderer {

    private FontShader shader = new FontShader();

    public void shutdown() {
        shader.cleanUp();
    }

    public void render(Text text) {
        shader.start();
        GL13.glActiveTexture(GL13.GL_ACTIVE_TEXTURE);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, text.getFont().getTextureAtlas());

        GL30.glBindVertexArray(text.getTextMeshVao());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        shader.loadColor(text.getColour());
        shader.loadTranslation(text.getPosition());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getData().getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);

        shader.stop();
    }

}
