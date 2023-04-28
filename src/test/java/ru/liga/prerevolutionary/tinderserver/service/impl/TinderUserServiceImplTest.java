package ru.liga.prerevolutionary.tinderserver.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.exception.DuplicatedEntityException;
import ru.liga.prerevolutionary.tinderserver.exception.NotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class TinderUserServiceImplTest {
    @Autowired
    private TinderUserServiceImpl tinderUserService;

    @Test
    void getTinderUserByChatId() {
        assertThat(tinderUserService.get("1"))
                .hasFieldOrPropertyWithValue("name", "Иванов Иван Иванович");
    }

    @Test
    void getNotExistTinderUserByChatId() {
        assertThatThrownBy(() -> tinderUserService.get("100500"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with chatId 100500 not found");
    }

    @Test
    void createNewTinderUser() {
        assertThatThrownBy(() -> tinderUserService.get("555555"))
                .isInstanceOf(NotFoundException.class);
        tinderUserService.create(new TinderUserDto("555555", "a", "MALE", "c", "d", "FEMALE"));
        assertThat(tinderUserService.get("555555"))
                .hasFieldOrPropertyWithValue("name", "a");
    }

    @Test
    void createDuplicatedTinderUser() {
        assertThat(tinderUserService.get("1"))
                .hasFieldOrPropertyWithValue("name", "Иванов Иван Иванович");
        assertThatThrownBy(() -> tinderUserService.create(new TinderUserDto("1", "a", "MALE", "c", "d", "FEMALE")))
                .isInstanceOf(DuplicatedEntityException.class)
                .hasMessageStartingWith("User with chatId already created 1 since");
    }

    @Test
    void updateExistTinderUser() {
        TinderUserDto dto;
        assertThat(dto = tinderUserService.get("1"))
                .hasFieldOrPropertyWithValue("name", "Иванов Иван Иванович");
        dto.setName("Петров Петр Петрович");
        tinderUserService.update(dto, "1");
        assertThat(tinderUserService.get("1"))
                .hasFieldOrPropertyWithValue("name", "Петров Петр Петрович");
    }

    @Test
    void updateNotExistTinderUser() {
        assertThatThrownBy(() -> tinderUserService.update(new TinderUserDto(), "123"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with chatId 123 not found");
    }

    @Test
    void getNextSearch() {
        assertThat(tinderUserService.getNextSearch("1"))
                .hasFieldOrPropertyWithValue("chatId", "3");
        assertThat(tinderUserService.getNextSearch("1"))
                .hasFieldOrPropertyWithValue("chatId", "5");
        assertThat(tinderUserService.getNextSearch("1"))
                .hasFieldOrPropertyWithValue("chatId", "7");
        assertThat(tinderUserService.getNextSearch("1"))
                .hasFieldOrPropertyWithValue("chatId", "10");
        assertThat(tinderUserService.getNextSearch("1"))
                .hasFieldOrPropertyWithValue("chatId", "3");
    }

    @Test
    void getNextSearchForNotExistTinderUser() {
        assertThatThrownBy(() -> tinderUserService.getNextSearch("123"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with chatId 123 not found");
    }

    @Test
    void getNextSearchForEmptySearch() {
        assertThatThrownBy(() -> tinderUserService.getNextSearch("9"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with chatId 9 have not next search user");
    }

    @Test
    void getPreviousSearch() {
        assertThat(tinderUserService.getPreviousSearch("1"))
                .hasFieldOrPropertyWithValue("chatId", "10");
        assertThat(tinderUserService.getPreviousSearch("1"))
                .hasFieldOrPropertyWithValue("chatId", "7");
        assertThat(tinderUserService.getPreviousSearch("1"))
                .hasFieldOrPropertyWithValue("chatId", "5");
        assertThat(tinderUserService.getPreviousSearch("1"))
                .hasFieldOrPropertyWithValue("chatId", "3");
        assertThat(tinderUserService.getPreviousSearch("1"))
                .hasFieldOrPropertyWithValue("chatId", "10");
    }

    @Test
    void getPreviousSearchForNotExistTinderUser() {
        assertThatThrownBy(() -> tinderUserService.getPreviousSearch("123"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with chatId 123 not found");
    }

    @Test
    void getPreviousSearchForEmptySearch() {
        assertThatThrownBy(() -> tinderUserService.getPreviousSearch("9"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with chatId 9 have not previous search user");
    }

    @Test
    void getNextView() {
        assertThat(tinderUserService.getNextView("1"))
                .hasFieldOrPropertyWithValue("chatId", "2");
        assertThat(tinderUserService.getNextView("1"))
                .hasFieldOrPropertyWithValue("chatId", "3");
        assertThat(tinderUserService.getNextView("1"))
                .hasFieldOrPropertyWithValue("chatId", "4");
        assertThat(tinderUserService.getNextView("1"))
                .hasFieldOrPropertyWithValue("chatId", "5");
        assertThat(tinderUserService.getNextView("1"))
                .hasFieldOrPropertyWithValue("chatId", "2");
    }

    @Test
    void getNextViewForNotExistTinderUser() {
        assertThatThrownBy(() -> tinderUserService.getNextView("123"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with chatId 123 not found");
    }

    @Test
    void getNextViewForEmptyView() {
        assertThatThrownBy(() -> tinderUserService.getNextView("9"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with chatId 9 have not next viewed user");
    }

    @Test
    void getPreviousView() {
        assertThat(tinderUserService.getPreviousView("1"))
                .hasFieldOrPropertyWithValue("chatId", "5");
        assertThat(tinderUserService.getPreviousView("1"))
                .hasFieldOrPropertyWithValue("chatId", "4");
        assertThat(tinderUserService.getPreviousView("1"))
                .hasFieldOrPropertyWithValue("chatId", "3");
        assertThat(tinderUserService.getPreviousView("1"))
                .hasFieldOrPropertyWithValue("chatId", "2");
        assertThat(tinderUserService.getPreviousView("1"))
                .hasFieldOrPropertyWithValue("chatId", "5");
    }

    @Test
    void getPreviousViewForNotExistTinderUser() {
        assertThatThrownBy(() -> tinderUserService.getPreviousView("123"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with chatId 123 not found");
    }

    @Test
    void getPreviousViewForEmptyView() {
        assertThatThrownBy(() -> tinderUserService.getPreviousView("9"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with chatId 9 have not previous viewed user");
    }
}