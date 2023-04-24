package ru.liga.prerevolutionary.tinderserver.dto.converter;

import org.springframework.stereotype.Component;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;

import java.util.Date;

@Component
public class DtoToTinderUserConverter implements Converter<TinderUserDto, TinderUser> {
    @Override
    public TinderUser convert(TinderUserDto dto) {
        return TinderUser.builder()
                .chatId(dto.getChatId())
                .name(dto.getName())
                .preference(sexConvert(dto.getPreference()))
                .sex(sexConvert(dto.getSex()))
                .header(dto.getHeader())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public TinderUser convert(TinderUserDto dto, TinderUser user) {
        user.setName(dto.getName());
        user.setPreference(sexConvert(dto.getPreference()));
        user.setSex(sexConvert(dto.getSex()));
        user.setHeader(dto.getHeader());
        user.setDescription(dto.getDescription());
        user.setUpdated(new Date());
        return user;
    }

    private String sexConvert(String sex) {
        try {
            Sex boxetSex = Sex.valueOf(sex);
            return switch (boxetSex) {
                case ALL -> "Все";
                case MALE -> "Сударь";
                case FEMALE -> "Сударыня";
            };
        } catch (IllegalArgumentException e) {
            return sex;
        }

    }
}
