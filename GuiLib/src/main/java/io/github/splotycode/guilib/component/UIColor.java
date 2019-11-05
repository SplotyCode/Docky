package io.github.splotycode.guilib.component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joml.Vector3f;

import java.awt.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UIColor {

    public static Vector3f toVector(int color) {
        return new Vector3f(((color >> 16) & 0xFF) / 255.0f,
                ((color >> 8) & 0xFF) / 255.0f,
                ((color >> 0) & 0xFF) / 255.0f);
    }

    public static final int BLACK = Color.BLACK.getRGB();
    public static final int WHITE = Color.WHITE.getRGB();
    public static final int BLUE = Color.BLUE.getRGB();
    public static final int YELLOW = Color.YELLOW.getRGB();
    public static final int RED = Color.RED.getRGB();
    public static final int RED_TRANSPARENT = new Color(255, 0, 0, 255 / 2).getRGB();

}
