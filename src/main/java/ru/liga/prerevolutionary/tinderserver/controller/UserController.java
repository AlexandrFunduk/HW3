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
import ru.liga.prerevolutionary.tinderserver.validation.CheckRequest;

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
        log.info("Accept request to register client, request: {}", dto);
        return tinderUserService.create(dto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TinderUserDto updateUser(@Valid @RequestBody TinderUserDto dto, @NotBlank @RequestHeader(value = "chatId") String chatId) {
        CheckRequest.validateEqualChatId(dto.getChatId(), chatId);
        log.info("Accept request to update {} from request with chatId {}", dto, chatId);
        return tinderUserService.update(dto, chatId);
    }

    @PostMapping(value = "/actions/like/{anotherChatId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void likeUser(@NotBlank @RequestHeader(value = "chatId") String chatId, @NotBlank @PathVariable String anotherChatId) {
        if (chatId.equals(anotherChatId)) {
            throw new NotAllowRequest("Not allowed to like yourself. ChatId " + chatId);
        }
        likeService.like(chatId, anotherChatId);
    }

    @GetMapping(value = "/{chatId}")
    public TinderUserDto getUser(@NotBlank @RequestHeader(value = "chatId") String headerChatId, @NotBlank @PathVariable String chatId) {
        CheckRequest.validateEqualChatId(chatId, headerChatId);
        TinderUserDto tinderUserDto = tinderUserService.get(chatId);
        log.info("Get user with chatId {}", chatId);
        return tinderUserDto;
    }
}
