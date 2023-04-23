package ru.liga.prerevolutionary.tinderserver.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.liga.prerevolutionary.tinderserver.exception.NotFoundException;
import ru.liga.prerevolutionary.tinderserver.model.LikeId;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;
import ru.liga.prerevolutionary.tinderserver.repository.LikeRepository;
import ru.liga.prerevolutionary.tinderserver.repository.TinderUserRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LikeServiceImplTest {
    static TinderUser user1;
    static TinderUser user2;

    @Mock
    private TinderUserRepository tinderUserRepository;

    @Mock
    private LikeRepository likeRepository;
    @InjectMocks
    private LikeServiceImpl likeService;

    @BeforeAll
    public static void beforeAll() {
        user1 = new TinderUser();
        user1.setId(111);
        user1.setChatId("1");
        user2 = new TinderUser();
        user2.setId(222);
        user2.setChatId("2");
    }

    @Test
    void getLikeIdForNotExistUsers() {
        assertThatThrownBy(() -> likeService.getLikeId("1", "2"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void getLikeIdForUsers() {
        mockTinderUserRepositoryForGetUsers();
        assertThat(likeService.getLikeId("1", "2"))
                .isEqualTo(new LikeId(111, 222));
    }

    @Test
    void getRelatedForFirstUserLikeSecondUser() {
        //для нормального теста надо поднимать базу, так что по сути не тест, а документация
        Mockito.when(likeRepository.findRelate(new LikeId(111, 222))).thenReturn(Optional.of("Любим вами"));
        mockTinderUserRepositoryForGetUsers();

        assertThat(likeService.getRelated("1", "2"))
                .isEqualTo("Любим вами");
    }

    @Test
    void getRelatedForSecondUserLikeFirstUser() {
        //для нормального теста надо поднимать базу, так что по сути не тест, а документация
        Mockito.when(likeRepository.findRelate(new LikeId(111, 222))).thenReturn(Optional.of("Вы любимы"));
        mockTinderUserRepositoryForGetUsers();

        assertThat(likeService.getRelated("1", "2"))
                .isEqualTo("Вы любимы");
    }

    @Test
    void getRelatedForUsersLikeEachOther() {
        //для нормального теста надо поднимать базу, так что по сути не тест, а документация
        Mockito.when(likeRepository.findRelate(new LikeId(111, 222))).thenReturn(Optional.of("Взаимность"));
        mockTinderUserRepositoryForGetUsers();

        assertThat(likeService.getRelated("1", "2"))
                .isEqualTo("Взаимность");
    }

    @Test
    void getRelatedForUsersNotLikeEachOther() {
        //для нормального теста надо поднимать базу, так что по сути не тест, а документация
        Mockito.when(likeRepository.findRelate(new LikeId(111, 222))).thenReturn(Optional.empty());
        mockTinderUserRepositoryForGetUsers();

        assertThat(likeService.getRelated("1", "2"))
                .isEqualTo("");
    }

    @Test
    void setLikeForNotExistUsers() {
        assertThatThrownBy(() -> likeService.like("1", "2"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void setLikeForExistUsers() {
        mockTinderUserRepositoryForGetUsers();
        assertThatNoException().isThrownBy(() -> likeService.like("1", "2"));
    }

    private void mockTinderUserRepositoryForGetUsers() {
        Mockito.when(tinderUserRepository.findUserByChatId("1")).thenReturn(Optional.of(user1));
        Mockito.when(tinderUserRepository.findUserByChatId("2")).thenReturn(Optional.of(user2));
    }
}