package ru.liga.prerevolutionary.tinderserver.service;

import ru.liga.prerevolutionary.tinderserver.model.LikeId;

public interface LikeService {
    LikeId getLikeId(String chatId, String anotherChatId);

    String getRelated(String chatId, String anotherChatId);
}
