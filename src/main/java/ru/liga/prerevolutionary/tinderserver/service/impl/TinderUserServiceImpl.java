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
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TinderUserServiceImpl implements TinderUserService {
    private final TinderUserRepository tinderUserRepository;
    private final Converter<TinderUserDto, TinderUser> reversConverter;
    private final Converter<TinderUser, TinderUserDto> converter;

    @Override
    public TinderUserDto get(String chatId) {
        TinderUser user = tinderUserRepository.getUserByChatId(chatId);
        return converter.convert(user);
    }

    @Override
    public TinderUserDto create(TinderUserDto tinderUserDto) {
        //validate dto
        tinderUserRepository.findUserByChatId(tinderUserDto.getChatId()).ifPresent(user -> {
            throw new DuplicatedEntityException(getDuplicateExceptionMessage(user));
        });
        TinderUser user = reversConverter.convert(tinderUserDto);
        user.setLastFoundUser(user);
        user.setRegistered(new Date());
        save(user);
        return converter.convert(user);
    }

    private String getDuplicateExceptionMessage(TinderUser user) {
        return "User with chatId already created %s since %s".formatted(user.getChatId(), user.getRegistered());
    }

    private void save(TinderUser user) {
        log.debug("Save user {}", user);
        tinderUserRepository.save(user);
    }

    @Override
    public TinderUserDto update(TinderUserDto tinderUserDto, String chatId) {
        //validate dto
        TinderUser oldTinderUSer = tinderUserRepository.getUserByChatId(chatId);
        TinderUser updatedTinderUSer = reversConverter.convert(tinderUserDto, oldTinderUSer);
        save(updatedTinderUSer);
        return converter.convert(updatedTinderUSer);
    }


    @Override
    public TinderUserDto getNextSearch(String chatId) {
        TinderUser user = tinderUserRepository.getUserByChatId(chatId);
        TinderUser nextSearchedUser = tinderUserRepository.findNextSearchedUser(user)
                .or(() -> findFromStart(user))
                .orElseThrow(() -> new NotFoundException("User with chatId %s have not next search user".formatted(chatId)));
        user.setLastFoundUser(nextSearchedUser);
        save(user);
        return converter.convert(nextSearchedUser);
    }

    private Optional<TinderUser> findFromStart(TinderUser user) {
        TinderUser userWithNegativeChatId = new TinderUser();
        userWithNegativeChatId.setId(-1);
        user.setLastFoundUser(userWithNegativeChatId);
        return tinderUserRepository.findNextSearchedUser(user);
    }

    @Override
    public TinderUserDto getPreviousSearch(String chatId) {
        TinderUser user = tinderUserRepository.getUserByChatId(chatId);
        TinderUser previousSearchedUser = tinderUserRepository.findPreviousSearchedUser(user)
                .or(() -> findFromAnd(user))
                .orElseThrow(() -> new NotFoundException("User with chatId %s have not previous search user".formatted(chatId)));
        user.setLastFoundUser(previousSearchedUser);
        save(user);
        return converter.convert(previousSearchedUser);
    }

    private Optional<TinderUser> findFromAnd(TinderUser user) {
        TinderUser userWithMaxChatId = new TinderUser();
        userWithMaxChatId.setId(Integer.MAX_VALUE);
        user.setLastFoundUser(userWithMaxChatId);
        return tinderUserRepository.findPreviousSearchedUser(user);
    }

    @Override
    public TinderUserDto getNextView(String chatId) {
        TinderUser user = tinderUserRepository.getUserByChatId(chatId);
        Optional<TinderUser> maybeNextViewedUser = tinderUserRepository.findNextRelatedUser(user);
        if (maybeNextViewedUser.isEmpty()) {
            TinderUser userWithNegativeChatId = new TinderUser();
            userWithNegativeChatId.setId(-1);
            user.setLastViewedUser(userWithNegativeChatId);
            maybeNextViewedUser = tinderUserRepository.findNextRelatedUser(user);
        }
        TinderUser nextViewedUser = maybeNextViewedUser
                .orElseThrow(() -> new NotFoundException("User with chatId %s have not next viewed user".formatted(chatId)));
        user.setLastViewedUser(nextViewedUser);
        save(user);
        return converter.convert(nextViewedUser);
    }

    @Override
    public TinderUserDto getPreviousView(String chatId) {
        TinderUser user = tinderUserRepository.getUserByChatId(chatId);
        Optional<TinderUser> maybePreviousViewedUser = tinderUserRepository.findPreviousRelatedUser(user);
        if (maybePreviousViewedUser.isEmpty()) {
            TinderUser userWithMaxChatId = new TinderUser();
            userWithMaxChatId.setId(Integer.MAX_VALUE);
            user.setLastViewedUser(userWithMaxChatId);
            maybePreviousViewedUser = tinderUserRepository.findPreviousRelatedUser(user);
        }
        TinderUser previousViewedUser = maybePreviousViewedUser
                .orElseThrow(() -> new NotFoundException("User with chatId %s have not previous viewed user".formatted(chatId)));
        user.setLastViewedUser(previousViewedUser);
        save(user);
        return converter.convert(previousViewedUser);
    }
}
