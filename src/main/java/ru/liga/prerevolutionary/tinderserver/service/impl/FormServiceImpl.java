package ru.liga.prerevolutionary.tinderserver.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionary.tinderserver.dto.ImageDto;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.service.FormService;
import ru.liga.prerevolutionary.tinderserver.service.TinderUserService;

@Slf4j
@Service
@AllArgsConstructor
public class FormServiceImpl implements FormService {
    private final ImageServiceImpl imageService;
    private final TinderUserService tinderUserService;
    private final ru.liga.prerevolutionary.tinderserver.service.LikeService LikeService;

    @Override
    public ImageDto searchNext(String chatId) {
        TinderUserDto nextSearch = tinderUserService.getNextSearch(chatId);
        String relate = LikeService.getRelated(chatId, nextSearch.getChatId());
        ImageDto form = imageService.getForm(relate, nextSearch);
        log.info("Get next search for chatId {} as image", chatId);
        return form;
    }

    @Override
    public ImageDto searchPrevious(String chatId) {
        TinderUserDto previousSearch = tinderUserService.getPreviousSearch(chatId);
        String relate = LikeService.getRelated(chatId, previousSearch.getChatId());
        ImageDto form = imageService.getForm(relate, previousSearch);
        log.info("Get previous search for chatId {} as image", chatId);
        return form;
    }

    @Override
    public ImageDto viewNext(String chatId) {
        TinderUserDto nextView = tinderUserService.getNextView(chatId);
        String relate = LikeService.getRelated(chatId, nextView.getChatId());
        ImageDto form = imageService.getForm(relate, nextView);
        log.info("Get next view for chatId {} as image", chatId);
        return form;
    }

    @Override
    public ImageDto viewPrevious(String chatId) {
        TinderUserDto previousView = tinderUserService.getPreviousView(chatId);
        String relate = LikeService.getRelated(chatId, previousView.getChatId());
        ImageDto form = imageService.getForm(relate, previousView);
        log.info("Get previous view for chatId {} as image", chatId);
        return form;
    }
}
