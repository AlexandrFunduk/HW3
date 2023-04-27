package ru.liga.prerevolutionary.tinderserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;

import java.util.Optional;

@Transactional(readOnly = true)
public interface TinderUserRepository extends JpaRepository<TinderUser, Integer> {
    Optional<TinderUser> findUserByChatId(String chatId);

    //todo выглядит очень сложно и непонятно, думаю лучше сделать селект попроще, а потом уже найти нужную запись используя джаву
    @Query(value = "SELECT top 1 * FROM TB_TINDER_USER WHERE ID > :#{#user.lastViewedUser.id} AND ID <> :#{#user.id} " +
            "AND ID IN (select * from (select * from ( " +
            "                              (select USER_ID as id from TB_LIKE where LIKE_ID = :#{#user.chatId}) " +
            "                               union all " +
            "                              (select LIKE_ID as id from TB_LIKE where USER_ID = :#{#user.chatId})) " +
            "               group by id))" +
            "ORDER BY ID", nativeQuery = true)
    Optional<TinderUser> findNextRelatedUser(TinderUser user);

    @Query(value = "SELECT top 1 * FROM TB_TINDER_USER WHERE ID < :#{#user.lastViewedUser.id} AND ID <> :#{#user.id} " +
            "AND ID IN (select * from (select * from ( " +
            "                              (select USER_ID as id from TB_LIKE where LIKE_ID = :#{#user.id}) " +
            "                               union all " +
            "                              (select LIKE_ID as id from TB_LIKE where USER_ID = :#{#user.id})) " +
            "               group by id))" +
            "ORDER BY ID DESC", nativeQuery = true)
    Optional<TinderUser> findPreviousRelatedUser(TinderUser user);


    @Query(value = "SELECT top 1 * FROM TB_TINDER_USER " +
            //todo кириллица никогда не должна быть в код, сейчас можешь оставить, но имей в виду
            //вместо русс букв в коде можешь использовать MessageService + message.properties
            "WHERE ID <> :#{#user.id} AND (PREFERENCE = :#{#user.sex} OR PREFERENCE = 'все') AND (SEX = :#{#user.preference} OR 'все'= :#{#user.preference}) AND ID > :#{#user.lastFoundUser.id} " +
            "ORDER BY ID", nativeQuery = true)
    Optional<TinderUser> findNextSearchedUser(TinderUser user);

    @Query(value = "SELECT top 1 * FROM TB_TINDER_USER " +
            "WHERE ID <> :#{#user.id} AND (PREFERENCE = :#{#user.sex} OR PREFERENCE = 'все') AND (SEX = :#{#user.preference} OR 'все'= :#{#user.preference}) AND ID < :#{#user.lastFoundUser.id} " +
            "ORDER BY ID DESC", nativeQuery = true)
    Optional<TinderUser> findPreviousSearchedUser(TinderUser user);
}
