package ru.liga.prerevolutionary.tinderserver.image;

import org.capaxit.imagegenerator.Margin;
import org.capaxit.imagegenerator.TextImage;
import org.capaxit.imagegenerator.TextWrapper;
import org.capaxit.imagegenerator.impl.TextImageImpl;
import org.capaxit.imagegenerator.textalign.GreedyTextWrapper;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.util.Optional;

public class TextImageHelper {
    private static final TextWrapper wrapper = new GreedyTextWrapper();

    public static Font autoDecrementSizeFontByWidth(TextImage textImage, Font maxSizeFont, String textForAutosize) {
        //todo какая то сложная логика с рефлексией, точно нельзя сделать проще?
        Margin margin = (Margin) ReflectionHelper.getFieldValue(textImage, "margin");
        Integer leftMargin = Optional.ofNullable(margin).map(Margin::getLeft).orElse(0);
        Integer rightMargin = Optional.ofNullable(margin).map(Margin::getRight).orElse(0);
        int width = textImage.getWidth() - leftMargin - rightMargin;
        FontMetrics fm = ((TextImageImpl) textImage).getBufferedImage().getGraphics().getFontMetrics(maxSizeFont);
        GlyphVector vector = maxSizeFont.createGlyphVector(fm.getFontRenderContext(), textForAutosize);
        Shape outline = vector.getOutline(0, 0);
        double expectedWidth = outline.getBounds().getWidth();
        if (expectedWidth > width) {
            int newFont = (int) (width * maxSizeFont.getSize() / expectedWidth);
            return new Font(maxSizeFont.getFontName(), maxSizeFont.getStyle(), newFont);
        }
        return maxSizeFont;
    }

    public static Font autoDecrementSizeFontByHeight(TextImage textImage, Font maxSizeFont, String s) {
        Font currentFont = maxSizeFont;
        Margin margin = (Margin) ReflectionHelper.getFieldValue(textImage, "margin");
        Integer leftMargin = Optional.ofNullable(margin).map(Margin::getLeft).orElse(0);
        Integer rightMargin = Optional.ofNullable(margin).map(Margin::getRight).orElse(0);
        Integer bottomMargin = Optional.ofNullable(margin).map(Margin::getBottom).orElse(0);

        int width = textImage.getWidth() - leftMargin - rightMargin;

        Integer currentYPos = (Integer) ReflectionHelper.getFieldValue(textImage, "yPos");

        int height = textImage.getHeight() - Optional.ofNullable(currentYPos).orElse(0) - bottomMargin;
        double expectedHeight = getExpectedHeight((TextImageImpl) textImage, s, width, currentFont);
        while (expectedHeight > height) {
            currentFont = new Font(currentFont.getFontName(), currentFont.getStyle(), currentFont.getSize() - 1);
            expectedHeight = getExpectedHeight((TextImageImpl) textImage, s, width, currentFont);
        }
        return currentFont;
    }

    private static double getExpectedHeight(TextImageImpl textImage, String s, int width, Font currentFont) {
        FontMetrics fm = textImage.getBufferedImage().getGraphics().getFontMetrics(currentFont);
        int lineCount = wrapper.doWrap(s, width, fm).size();
        return lineCount * (fm.getAscent() + fm.getHeight());
    }
}
