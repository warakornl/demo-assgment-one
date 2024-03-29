package com.example.assignmentone.controller;

import com.example.assignmentone.BaseTest;
import com.example.assignmentone.model.User;
import com.example.assignmentone.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests extends BaseTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private String baseUrl = "/api/v1/user";

    @Test
    public void postUserCreateSuccess() throws Exception {
        Object userRequest = this.getResourceRequest("user/CreateSuccess");
        doReturn(new User())
                .when(userService)
                .createUser(any());
        this.tesCurlPostController(baseUrl.concat("/create"), userRequest, "200");
    }

    @Test
    public void postUserCreateValidateFail() throws Exception {
        Object userRequest = this.getResourceRequest("user/CreateValidateFail");
        this.testBadRequest(baseUrl.concat("/create"), userRequest);
    }
}
