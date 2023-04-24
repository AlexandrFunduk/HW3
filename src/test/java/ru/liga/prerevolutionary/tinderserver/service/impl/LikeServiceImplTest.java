package ru.liga.prerevolutionary.tinderserver.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.liga.prerevolutionary.tinderserver.exception.NotFoundException;
import ru.liga.prerevolutionary.tinderserver.model.LikeId;

import static org.assertj.core.api.AssertionsForClassTypes.*;


@SpringBootTest
class LikeServiceImplTest {
    @Autowired
    private LikeServiceImpl likeService;

    @Test
    void getLikeIdForNotExistUsers() {
        assertThatThrownBy(() -> likeService.getLikeId("123", "2"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with chatId 123 not found");
    }

    @Test
    void getLikeIdForUsers() {
        assertThat(likeService.getLikeId("1", "2"))
                .isEqualTo(new LikeId(1, 2));
    }

    @Test
    void getRelatedForFirstUserLikeSecondUser() {
        assertThat(likeService.getRelated("1", "2"))
                .isEqualTo("Любим вами");
    }

    @Test
    void getRelatedForSecondUserLikeFirstUser() {
        assertThat(likeService.getRelated("1", "5"))
                .isEqualTo("Вы любимы");
    }

    @Test
    void getRelatedForUsersLikeEachOther() {
        assertThat(likeService.getRelated("1", "4"))
                .isEqualTo("Взаимность");
    }

    @Test
    void getRelatedForUsersNotLikeEachOther() {
        assertThat(likeService.getRelated("1", "9"))
                .isEqualTo("");
    }

    @Test
    void setLikeForNotExistUsers() {
        assertThatThrownBy(() -> likeService.like("1", "123"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with chatId 123 not found");
    }

    @Test
    void setLikeForExistUsers() {
        assertThatNoException().isThrownBy(() -> likeService.like("6", "7"));
    }
}