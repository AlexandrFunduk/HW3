package ru.liga.prerevolutionary.tinderserver.dto.converter;

import org.springframework.stereotype.Component;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;

import java.util.List;

@Component
public class StringsToTinderUserDto implements Converter<List<String>, TinderUserDto> {
    @Override
    public TinderUserDto convert(List<String> strings) {
        TinderUserDto dto = new TinderUserDto();
        convert(strings, dto);
        return dto;
    }

    @Override
    public TinderUserDto convert(List<String> strings, TinderUserDto dto) {
        dto.setName(strings.get(0));
        dto.setHeader(strings.get(1));
        dto.setDescription(strings.get(2));
        dto.setSex(strings.get(3));
        dto.setPreference(strings.get(4));
        return dto;
    }
}
