package io.github.splotycode.guilib.render;

import static org.lwjgl.opengl.GL11.*;

public abstract class Renderer {

    public abstract float width();
    public abstract float height();
    public abstract void preRender();

    public void color(int color) {
        glColor4f(((color >> 16) & 0xFF) / 255.0f,
                ((color >> 8) & 0xFF) / 255.0f,
                ((color >> 0) & 0xFF) / 255.0f,
                ((color >> 24) & 0xFF) / 255.0f);
    }

    public Renderer drawLine(float startX, float startY, float endX, float endY) {
        preRender();
        glLineWidth(4);
        glBegin(GL_LINES);
        glVertex2f(startX, startY);
        glVertex2f(endX, endY);
        glEnd();
        return this;
    }

    public Renderer drawLine(float startX, float startY, float endX, float endY, int color) {
        color(color);
        return drawLine(startX, startY, endX, endY);
    }

    public Renderer drawHorizontalLine(float startX, float startY, float length) {
        return drawLine(startX, startY, startX + length, startY);
    }

    public Renderer drawHorizontalLine(float startX, float startY, float length, int color) {
        color(color);
        return drawHorizontalLine(startX, startY, length);
    }

    public Renderer drawVerticalLine(float startX, float startY, float length) {
        return drawLine(startX, startY, startX, startY + length);
    }

    public Renderer drawVerticalLine(float startX, float startY, float length, int color) {
        color(color);
        return drawVerticalLine(startX, startY, length);
    }

    public Renderer drawRect(float x, float y, float width, float height) {
        preRender();
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
        return this;
    }

    public Renderer drawRect(float x, float y, float width, float height, int color) {
        color(color);
        return drawRect(x, y, width, height);
    }

    public Renderer fill() {
        return drawRect(0, 0, width(), height());
    }

    public Renderer fill(int color) {
        color(color);
        return fill();
    }

}
