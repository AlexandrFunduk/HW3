package ru.liga.prerevolutionary.tinderserver.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionary.tinderserver.model.Like;
import ru.liga.prerevolutionary.tinderserver.model.LikeId;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;
import ru.liga.prerevolutionary.tinderserver.repository.LikeRepository;
import ru.liga.prerevolutionary.tinderserver.repository.TinderUserRepository;
import ru.liga.prerevolutionary.tinderserver.service.LikeService;

@Slf4j
@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final TinderUserRepository tinderUserRepository;
    private final LikeRepository likeRepository;

    @Override
    public LikeId getLikeId(String chatId, String anotherChatId) {
        TinderUser user = tinderUserRepository.getUserByChatId(chatId);
        TinderUser anotherUser = tinderUserRepository.getUserByChatId(anotherChatId);
        return new LikeId(user.getId(), anotherUser.getId());
    }

    @Override
    public String getRelated(String chatId, String anotherChatId) {
        LikeId likeId = getLikeId(chatId, anotherChatId);
        return likeRepository.findRelate(likeId).orElse("");
    }

    @Override
    public void like(String chatId, String anotherChatId) {
        log.info("like from {} to {}", chatId, anotherChatId);
        TinderUser user = tinderUserRepository.getUserByChatId(chatId);
        TinderUser anotherUser = tinderUserRepository.getUserByChatId(anotherChatId);
        Like like = new Like(user.getId(), anotherUser.getId());
        likeRepository.save(like);
    }
}
