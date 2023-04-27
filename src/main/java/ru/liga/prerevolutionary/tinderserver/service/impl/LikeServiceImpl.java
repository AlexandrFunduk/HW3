package ru.liga.prerevolutionary.tinderserver.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionary.tinderserver.exception.NotFoundException;
import ru.liga.prerevolutionary.tinderserver.model.Like;
import ru.liga.prerevolutionary.tinderserver.model.LikeId;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;
import ru.liga.prerevolutionary.tinderserver.repository.LikeRepository;
import ru.liga.prerevolutionary.tinderserver.repository.TinderUserRepository;
import ru.liga.prerevolutionary.tinderserver.service.LikeService;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final TinderUserRepository tinderUserRepository;
    private final LikeRepository likeRepository;

    public LikeId getLikeId(String chatId, String anotherChatId) {
        //todo можно вынести создание message для ексепшена в статический метод и использовать его везде
        // у тебя много где повторяется одно и тоже
        TinderUser user = tinderUserRepository.findUserByChatId(chatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(chatId)));
        TinderUser anotherUser = tinderUserRepository.findUserByChatId(anotherChatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(anotherChatId)));
        return new LikeId(user.getId(), anotherUser.getId());
    }

    public String getRelated(String chatId, String anotherChatId) {
        LikeId likeId = getLikeId(chatId, anotherChatId);
        return likeRepository.findRelate(likeId).orElse("");
    }

    //todo у тебя здесь есть аннотация, а у методов выше нет, лучше делать чтобы все было одинаково
    //либо добавить аннотации выше, либо убрать здесь
    @Override
    public void like(String chatId, String anotherChatId) {
        TinderUser user = tinderUserRepository.findUserByChatId(chatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(chatId)));
        TinderUser anotherUser = tinderUserRepository.findUserByChatId(anotherChatId).orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(anotherChatId)));
        Like like = new Like(user.getId(), anotherUser.getId());
        likeRepository.save(like);
    }
}
