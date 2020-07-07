package com.scb.assignmentone.controller;

import com.scb.assignmentone.config.CommonConstants;
import com.scb.assignmentone.exception.BusinessException;
import com.scb.assignmentone.model.User;
import com.scb.assignmentone.model.response.ResponseModel;
import com.scb.assignmentone.service.UserService;
import com.scb.assignmentone.service.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Validate validateService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(
            @Valid @NonNull @RequestBody User user)
            throws NoSuchAlgorithmException {
        User profile = userService.createUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseModel(profile));
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<?> findUserById(
            @PathVariable("userId") String userId
    ) {
        User profile = userService.findUserById(userId);
        ResponseModel<User> data = new ResponseModel<>(profile);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(data);
    }

    @GetMapping
    public ResponseEntity<?> getAllUser() {
        List<User> profiles = userService.getAllUser();
        ResponseModel<List<User>> data = new ResponseModel<>(profiles);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(data);
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<?> updateUserById(
            @PathVariable("userId") String userId,
            @NonNull @RequestBody User userToUpdate) {
            String msgValidate = validateService.validateUpdateUser(userToUpdate);
            if(!StringUtils.isEmpty(msgValidate)){
                return ResponseEntity
                        .status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(new ResponseModel(CommonConstants.STATUS_CODE_UNPROCESSABLE_ENTITY, msgValidate));
            }else{
                userToUpdate.setId(userId);
                userService.updateUser(userId, userToUpdate);
                User user = userService.findUserById(userId);
                ResponseModel<User> data = new ResponseModel<>(user);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(data);
            }
    }

    @PatchMapping(path = "{userId}")
    public ResponseEntity<?> updatePatchUserById(
            @PathVariable("userId") String userId,
            @NonNull @RequestBody User userToUpdate) {
            User profile = userService.findUserById(userId);
            if (null == profile) {
                throw new BusinessException(404, "User Not found");
            }else{
                userToUpdate.setId(userId);
                userService.updatePatchUser(userId,userToUpdate);
                User user = userService.findUserById(userId);
                ResponseModel<User> data = new ResponseModel<>(user);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(data);
            }
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable("userId") String userId) {
            User profile = userService.findUserById(userId);
            if (null == profile) {
                throw new BusinessException(404, "User Not found");
            }else{
                Object result = userService.deleteUserById(userId);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(result);
            }
    }
}
