package ru.liga.prerevolutionary.tinderserver.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prerevolutionary.tinderserver.dto.ImageDto;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.service.LikeService;
import ru.liga.prerevolutionary.tinderserver.service.TinderUserService;
import ru.liga.prerevolutionary.tinderserver.service.impl.ImageServiceImpl;

@Slf4j
@RestController
@RequestMapping("/users/image")
@AllArgsConstructor
@Valid
public class ImageController {
    private final ImageServiceImpl imageService;
    private final TinderUserService tinderUserService;
    private final LikeService LikeService;

    @GetMapping
    public ImageDto getImage(@NotBlank @RequestHeader(value = "chatId") String chatId) {
        return imageService.getProfile(chatId);
    }

    @GetMapping(value = "/search/next")
    public ImageDto searchNext(@NotBlank @RequestHeader(value = "chatId") String chatId) {
        //todo много логики в контроллере не должно быть, лучше вынести все что ниже в метод сервиса
        TinderUserDto nextSearch = tinderUserService.getNextSearch(chatId);
        String relate = LikeService.getRelated(chatId, nextSearch.getChatId());
        ImageDto form = imageService.getForm(relate, nextSearch);
        log.info("Get next search for chatId {} as image", chatId);
        return form;
    }

    @GetMapping(value = "/search/previous")
    public ImageDto searchPrevious(@NotBlank @RequestHeader(value = "chatId") String chatId) {
        TinderUserDto previousSearch = tinderUserService.getPreviousSearch(chatId);
        String relate = LikeService.getRelated(chatId, previousSearch.getChatId());
        ImageDto form = imageService.getForm(relate, previousSearch);
        log.info("Get previous search for chatId {} as image", chatId);
        return form;
    }

    @GetMapping(value = "/view/next")
    public ImageDto viewNext(@NotBlank @RequestHeader("chatId") String chatId) {
        TinderUserDto nextView = tinderUserService.getNextView(chatId);
        String relate = LikeService.getRelated(chatId, nextView.getChatId());
        ImageDto form = imageService.getForm(relate, nextView);
        log.info("Get next view for chatId {} as image", chatId);
        return form;
    }

    @GetMapping(value = "/view/previous")
    public ImageDto viewPrevious(@NotBlank @RequestHeader("chatId") String chatId) {
        TinderUserDto previousView = tinderUserService.getPreviousView(chatId);
        String relate = LikeService.getRelated(chatId, previousView.getChatId());
        ImageDto form = imageService.getForm(relate, previousView);
        log.info("Get previous view for chatId {} as image", chatId);
        return form;
    }
}
