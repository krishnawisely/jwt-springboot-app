package com.krishna.jwtapp.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;

@Component
public class JWTUtil {
    static final RSAPublicKey rsaPublicKey = (RSAPublicKey) RSAUtil.getGenerateRSAKeyPair().getPublic();
    static final RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) RSAUtil.getGenerateRSAKeyPair().getPrivate();

    @PostConstruct
    private static void prepareRSAPublicAndPrivateKey(){
        try {
            FileOutputStream publicFile = new FileOutputStream("keyfiles/public.key");
            publicFile.write(rsaPublicKey.getEncoded());
            FileOutputStream privateFile = new FileOutputStream("keyfiles/private.key");
            privateFile.write(rsaPrivateKey.getEncoded());
            publicFile.close();
            privateFile.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String createToken(){
        String token=null;
        try {
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getRSAPublickey(), RSAUtil.getRSAPrivatekey());
            token = JWT.create().withIssuer("krishna").sign(algorithm);
        } catch (JWTCreationException exception){
            exception.printStackTrace();
        }
        return token;
    }

    public static DecodedJWT verifyToken(final String token){
        DecodedJWT decodedJWT = null;
        try{
            System.out.println(rsaPrivateKey.getEncoded());
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getRSAPublickey(), RSAUtil.getRSAPrivatekey());
            JWTVerifier jwtVerifier = JWT.require(algorithm)
            .withIssuer("krishna").build();

            decodedJWT = jwtVerifier.verify(token);
        } catch(JWTVerificationException e){
            e.printStackTrace();
        }
        return decodedJWT;
    }
}
