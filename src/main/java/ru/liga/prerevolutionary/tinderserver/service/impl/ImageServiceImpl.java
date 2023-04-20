package ru.liga.prerevolutionary.tinderserver.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionary.tinderserver.dto.ImageDto;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.image.OldImageBuilder;
import ru.liga.prerevolutionary.tinderserver.service.ImageService;
import ru.liga.prerevolutionary.tinderserver.service.TinderUserService;

import java.util.Base64;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final TinderUserService tinderUserService;

    @Override
    public ImageDto getProfile(String chatId) {
        TinderUserDto user = tinderUserService.get(chatId);
        OldImageBuilder oldImageBuilder = new OldImageBuilder(user);
        byte[] bytes = oldImageBuilder.build();
        return new ImageDto(chatId, Base64.getEncoder().encodeToString(bytes));
    }

    @Override
    public ImageDto getForm(String relationLabel, TinderUserDto anotherUser) {
        OldImageBuilder oldImageBuilder = new OldImageBuilder(anotherUser, relationLabel);
        return new ImageDto(anotherUser.getChatId(), Base64.getEncoder().encodeToString(oldImageBuilder.build()));
    }
}
