package ru.liga.prerevolutionary.tinderserver.service;

import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;

public interface TinderUserService {
    void create(TinderUserDto tinderUserDto);

    void update(TinderUserDto tinderUserDto, String chatId);

    void like(String chatId, String anotherChatId);
}
