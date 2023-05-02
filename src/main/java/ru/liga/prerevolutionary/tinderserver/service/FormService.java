package ru.liga.prerevolutionary.tinderserver.service;

import ru.liga.prerevolutionary.tinderserver.dto.ImageDto;

public interface FormService {

    ImageDto searchNext(String chatId);

    ImageDto searchPrevious(String chatId);

    ImageDto viewNext(String chatId);

    ImageDto viewPrevious(String chatId);

}
