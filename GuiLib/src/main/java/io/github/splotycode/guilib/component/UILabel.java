package io.github.splotycode.guilib.component;

import io.github.splotycode.guilib.font.FontFile;
import io.github.splotycode.guilib.font.Text;
import io.github.splotycode.guilib.layout.Constrains;
import io.github.splotycode.guilib.render.DummyRenderer;
import io.github.splotycode.guilib.render.RenderContext;
import io.github.splotycode.guilib.render.Renderer;
import io.github.splotycode.guilib.window.Window;

import static org.lwjgl.opengl.GL11.*;

public class UILabel extends UIComponent {

    private final Text text;
    private String string;
    private float xAdvance;

    private float lastAspectRatio = -1;

    public UILabel(String string, FontFile font, int color) {
        this(new Text(string, 2, null, UIColor.toVector(color), font));
    }

    public UILabel(String string, String font, int color) {
        this(new Text(string, 2, null, UIColor.toVector(color), font));
    }

    private UILabel(Text text) {
        this.text = text;
        string = text.getText();
    }

    public void setText(String string) {
        this.string = string;
        text.setTextNoUpdate(string);
        lastAspectRatio = -1;
    }

    public void setColor(int color) {
        text.setColour(UIColor.toVector(color));
    }

    private void reCalcAdvance() {
        xAdvance = 0;
        for (char c : string.toCharArray()) {
            xAdvance += c == 32 ?
                    text.getFont().realSpaceWidth(text.getAspectRatio()) :
                    text.getFont().getCharData(c).getAdvance(text.getFont(), text.getAspectRatio());
        }
        lastAspectRatio = text.getAspectRatio();
    }

    @Override
    protected void updateSelf(RenderContext ctx) {
        super.updateSelf(ctx);
        Window window = ctx.getWindow();
        Constrains constrains = ctx.getConstrains();

        if (lastAspectRatio != ctx.getWindowAspectRatio()) {
            text.reload(ctx);
            reCalcAdvance();
        }
        float fittingWidthFont = constrains.width() / (xAdvance * window.getWidth());
        float fittingHeightFont = constrains.height() / (0.03f * window.getHeight());
        float fontSize = Math.min(fittingWidthFont, fittingHeightFont);
        text.setFontSize(ctx, fontSize);
        float x = computePosition(constrains.x(), fontSize, fittingWidthFont, xAdvance, window.getWidth());
        float y = computePosition(constrains.y(), fontSize, fittingHeightFont, 0.03f, window.getHeight());
        text.setPosition(x, y);

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        glOrtho(0, 1, 1, 0, 1, -1);
        DummyRenderer.INSTANCE.drawRect(x, y, fontSize * xAdvance, fontSize * 0.03f, UIColor.YELLOW);
        glPopMatrix();
    }

    private float computePosition(float coordinate, float usedFontSize, float calculatedFontSize, float scaling, float window) {
        float calculated = calculatedFontSize * scaling;
        float actual = usedFontSize * scaling;

        float loss = calculated - actual;

        return coordinate / window + loss / 2;
    }

    @Override
    protected void renderSelf(RenderContext ctx, Renderer renderer) {
        renderer.fill(UIColor.RED_TRANSPARENT);
        text.renderNoUpdate(ctx);
    }
}
