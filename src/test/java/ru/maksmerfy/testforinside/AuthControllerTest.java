package ru.maksmerfy.testforinside;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImV4cCI6MTYzNjU3ODAwMH0.b6TFQ4WnnWMwF7yOc2gLG4Y0SwEQlF61OM5Ll1oHIpSAzdFcnnstxgyT7Grlegcpw5ArBFjgXT5etbXKGGo4Ew";

    @Test
    void authController() throws Exception{
        this.mockMvc.perform(post("/login"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    void authControllerWrongLogin() throws Exception{
        this.mockMvc.perform(post("/login")
                        .content("{ \"name\": \"testUser\", \"password\": \"\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(""));
    }

    @Test
    void authControllerValidLogin() throws Exception{
        this.mockMvc.perform(post("/login")
                        .content("{ \"name\": \"testUser\", \"password\": \"12345\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

}
