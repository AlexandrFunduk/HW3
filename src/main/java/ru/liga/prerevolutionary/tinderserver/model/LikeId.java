package ru.liga.prerevolutionary.tinderserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.util.ProxyUtils;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "like_id")
    private Integer likedUserId;

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        LikeId that = (LikeId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(likedUserId, that.likedUserId);
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userId = this.userId;
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $likedUserId = this.likedUserId;
        result = result * PRIME + ($likedUserId == null ? 43 : $likedUserId.hashCode());
        return result;
    }
}
