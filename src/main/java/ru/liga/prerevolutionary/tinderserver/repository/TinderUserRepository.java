package ru.liga.prerevolutionary.tinderserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface TinderUserRepository extends JpaRepository<TinderUser, Integer> {
    Optional<TinderUser> findUserByChatId(String chatId);

    @Query(value = "SELECT top 1 * FROM TB_TINDER_USER WHERE ID > :#{#lastViewedUser} AND ID <> :#{#userId} " +
            "AND ID IN :#{#relatedIds} " +
            "ORDER BY ID ", nativeQuery = true)
    Optional<TinderUser> findTopByIdGreaterThanAndIdNotAndIdIn(Integer lastViewedUser, Integer userId, List<Integer> relatedIds);

    @Query(value = "SELECT top 1 * FROM TB_TINDER_USER WHERE ID < :#{#lastViewedUser} AND ID <> :#{#userId} " +
            "AND ID IN :#{#relatedIds} " +
            "ORDER BY ID DESC", nativeQuery = true)
    Optional<TinderUser> findTopByIdLessThanAndIdNotAndIdIn(Integer lastViewedUser, Integer userId, List<Integer> relatedIds);

    @Query(value = "select * from ( " +
            "            (select USER_ID as id from TB_LIKE where LIKE_ID = :#{#userId}) " +
            "            union all " +
            "            (select LIKE_ID as id from TB_LIKE where USER_ID = :#{#userId}) " +
            ") group by id", nativeQuery = true)
    List<Integer> findRelatedIds(Integer userId);


    @Query(value = "SELECT top 1 * FROM TB_TINDER_USER " +
            "WHERE ID <> :#{#user.id} AND (PREFERENCE = :#{#user.sex} OR PREFERENCE = 'Все') AND (SEX = :#{#user.preference} OR 'Все'= :#{#user.preference}) AND ID > :#{#user.lastFoundUser.id} " +
            "ORDER BY ID", nativeQuery = true)
    Optional<TinderUser> findNextSearchedUser(TinderUser user);

    @Query(value = "SELECT top 1 * FROM TB_TINDER_USER " +
            "WHERE ID <> :#{#user.id} AND (PREFERENCE = :#{#user.sex} OR PREFERENCE = 'Все') AND (SEX = :#{#user.preference} OR 'Все'= :#{#user.preference}) AND ID < :#{#user.lastFoundUser.id} " +
            "ORDER BY ID DESC", nativeQuery = true)
    Optional<TinderUser> findPreviousSearchedUser(TinderUser user);
}
