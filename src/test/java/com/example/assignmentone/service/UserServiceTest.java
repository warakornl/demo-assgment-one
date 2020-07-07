package com.example.assignmentone.service;

import com.example.assignmentone.exception.BusinessException;
import com.example.assignmentone.exception.RecordNotFoundException;
import com.example.assignmentone.model.User;
import com.example.assignmentone.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User mockUser() {
        return (new User())
                .setId(UUID.randomUUID().toString())
                .setEmail("email-test@gmail.com")
                .setPassword("pwTest")
                .setName("NameTest")
                .setPhone("0811111111");
    }

    @Test
    public void createUserSuccess() {
        User mockUser = this.mockUser();
        doReturn(mockUser.getId())
                .when(userRepository)
                .createUser(any());
        doReturn(mockUser)
                .when(userRepository)
                .findUserById(anyString());
        Object user = userService.createUser(mockUser);
        assertEquals(user, mockUser);
    }

    @Test
    public void createUserFailCanNotCreateUser() {
        User mockUser = this.mockUser();
        doThrow(new BusinessException(500, "can't create user"))
                .when(userRepository)
                .createUser(any());
        try {
            Object user = userService.createUser(mockUser);
        } catch (BusinessException ex) {
            assertEquals("500", ex.getCode());
            assertEquals("can't create user", ex.getMessage());
        }
    }

    @Test
    public void createUserFailNotFoundUser() {
        User mockUser = this.mockUser();
        doReturn(mockUser.getId())
                .when(userRepository)
                .createUser(any());
        doThrow(new RecordNotFoundException(404, "not found user"))
                .when(userRepository)
                .findUserById(anyString());
        try {
            Object user = userService.createUser(mockUser);
        } catch (RecordNotFoundException ex) {
            assertEquals("404", ex.getCode());
            assertEquals("not found user", ex.getMessage());
        }
    }


}
