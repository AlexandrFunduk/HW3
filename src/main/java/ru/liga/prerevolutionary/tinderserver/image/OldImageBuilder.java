package ru.liga.prerevolutionary.tinderserver.image;

import org.capaxit.imagegenerator.Alignment;
import org.capaxit.imagegenerator.Margin;
import org.capaxit.imagegenerator.imageexporter.ImageType;
import org.capaxit.imagegenerator.imageexporter.ImageWriter;
import org.capaxit.imagegenerator.imageexporter.ImageWriterFactory;
import org.capaxit.imagegenerator.impl.TextImageImpl;
import org.capaxit.imagegenerator.textalign.GreedyTextWrapper;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class OldImageBuilder {

    private final TextImageImpl textImage;
    private final BufferedImage backgroundImage;
    private final TinderUserDto user;
    private final String label;

    public OldImageBuilder(TinderUserDto user) {
        this(user, "");
    }

    public OldImageBuilder(TinderUserDto user, String label) {
        //todo чтение файла из ресурсов надо сделать один раз и переиспользовать его каждый раз
        // постоянно читать один и тот же файл нет смысла, к тому же это ест много ресурсов
        //todo можно сделать билдер как Component, инициализирую нужные поля в конструкторе, а уже ниже в методе build() принимать user и label
        try (InputStream oldImage = OldImageBuilder.class.getClassLoader().getResourceAsStream("image/prerev-background.jpg")) {
            assert oldImage != null;
            this.backgroundImage = ImageIO.read(oldImage);
            this.textImage = new TextImageImpl(backgroundImage.getWidth(), backgroundImage.getHeight(), new Margin(25, 20, 25, 20));
            this.user = user;
            this.label = label;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] build() {
        Font likeFont = new Font("Old Standard TT Regular", Font.BOLD, 25);
        Font nameFont = new Font("Old Standard TT Regular", Font.PLAIN, 30);
        Font headerFont = new Font("Old Standard TT Regular", Font.BOLD, 30);
        Font plainFont = new Font("Old Standard TT Regular", Font.PLAIN, 25);

        textImage.write(backgroundImage, 0, 0)
                .useTextWrapper(new GreedyTextWrapper())
                .wrap(true);
        if (!"".equals(label)) {
            textImage.withFont(likeFont)
                    .setTextAligment(Alignment.RIGHT)
                    .write(label);
        } else {
            textImage.newLine();
        }

        String sexWithName = user.getSex() + ", " + user.getName();
        nameFont = TextImageHelper.autoDecrementSizeFontByWidth(textImage, nameFont, sexWithName);
        textImage.setTextAligment(Alignment.LEFT)
                .withFont(nameFont)
                .write(sexWithName)
                .newLine()
                .withFont(headerFont)
                .write(user.getHeader());

        plainFont = TextImageHelper.autoDecrementSizeFontByHeight(textImage, plainFont, user.getDescription());
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
