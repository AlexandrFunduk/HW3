package ru.liga.prerevolutionary.tinderserver.service;

import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;

public interface TinderUserService {

    TinderUserDto get(String chatId);

    TinderUserDto create(TinderUserDto tinderUserDto);

    TinderUserDto update(TinderUserDto tinderUserDto, String chatId);

    TinderUserDto getNextSearch(String chatId);

    TinderUserDto getPreviousSearch(String chatId);

    TinderUserDto getNextView(String chatId);

    TinderUserDto getPreviousView(String chatId);

    TinderUser getUserByChatId(String chatId);
}
