package ru.liga.prerevolutionary.tinderserver.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.service.TinderUserService;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final TinderUserService tinderUserService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody TinderUserDto user) {
        log.info("register {}", user);
        tinderUserService.create(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody TinderUserDto dto, @RequestHeader("chatId") String chatId) {
        log.info("update {} from request with chatId {}", dto, chatId);
        //validate
        tinderUserService.update(dto, chatId);
    }

    @PostMapping(value = "/like/{anotherChatId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void likeUser(@RequestHeader("chatId") String chatId, @PathVariable String anotherChatId) {
        log.info("like from {} to {}", chatId, anotherChatId);
        tinderUserService.like(chatId, anotherChatId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getUser() {
        return new TinderUserDto("123", "name", "sex", "header", "description", "preference");
    }
}
