package com.krishna.jwtapp.config;

import java.io.IOException;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.krishna.jwtapp.utils.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        boolean isReqValid = true;
        String token =request.getHeader("authorize");
        if((Objects.nonNull(token) && token.trim() != "") && !request.getRequestURL().toString().contains("login")) {
            DecodedJWT decodedJWT = JWTUtil.verifyToken(token);
            if(Objects.isNull(decodedJWT)){
                isReqValid = false;
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                try{
                response.getWriter().write("Invalid User");
                response.getWriter().flush();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return isReqValid;
    }
}
