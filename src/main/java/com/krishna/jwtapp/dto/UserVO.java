package com.krishna.jwtapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserVO {
    private String name;
    @JsonIgnore
    private String password;
    private String email;
    private Long age;
}
