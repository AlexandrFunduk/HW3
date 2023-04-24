package ru.liga.prerevolutionary.tinderserver.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "chatId": "777",
                                    "name": "55",
                                    "sex": "sex",
                                    "header": "header",
                                    "description": "description",
                                    "preference": "preference"
                                }""")
                )
                .andExpect(status().isCreated());
    }

    @Test
    void createDuplicateUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "chatId": "1",
                                    "name": "55",
                                    "sex": "sex",
                                    "header": "header",
                                    "description": "description",
                                    "preference": "preference"
                                }""")
                )
                .andExpect(status().isConflict());
    }

    @Test
    void updateUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .header("chatId", "7")
                        .header("Content-Type", "application/json")
                        .header("Content-Length", "0")
                        .content("""
                                {
                                    "chatId": "7",
                                    "name": "55",
                                    "sex": "sex",
                                    "header": "header",
                                    "description": "description",
                                    "preference": "preference"
                                }""")
                )
                .andExpect(status().isOk());
    }

    @Test
    void updateNotExistUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .header("chatId", "88888")
                        .header("Content-Type", "application/json")
                        .header("Content-Length", "0")
                        .content("""
                                {
                                    "chatId": "7",
                                    "name": "55",
                                    "sex": "sex",
                                    "header": "header",
                                    "description": "description",
                                    "preference": "preference"
                                }""")
                )
                .andExpect(status().isConflict());
    }

    @Test
    void likeUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/actions/like/6")
                        .header("chatId", "7")
                        .header("Content-Type", "application/json")
                        .header("Content-Length", "0")
                )
                .andExpect(status().isCreated());
    }

    @Test
    void likeUserNotExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/actions/like/6")
                        .header("chatId", "555")
                        .header("Content-Type", "application/json")
                        .header("Content-Length", "0")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void getUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/2").header("chatId", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "chatId": "2",
                            "name": "Петрова Ольга Сергеевна",
                            "sex": "ж",
                            "header": "Ищу надежного партнера",
                            "description": "Я - уверенная в себе женщина с чувством юмора",
                            "preference": "ж"
                        }"""));
    }

    @Test
    void getUserWithNotEqualsHeaderAndBodyChatId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/33333").header("chatId", "2"))
                .andExpect(status().isConflict());
    }

    @Test
    void getNotExistUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/33333").header("chatId", "33333"))
                .andExpect(status().isNotFound());
    }


}