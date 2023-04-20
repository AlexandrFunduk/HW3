package ru.liga.prerevolutionary.tinderserver.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.dto.converter.Converter;
import ru.liga.prerevolutionary.tinderserver.exception.DuplicatedEntityException;
import ru.liga.prerevolutionary.tinderserver.exception.NotFoundException;
import ru.liga.prerevolutionary.tinderserver.model.Like;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;
import ru.liga.prerevolutionary.tinderserver.repository.LikeRepository;
import ru.liga.prerevolutionary.tinderserver.repository.TinderUserRepository;
import ru.liga.prerevolutionary.tinderserver.service.TinderUserService;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TinderUserServiceImpl implements TinderUserService {
    private final TinderUserRepository tinderUserRepository;
    private final LikeRepository likeRepository;
    private final Converter<TinderUserDto, TinderUser> reversConverter;
    private final Converter<TinderUser, TinderUserDto> converter;

    @Override
    public TinderUserDto get(String chatId) {
        TinderUser user = tinderUserRepository.findUserByChatId(chatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(chatId)));
        return converter.convert(user);
    }

    @Override
    public TinderUserDto create(TinderUserDto tinderUserDto) {
        //validate dto
        tinderUserRepository.findUserByChatId(tinderUserDto.getChatId()).ifPresent(user -> {
            throw new DuplicatedEntityException("User with chatId already created %s since %s".formatted(user.getChatId(), user.getRegistered()));
        });
        TinderUser user = reversConverter.convert(tinderUserDto);
        user.setLastFoundUser(user);
        user.setRegistered(new Date());
        tinderUserRepository.save(user);
        return converter.convert(user);
    }

    @Override
    public TinderUserDto update(TinderUserDto tinderUserDto, String chatId) {
        //validate dto
        TinderUser oldTinderUSer = tinderUserRepository.findUserByChatId(chatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(chatId)));
        TinderUser updatedTinderUSer = reversConverter.convert(tinderUserDto, oldTinderUSer);
        tinderUserRepository.save(updatedTinderUSer);
        return converter.convert(updatedTinderUSer);
    }

    @Override
    public void like(String chatId, String anotherChatId) {
        TinderUser user = tinderUserRepository.findUserByChatId(chatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(chatId)));
        TinderUser anotherUser = tinderUserRepository.findUserByChatId(anotherChatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(anotherChatId)));
        Like like = new Like(user.getId(), anotherUser.getId());
        likeRepository.save(like);
    }

    @Override
    public TinderUserDto getNextSearch(String chatId) {
        TinderUser user = tinderUserRepository.findUserByChatId(chatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(chatId)));
        Optional<TinderUser> maybeNextSearchedUser = tinderUserRepository.findNextSearchedUser(user);
        if (maybeNextSearchedUser.isEmpty()) {
            TinderUser userWithNegativeChatId = new TinderUser();
            userWithNegativeChatId.setId(-1);
            user.setLastFoundUser(userWithNegativeChatId);
            maybeNextSearchedUser = tinderUserRepository.findNextSearchedUser(user);
        }
        TinderUser nextSearchedUser = maybeNextSearchedUser.orElseThrow(() -> new NotFoundException("User with chatId %s have not next search user".formatted(chatId)));
        user.setLastFoundUser(nextSearchedUser);
        tinderUserRepository.save(user);
        return converter.convert(nextSearchedUser);
    }

    @Override
    public TinderUserDto getPreviousSearch(String chatId) {
        TinderUser user = tinderUserRepository.findUserByChatId(chatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(chatId)));
        Optional<TinderUser> maybePreviousSearchedUser = tinderUserRepository.findPreviousSearchedUser(user);
        if (maybePreviousSearchedUser.isEmpty()) {
            TinderUser userWithMaxChatId = new TinderUser();
            userWithMaxChatId.setId(Integer.MAX_VALUE);
            user.setLastFoundUser(userWithMaxChatId);
            maybePreviousSearchedUser = tinderUserRepository.findPreviousSearchedUser(user);
        }
        TinderUser previousSearchedUser = maybePreviousSearchedUser.orElseThrow(() -> new NotFoundException("User with chatId %s have not previous search user".formatted(chatId)));
        user.setLastFoundUser(previousSearchedUser);
        tinderUserRepository.save(user);
        return converter.convert(previousSearchedUser);
    }

    @Override
    public TinderUserDto getNextView(String chatId) {
        TinderUser user = tinderUserRepository.findUserByChatId(chatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(chatId)));
        Optional<TinderUser> maybeNextViewedUser = tinderUserRepository.findNextRelatedUser(user);
        if (maybeNextViewedUser.isEmpty()) {
            TinderUser userWithNegativeChatId = new TinderUser();
            userWithNegativeChatId.setId(-1);
            user.setLastViewedUser(userWithNegativeChatId);
            maybeNextViewedUser = tinderUserRepository.findNextRelatedUser(user);
        }
        TinderUser nextViewedUser = maybeNextViewedUser.orElseThrow(() -> new NotFoundException("User with chatId %s have not next viewed user".formatted(chatId)));
        user.setLastViewedUser(nextViewedUser);
        tinderUserRepository.save(user);
        return converter.convert(nextViewedUser);
    }

    @Override
    public TinderUserDto getPreviousView(String chatId) {
        TinderUser user = tinderUserRepository.findUserByChatId(chatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(chatId)));
        Optional<TinderUser> maybePreviousViewedUser = tinderUserRepository.findPreviousRelatedUser(user);
        if (maybePreviousViewedUser.isEmpty()) {
            TinderUser userWithMaxChatId = new TinderUser();
            userWithMaxChatId.setId(Integer.MAX_VALUE);
            user.setLastViewedUser(userWithMaxChatId);
            maybePreviousViewedUser = tinderUserRepository.findPreviousRelatedUser(user);
        }
        TinderUser previousViewedUser = maybePreviousViewedUser.orElseThrow(() -> new NotFoundException("User with chatId %s have not previous viewed user".formatted(chatId)));
        user.setLastViewedUser(previousViewedUser);
        tinderUserRepository.save(user);
        return converter.convert(previousViewedUser);
    }
}
