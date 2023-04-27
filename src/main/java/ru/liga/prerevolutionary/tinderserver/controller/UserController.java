package ru.liga.prerevolutionary.tinderserver.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.exception.NotAllowRequest;
import ru.liga.prerevolutionary.tinderserver.service.LikeService;
import ru.liga.prerevolutionary.tinderserver.service.TinderUserService;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Valid
public class UserController {
    private final TinderUserService tinderUserService;
    private final LikeService likeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TinderUserDto createUser(@NotNull @RequestBody TinderUserDto dto) {
        //todo если пишешь лог в контроллере, то лучше писать его в виде:
        // log.info("Accept request to register client, request: {}", dto);
        log.info("register {}", dto);
        return tinderUserService.create(dto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TinderUserDto updateUser(@Valid @RequestBody TinderUserDto dto, @NotBlank @RequestHeader(value = "chatId") String chatId) {
        validate(dto.getChatId(), chatId);
        log.info("update {} from request with chatId {}", dto, chatId);
        return tinderUserService.update(dto, chatId);
    }

    @PostMapping(value = "/actions/like/{anotherChatId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void likeUser(@NotBlank @RequestHeader(value = "chatId") String chatId, @NotBlank @PathVariable String anotherChatId) {
        if (chatId.equals(anotherChatId)) {
            throw new NotAllowRequest("Not allowed to like yourself. ChatId " + chatId);
        }
        likeService.like(chatId, anotherChatId);
        log.info("like from {} to {}", chatId, anotherChatId);
    }

    @GetMapping(value = "/{chatId}")
    public TinderUserDto getUser(@NotBlank @RequestHeader(value = "chatId") String headerChatId, @NotBlank @PathVariable String chatId) {
        validate(chatId, headerChatId);
        TinderUserDto tinderUserDto = tinderUserService.get(chatId);
        log.info("Get user with chatId {}", chatId);
        return tinderUserDto;
    }

    //todo лучше будет перенести этот метод в какой нибудь класс ValidationUtils, в контроллере должны быть только ручки
    private void validate(String chatId, String headerChatId) {
        if (!headerChatId.equals(chatId)) {
            throw new NotAllowRequest("Header parameter chatId %s not equals path parameter %s".formatted(headerChatId, chatId));
        }
    }
}
