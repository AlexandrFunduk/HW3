package ru.liga.prerevolutionary.tinderserver.service;

import ru.liga.prerevolutionary.tinderserver.dto.ImageDto;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;

public interface ImageService {
    ImageDto getForm(String relationLabel, TinderUserDto anotherUser);

    ImageDto getProfile(String chatId);
}
