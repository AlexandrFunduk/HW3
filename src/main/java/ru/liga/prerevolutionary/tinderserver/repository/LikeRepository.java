package ru.liga.prerevolutionary.tinderserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prerevolutionary.tinderserver.model.Like;
import ru.liga.prerevolutionary.tinderserver.model.LikeId;

import java.util.List;

@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<Like, LikeId> {

    @Query(value = "SELECT * FROM TB_LIKE " +
            "WHERE (USER_ID = :#{#id.userId} and LIKE_ID = :#{#id.likedUserId} )" +
            "       or (USER_ID = :#{#id.likedUserId}  and LIKE_ID = :#{#id.userId})", nativeQuery = true)
    List<Like> findRelate(LikeId id);
}