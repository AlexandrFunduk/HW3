package ru.liga.prerevolutionary.tinderserver.image;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontRegistrar {
    public static void registerFromResource(String resource) {
        try {
            InputStream resourceAsStream = FontRegistrar.class.getClassLoader().getResourceAsStream(resource);
            assert resourceAsStream != null;
            Font font = Font.createFont(Font.TRUETYPE_FONT, resourceAsStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
