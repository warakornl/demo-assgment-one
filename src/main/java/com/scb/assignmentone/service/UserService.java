package com.scb.assignmentone.service;

import com.scb.assignmentone.exception.BusinessException;
import com.scb.assignmentone.exception.RecordNotFoundException;
import com.scb.assignmentone.model.User;
import com.scb.assignmentone.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) throws BusinessException {
        String userId = userRepository.createUser(user);
        if (null == userId) {
            throw new BusinessException(500, "can't create user");
        }
        User profile = userRepository.findUserById(userId);
        if (null == profile) {
            throw new RecordNotFoundException(404, "not found user");
        }
        return profile;
    }

    public User findUserById(String userId) throws BusinessException {
        User profile = userRepository.findUserById(userId);
        if (null == profile) {
            throw new BusinessException(404, "Not found User");
        }
        return profile;
    }

    public List<User> getAllUser() throws BusinessException {
        return userRepository.getAllUser();
    }

    public void updateUser(String userId, User user) {
        if(null == findUserById(userId)){
            throw new BusinessException(404, "Not found User");
        }else{
            if (null == userRepository.updateUser(user)) {
                throw new BusinessException(500, "Update User fail");
            }
        }
    }

    public void updatePatchUser(String userId, User user) {
        userRepository.updatePatchUser(user);
    }

    public Object deleteUserById(String userId) {
        if (null == userRepository.deleteUserById(userId)) {
            throw new BusinessException(500, "Delete User  fail");
        }else{
            return "Delete User success.";
        }
    }



}
