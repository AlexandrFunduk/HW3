package ru.liga.prerevolutionary.tinderserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TinderUserDto {
    private String chatId;
    private String name;
    private String sex;
    private String header;
    private String description;
    private String preference;
}
