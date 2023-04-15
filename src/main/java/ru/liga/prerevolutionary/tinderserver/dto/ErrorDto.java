package ru.liga.prerevolutionary.tinderserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorDto {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String error;

    public ErrorDto(String error) {
        this.error = error;
    }
}
