package com.krishna.jwtapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.krishna.jwtapp.dto.UserVO;
import com.krishna.jwtapp.service.exception.BussinessException;
import com.krishna.jwtapp.utils.JWTUtil;

@Service
public class UserService {
    public String doUserLogin(UserVO userVO) throws BussinessException{
        String token = null;
        if("krishna".equals(userVO.getName()) || "qwerty".equals(userVO.getPassword())){
            token = JWTUtil.createToken();
        } else{
            throw new BussinessException("Invalid user or password!");
        }
        return token;
    }

    public List<UserVO> doGetUsers(){
        List<UserVO> list = new ArrayList<>();
        for(int i=0;i < 10; i++){
            UserVO userVO = new UserVO();
            userVO.setName("user"+i);
            userVO.setEmail("email"+i+".gmail.com");
            userVO.setAge(20l+i);
            list.add(userVO);
        }
        return list;
    }
}
