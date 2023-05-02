package ru.liga.prerevolutionary.tinderserver.image;

import org.capaxit.imagegenerator.Alignment;
import org.capaxit.imagegenerator.Margin;
import org.capaxit.imagegenerator.imageexporter.ImageType;
import org.capaxit.imagegenerator.imageexporter.ImageWriter;
import org.capaxit.imagegenerator.imageexporter.ImageWriterFactory;
import org.capaxit.imagegenerator.impl.TextImageImpl;
import org.capaxit.imagegenerator.textalign.GreedyTextWrapper;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
public class OldImageBuilder {

    private static final String OLD_STANDARD_TT_REGULAR = "Old Standard TT Regular";
    private static final Font MAX_SIZE_FONT_FOR_PLAIN_TEXT = new Font(OLD_STANDARD_TT_REGULAR, Font.PLAIN, 25);
    private static final Font MAX_FOUNT_SIZE_FOR_NAME_WITH_SEX = new Font(OLD_STANDARD_TT_REGULAR, Font.PLAIN, 30);
    private static final Font LIKE_FONT = new Font(OLD_STANDARD_TT_REGULAR, Font.BOLD, 25);
    private static final Font HEADER_FONT = new Font(OLD_STANDARD_TT_REGULAR, Font.BOLD, 30);
    private final BufferedImage backgroundImage;

    public OldImageBuilder() {
        try (InputStream oldImage = OldImageBuilder.class.getClassLoader().getResourceAsStream("image/prerev-background.jpg")) {
            assert oldImage != null;
            this.backgroundImage = ImageIO.read(oldImage);
        } catch (IOException e) {
            throw new RuntimeException("Resource image/prerev-background.jpg can not read", e);
        }
    }

    public byte[] build(TinderUserDto user) {
        return build(user, "");
    }

    public byte[] build(TinderUserDto user, String label) {
        TextImageImpl textImage = new TextImageImpl(backgroundImage.getWidth(), backgroundImage.getHeight(), new Margin(25, 20, 25, 20));

        textImage.write(backgroundImage, 0, 0)
                .useTextWrapper(new GreedyTextWrapper())
                .wrap(true);
        if (!"".equals(label)) {
            textImage.withFont(LIKE_FONT)
                    .setTextAligment(Alignment.RIGHT)
                    .write(label);
        } else {
            textImage.newLine();
        }

        String sexWithName = user.getSex() + ", " + user.getName();
        Font optimalFountSizeForNameWithSex = TextImageHelper.autoDecrementSizeFontByWidth(textImage, MAX_FOUNT_SIZE_FOR_NAME_WITH_SEX, sexWithName);
        textImage.setTextAligment(Alignment.LEFT)
                .withFont(optimalFountSizeForNameWithSex)
                .write(sexWithName)
                .newLine()
                .withFont(HEADER_FONT)
                .write(user.getHeader());

        Font plainFont = TextImageHelper.autoDecrementSizeFontByHeight(textImage, MAX_SIZE_FONT_FOR_PLAIN_TEXT, user.getDescription());
        textImage.withFont(plainFont)
                .write(user.getDescription());

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageWriter imageWriter = ImageWriterFactory.getImageWriter(ImageType.PNG);
            //сохраняем для теста, строку ниже нужно удалить
            imageWriter.writeImageToFile(textImage, new File("src/main/resources/image/test.png"));

            imageWriter.writeImageToOutputStream(textImage, out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
