package com.scb.assignmentone.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class User {
    private String id;
    @NotNull(message = "The email field is required")
    private String email;

    @NotNull(message = "The password filed is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotNull(message = "The phone filed is required")
    private String phone;
    @NotNull(message = "The name filed is required")
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String lastLogin;
    private String createdDatetime;
    private String updatedDatetime;
}