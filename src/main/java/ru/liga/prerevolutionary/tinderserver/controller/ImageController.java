package ru.liga.prerevolutionary.tinderserver.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prerevolutionary.tinderserver.dto.ImageDto;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/users/image")
@AllArgsConstructor
public class ImageController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getImage(@RequestHeader("chatId") String chatId) {
        return tempMock();
    }

    @GetMapping(value = "/next", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getNext(@RequestHeader("chatId") String chatId) {
        return tempMock();
    }

    @GetMapping(value = "/previous", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getPrevious(@RequestHeader("chatId") String chatId) {
        return tempMock();
    }


    private ImageDto tempMock() {
        String encodedString = null;
        try {
            encodedString = Base64.getEncoder().encodeToString(this.getClass().getClassLoader().getResourceAsStream("image/prerev-background.jpg").readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ImageDto(encodedString);
    }
}
