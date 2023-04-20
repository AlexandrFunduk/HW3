package ru.liga.prerevolutionary.tinderserver.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "TB_LIKE")
public class Like {
    @EmbeddedId
    private LikeId id;

    public Like(Integer userId, Integer likedUserId) {
        this.id = new LikeId(userId, likedUserId);
    }
}