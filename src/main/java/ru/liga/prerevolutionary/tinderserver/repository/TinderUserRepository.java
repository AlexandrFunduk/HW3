package ru.liga.prerevolutionary.tinderserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;

import java.util.Optional;

@Transactional(readOnly = true)
public interface TinderUserRepository extends JpaRepository<TinderUser, Integer> {
    Optional<TinderUser> findUserByChatId(String chatId);
}
