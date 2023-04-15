package ru.liga.prerevolutionary.tinderserver.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.dto.converter.Converter;
import ru.liga.prerevolutionary.tinderserver.exception.DuplicatedEntityException;
import ru.liga.prerevolutionary.tinderserver.exception.NotFoundException;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;
import ru.liga.prerevolutionary.tinderserver.repository.TinderUserRepository;
import ru.liga.prerevolutionary.tinderserver.service.TinderUserService;

import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class TinderUserServiceImpl implements TinderUserService {
    private final TinderUserRepository tinderUserRepository;
    private final Converter<TinderUserDto, TinderUser> converter;

    @Override
    public void create(TinderUserDto tinderUserDto) {
        //validate dto
        tinderUserRepository.findUserByChatId(tinderUserDto.getChatId()).ifPresent(user -> {
            throw new DuplicatedEntityException("User with chatId already created %s since %s".formatted(user.getChatId(), user.getRegistered()));
        });
        TinderUser user = converter.convert(tinderUserDto);
        user.setRegistered(new Date());
        tinderUserRepository.save(user);
    }

    @Override
    public void update(TinderUserDto tinderUserDto, String chatId) {
        //validate dto
        TinderUser oldTinderUSer = tinderUserRepository.findUserByChatId(chatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(chatId)));
        TinderUser updatedTinderUSer = converter.convert(tinderUserDto, oldTinderUSer);
        tinderUserRepository.save(updatedTinderUSer);
    }

    @Override
    public void like(String chatId, String anotherChatId) {
        log.error("Метод like в разработке");
    }
}
