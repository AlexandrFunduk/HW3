package ru.liga.prerevolutionary.tinderserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prerevolutionary.tinderserver.model.Like;
import ru.liga.prerevolutionary.tinderserver.model.LikeId;

import java.util.Optional;

@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<Like, LikeId> {

    //todo зачем здесь логика на sql? сложно разобраться что здесь
    // лучше сделать основной селект, а логику вынести в джаву
    @Query(value = "SELECT case " +
            "           when s = 3 then 'Взаимность' " +
            "           when s = 1 then 'Любим вами' " +
            "           when s = 2 then 'Вы любимы' " +
            "       end " +
            "FROM ( SELECT sum(c) as s " +
            "       FROM (  SELECT case " +
            "                           when r.id = :#{#id.userId} THEN 1 " +
            "                           when r.id = :#{#id.likedUserId}  THEN 2 " +
            "                       end as c " +
            "               FROM (  SELECT USER_ID as id " +
            "                       FROM TB_LIKE " +
            "                       WHERE (USER_ID = :#{#id.userId} and LIKE_ID = :#{#id.likedUserId} ) " +
            "                               or (USER_ID = :#{#id.likedUserId}  and LIKE_ID = :#{#id.userId}) " +
            "               group by USER_ID) as r))", nativeQuery = true)
    Optional<String> findRelate(LikeId id);

}