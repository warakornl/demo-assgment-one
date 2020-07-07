package com.scb.assignmentone.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.scb.assignmentone.model.User;

@Service
public class Validate {

    public String validateUpdateUser(User user) {
        String errMsg = "";
        if(StringUtils.isEmpty(user.getEmail())){
            errMsg = "Please insert email.";
        }else if(StringUtils.isEmpty(user.getPassword())){
            errMsg = "Please insert password.";
        }else if(StringUtils.isEmpty(user.getPhone())){
            errMsg = "Please insert phone.";
        }else if(StringUtils.isEmpty(user.getName())) {
            errMsg = "Please insert name.";
        }
        return errMsg;
    }
}
