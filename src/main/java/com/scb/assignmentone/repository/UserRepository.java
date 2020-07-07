package com.scb.assignmentone.repository;

import com.scb.assignmentone.model.User;
import com.scb.assignmentone.repository.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.query.QueryCreationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.*;

@Slf4j
@Repository
public class UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public String createUser(User user) {
        StringJoiner sql = new StringJoiner(" ");
        sql.add("INSERT INTO \"user\"(")
                .add("id, email, password, name, phone, last_login, created_datetime, updated_datetime)")
                .add("VALUES (:id, :email, :password, :name, :phone, :lastLogin , now(), now());");
        Date date = Calendar.getInstance().getTime();

        HashMap<String, Object> params = new HashMap<>();
        String userId = UUID.randomUUID().toString();
        params.put("id", userId);
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("name", user.getName());
        params.put("phone", user.getPhone());
        params.put("lastLogin", StringUtils.hasText(user.getLastLogin()) == false ? date :user.getLastLogin());
        try {
            int res = namedParameterJdbcTemplate.update(sql.toString(), params);
            return (res == 1) ? userId : null;
        } catch (EmptyResultDataAccessException exception) {
            log.info("Cannot create product", sql.toString() ,exception.getMessage());
            return null;
        } catch (DataAccessException exception) {
            if (exception.getCause() instanceof SQLException) {
                log.info("Cannot create product:SQLException", sql.toString(), exception.getMessage());
            } else{
                log.info("Cannot create product", sql.toString(), exception.getMessage());
            }
            return null;
        }
    }

    public User findUserById(String userId) {
        StringJoiner sql = new StringJoiner(" ");
        sql.add("SELECT * FROM \"user\" WHERE id = :userId");

        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql.toString(), params, new UserMapper());
        }
        catch (EmptyResultDataAccessException exception) {
            log.info("user not found", sql.toString());
            return null;
        }
    }

    public List<User> getAllUser() {
        StringJoiner sql = new StringJoiner(" ");
        sql.add("SELECT * FROM \"user\"");
        try {
            return namedParameterJdbcTemplate.query(sql.toString(), new UserMapper());
        } catch (EmptyResultDataAccessException exception) {
            log.info("can't get user", sql.toString());
            return null;
        }
    }

    public Object updateUser(User user) {
        StringJoiner sql = new StringJoiner(" ");
        sql.add("UPDATE \"user\" SET")
                .add("name = :name, email = :email, password = :password, phone = :phone")
                .add("WHERE id = :id");
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("name", user.getName());
        params.put("phone", user.getPhone());
        try {
            return namedParameterJdbcTemplate.update(sql.toString(), params) == 1 ? 1 : null;
        } catch (EmptyResultDataAccessException ex) {
            log.info("can't update user", sql.toString());
            return null;
        }
    }

    public Object updatePatchUser(User user) {
        StringJoiner sql = new StringJoiner(",");
        String sqlUpdate = "UPDATE \"user\" SET ";
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        if(StringUtils.hasText(user.getEmail())){
            params.put("email", user.getEmail());
            sql.add("email = :email");
        }
        if(StringUtils.hasText(user.getPassword())){
            params.put("password", user.getPassword());
            sql.add("password = :password");
        }
        if(StringUtils.hasText(user.getName())){
            params.put("name", user.getName());
            sql.add("name = :name");
        }
        if(StringUtils.hasText(user.getPhone())){
            params.put("phone", user.getPhone());
            sql.add("phone = :phone");
        }
        if(sql.length()>0){
            try {
                sql.add("updated_datetime = now()");
                String sqlStr = sqlUpdate + sql.toString() + " WHERE id = :id";
                int result = namedParameterJdbcTemplate.update(sqlStr, params);
                return  result == 1 ? 1 : null;
            } catch (EmptyResultDataAccessException ex) {
                log.info("can't update user", sql.toString());
                return null;
            }
        }
        return null;
    }

    public Object deleteUserById(String userId) {
        String sql = "DELETE FROM \"user\" WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", userId);
        try {
            int result = namedParameterJdbcTemplate.update(sql, params);
            return  result == 1 ? 1 : null;
        } catch (QueryCreationException ex) {
            log.error("query error", ex.getMessage());
            return null;
        }
    }
}
