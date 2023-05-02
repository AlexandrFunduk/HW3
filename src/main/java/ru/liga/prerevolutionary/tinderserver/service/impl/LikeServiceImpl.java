package ru.liga.prerevolutionary.tinderserver.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionary.tinderserver.model.Like;
import ru.liga.prerevolutionary.tinderserver.model.LikeId;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;
import ru.liga.prerevolutionary.tinderserver.repository.LikeRepository;
import ru.liga.prerevolutionary.tinderserver.service.LikeService;
import ru.liga.prerevolutionary.tinderserver.service.TinderUserService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final TinderUserService tinderUserService;
    private final LikeRepository likeRepository;

    @Override
    public LikeId getLikeId(String chatId, String anotherChatId) {
        TinderUser user = tinderUserService.getUserByChatId(chatId);
        TinderUser anotherUser = tinderUserService.getUserByChatId(anotherChatId);
        return new LikeId(user.getId(), anotherUser.getId());
    }

    @Override
    public String getRelated(String chatId, String anotherChatId) {
        LikeId id = getLikeId(chatId, anotherChatId);
        List<Like> relate = likeRepository.findRelate(id);
        if (relate.isEmpty()) {
            return "";
        }
        if (relate.size() == 1) {
            if (id.getUserId().equals(relate.get(0).getId().getUserId())) {
                return "Любим вами";
            } else {
                return "Вы любимы";
            }
        }
        return "Взаимность";
    }

    @Override
    public void like(String chatId, String anotherChatId) {
        log.info("like from {} to {}", chatId, anotherChatId);
        TinderUser user = tinderUserService.getUserByChatId(chatId);
        TinderUser anotherUser = tinderUserService.getUserByChatId(anotherChatId);
        Like like = new Like(user.getId(), anotherUser.getId());
        likeRepository.save(like);
    }
}
