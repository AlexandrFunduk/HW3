package ru.liga.prerevolutionary.tinderserver.dto.converter;

import org.springframework.stereotype.Component;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class TinderUserDtoToStrings implements Converter<TinderUserDto, List<String>> {

    @Override
    public List<String> convert(TinderUserDto dto) {
        List<String> result = new ArrayList<>();
        result.add(dto.getName());
        result.add(dto.getHeader());
        result.add(dto.getDescription());
        result.add(dto.getSex());
        result.add(dto.getPreference());
        return result;
    }

    @Override
    public List<String> convert(TinderUserDto dto, List<String> strings) {
        strings = convert(dto);
        return strings;
    }
}
