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
import ru.liga.prerevolutionary.tinderserver.service.FormService;
import ru.liga.prerevolutionary.tinderserver.service.impl.ImageServiceImpl;

@Slf4j
@RestController
@RequestMapping("/users/image")
@AllArgsConstructor
@Valid
public class ImageController {
    private final ImageServiceImpl imageService;
    private final FormService formService;

    @GetMapping
    public ImageDto getImage(@NotBlank @RequestHeader(value = "chatId") String chatId) {
        return imageService.getProfile(chatId);
    }

    @GetMapping(value = "/search/next")
    public ImageDto searchNext(@NotBlank @RequestHeader(value = "chatId") String chatId) {
        return formService.searchNext(chatId);
    }

    @GetMapping(value = "/search/previous")
    public ImageDto searchPrevious(@NotBlank @RequestHeader(value = "chatId") String chatId) {
        return formService.searchPrevious(chatId);
    }

    @GetMapping(value = "/view/next")
    public ImageDto viewNext(@NotBlank @RequestHeader("chatId") String chatId) {
        return formService.viewNext(chatId);
    }

    @GetMapping(value = "/view/previous")
    public ImageDto viewPrevious(@NotBlank @RequestHeader("chatId") String chatId) {
        return formService.viewPrevious(chatId);
    }
}
