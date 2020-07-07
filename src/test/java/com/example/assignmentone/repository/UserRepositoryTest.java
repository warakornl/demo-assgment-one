package com.example.assignmentone.repository;

import com.example.assignmentone.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.StringJoiner;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@Slf4j
public class UserRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        StringJoiner sql = new StringJoiner(" ");
        sql.add("INSERT INTO \"user\"(")
                .add("id, email, password, name, phone, last_login, created_datetime, updated_datetime)")
                .add("VALUES (1, 'test01@gmail.com', 'pwTest01', 'NameTest01', '0811111111', now(), now(), now());");
        jdbcTemplate.execute(sql.toString());
    }

    private User mockUser() {
        return (new User())
                .setEmail("email-test@gmail.com")
                .setPassword("pwTest")
                .setName("NameTest")
                .setPhone("0811111111");
    }


    @Test
    public void createUserSuccess() {
        User mockUser = this.mockUser();
        Object userId = userRepository.createUser(mockUser);
        assertNotNull(userId);
    }

    @Test
    public void createUserFail() {
        Object userId = userRepository.createUser(new User().setPassword("12345"));
        assertNull(userId);
    }

    @Test
    public void getAllUserSuccess() {
        List<User> user = userRepository.getAllUser();
        assertEquals(true, user.size()>0);
    }

    @Test
    public void getAllUserFail() {
        List<User> user = userRepository.getAllUser();
        assertEquals(false, user.size()<=0);
    }

    @Test
    public void updateUserSuccess() {
        Object user = userRepository.updateUser(this.mockUser().setId("1"));
        assertNotNull(user);
    }

    @Test
    public void updateUserFail() {
        Object user = userRepository.updateUser(this.mockUser().setId("testUpdateFail"));
        assertNull(user);
    }

    @Test
    public void updatePatchUserSuccess() {
        Object user = userRepository.updatePatchUser(
                new User().setId("1").setPassword("pwTestPatch01"));
        assertNotNull(user);
    }

    @Test
    public void updatePatchUserFail() {
        Object user = userRepository.updatePatchUser(
                new User().setId("100").setPassword("pwTestPatch01"));
        assertNull(user);
    }

    @Test
    public void deleteUserSuccess() {
        Object user = userRepository.deleteUserById("1");
        assertNotNull(user);
    }

    @Test
    public void deleteUserFail() {
        Object user = userRepository.deleteUserById("100");
        assertNull(user);
    }

}
