package com.scb.assignmentone.repository.mapper;

import org.springframework.jdbc.core.RowMapper;

import com.scb.assignmentone.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPhone(resultSet.getString("phone"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setLastLogin(resultSet.getString("last_login"));
        user.setCreatedDatetime(resultSet.getString("created_datetime"));
        user.setUpdatedDatetime(resultSet.getString("updated_datetime"));
        return user;
    }
}
