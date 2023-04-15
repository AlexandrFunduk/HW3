package ru.liga.prerevolutionary.tinderserver.dto.converter;

public interface Converter<SOURCE, TARGET> {
    TARGET convert(SOURCE source);

    TARGET convert(SOURCE source, TARGET target);
}
