package com.krishna.jwtapp.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.krishna.jwtapp.utils.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class UserInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        boolean isReqValid = true;
        String token =request.getHeader("authorize");
        // Configure unsecured URLs
        List<String> unsecuredURLs = List.of("login");
        Optional<String> unsecuredURL = unsecuredURLs.stream().filter( url -> request.getRequestURL().toString().contains(url)).findFirst();

        if((Objects.nonNull(token) && token.trim() != "") && !unsecuredURL.isPresent()) {
            DecodedJWT decodedJWT = JWTUtil.verifyToken(token);
            if(Objects.isNull(decodedJWT)){
                isReqValid = false;
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                try{
                response.getWriter().write("Invalid User");
                response.getWriter().flush();
                } catch(IOException e){
                    log.error(e.getMessage(),e);
                }
            }
        }
        return isReqValid;
    }
}
