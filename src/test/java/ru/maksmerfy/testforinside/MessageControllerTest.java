package ru.maksmerfy.testforinside;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/messages-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImV4cCI6MTYzNjU3ODAwMH0.b6TFQ4WnnWMwF7yOc2gLG4Y0SwEQlF61OM5Ll1oHIpSAzdFcnnstxgyT7Grlegcpw5ArBFjgXT5etbXKGGo4Ew";
    private final List<String> nullList = null;


    @Test
    void messageController() throws Exception{
        this.mockMvc.perform(post("/message"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    void messageControllerWrongToken() throws Exception{
        this.mockMvc.perform(post("/message")
                        .header("authorization", "Bearer ")
                        .content("{ \"name\": \"testUser\", \"message\": \"\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    void messageControllerWrongLogin() throws Exception{
        this.mockMvc.perform(post("/message")
                        .header("authorization", "Bearer " + token)
                        .content("{ \"name\": \"User\", \"message\": \"\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.data").value(nullList));

    }

    @Test
    void messageControllerAddMessage() throws Exception{
        this.mockMvc.perform(post("/message")
                        .header("authorization", "Bearer " + token)
                        .content("{ \"name\": \"testUser\", \"message\": \"my test message\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.data").value(nullList));

    }

    @Test
    void messageControllerReadHistory() throws Exception{
        List<String> listOfMessage = new ArrayList<String>();
        listOfMessage.add("my test message");
        this.mockMvc.perform(post("/message")
                        .header("authorization", "Bearer " + token)
                        .content("{ \"name\": \"testUser\", \"message\": \"history 2\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.data").value(listOfMessage));

    }

    @Test
    void messageControllerReadHistoryWrongLimit() throws Exception{
        this.mockMvc.perform(post("/message")
                        .header("authorization", "Bearer " + token)
                        .content("{ \"name\": \"testUser\", \"message\": \"history asd\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.data").value(nullList));

    }
}
