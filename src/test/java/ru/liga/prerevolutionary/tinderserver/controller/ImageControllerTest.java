package ru.liga.prerevolutionary.tinderserver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getImage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/image").header("chatId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value("2"))
                .andExpect(jsonPath("$.image").exists());
    }

    @Test
    void searchNext() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/image/search/next").header("chatId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value("3"))
                .andExpect(jsonPath("$.image").exists());
    }

    @Test
    void searchPrevious() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/image/search/previous").header("chatId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value("10"))
                .andExpect(jsonPath("$.image").exists());
    }

    @Test
    void viewNext() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/image/view/next").header("chatId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value("2"))
                .andExpect(jsonPath("$.image").exists());
    }

    @Test
    void viewPrevious() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/image/view/previous").header("chatId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value("5"))
                .andExpect(jsonPath("$.image").exists());
    }
}