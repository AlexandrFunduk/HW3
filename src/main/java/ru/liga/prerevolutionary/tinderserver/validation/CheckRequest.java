package ru.liga.prerevolutionary.tinderserver.validation;

import ru.liga.prerevolutionary.tinderserver.exception.NotAllowRequest;

public class CheckRequest {
    public static void validateEqualChatId(String chatId, String headerChatId) {
        if (!headerChatId.equals(chatId)) {
            throw new NotAllowRequest("Header parameter chatId %s not equals path parameter %s".formatted(headerChatId, chatId));
        }
    }
}
