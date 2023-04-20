package ru.liga.prerevolutionary.tinderserver.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TinderUserDto {
    @NotBlank
    private String chatId;
    @NotBlank
    private String name;
    @NotBlank
    private String sex;
    @NotBlank
    private String header;
    @NotBlank
    private String description;
    @NotBlank
    private String preference;
}
