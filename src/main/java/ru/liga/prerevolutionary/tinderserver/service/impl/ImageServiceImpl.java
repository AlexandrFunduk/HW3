package ru.liga.prerevolutionary.tinderserver.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionary.tinderserver.dto.ImageDto;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.image.OldImageBuilder;
import ru.liga.prerevolutionary.tinderserver.service.ImageService;
import ru.liga.prerevolutionary.tinderserver.service.TinderUserService;
import ru.liga.prerevolutionary.tinderserver.service.translator.PrerevolutionaryTranslateService;

import java.util.Base64;

@Slf4j
@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final TinderUserService tinderUserService;
    private final PrerevolutionaryTranslateService prerevolutionaryTranslateService;
    private final OldImageBuilder oldImageBuilder;

    @Override
    public ImageDto getProfile(String chatId) {
        log.info("Get profile with chatId {} as image", chatId);
        TinderUserDto user = prerevolutionaryTranslateService.translate(tinderUserService.get(chatId));
        byte[] imageAsBytes = oldImageBuilder.build(user);
        return new ImageDto(chatId, Base64.getEncoder().encodeToString(imageAsBytes));
    }

    @Override
    public ImageDto getForm(String relationLabel, TinderUserDto anotherUser) {
        TinderUserDto translatedUser = prerevolutionaryTranslateService.translate(anotherUser);
        String translatedLabel = prerevolutionaryTranslateService.translate(relationLabel);
        byte[] imageAsBytes = oldImageBuilder.build(translatedUser, translatedLabel);
        return new ImageDto(anotherUser.getChatId(), Base64.getEncoder().encodeToString(imageAsBytes));
    }
}
