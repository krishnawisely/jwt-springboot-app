package com.krishna.jwtapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krishna.jwtapp.dto.UserVO;
import com.krishna.jwtapp.service.UserService;
import com.krishna.jwtapp.service.exception.BussinessException;

@RequestMapping("users")
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping(value = "/login")
    public ResponseEntity<String> userLogin(@RequestBody UserVO userVO) throws BussinessException{
        String token = userService.doUserLogin(userVO);
        return ResponseEntity.ok(token);
    }

    @GetMapping
    public ResponseEntity<List<UserVO>> getUsers(){
        return ResponseEntity.ok(userService.doGetUsers());
    }
}
