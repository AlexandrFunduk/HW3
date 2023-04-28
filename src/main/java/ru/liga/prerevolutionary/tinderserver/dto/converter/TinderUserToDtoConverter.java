package ru.liga.prerevolutionary.tinderserver.dto.converter;

import org.springframework.stereotype.Component;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;

@Component
public class TinderUserToDtoConverter implements Converter<TinderUser, TinderUserDto> {

    @Override
    public TinderUserDto convert(TinderUser user) {
        return new TinderUserDto(user.getChatId(), user.getName(), sexConvert(user.getSex()), user.getHeader(), user.getDescription(), sexConvert(user.getPreference()));
    }

    @Override
    public TinderUserDto convert(TinderUser user, TinderUserDto dto) {
        dto.setChatId(user.getChatId());
        dto.setName(user.getName());
        dto.setSex(sexConvert(user.getSex()));
        dto.setHeader(user.getHeader());
        dto.setDescription(user.getDescription());
        dto.setPreference(sexConvert(user.getPreference()));
        return dto;
    }

    private String sexConvert(String sex) {
        try {
            return Sex.ofTitle(sex).name();
        } catch (IllegalArgumentException e) {
            return "";
        }
    }
}
