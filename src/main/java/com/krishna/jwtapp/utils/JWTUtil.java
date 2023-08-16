package com.krishna.jwtapp.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
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
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JWTUtil {
    static final KeyPair kepair = RSAUtil.getGenerateRSAKeyPair();
    static final RSAPublicKey rsaPublicKey = (RSAPublicKey) kepair.getPublic();
    static final RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) kepair.getPrivate();

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
            log.error(e.getMessage(),e);
        }
    }

    public static String createToken(){
        String token=null;
        try {
            log.info("Create token");
            RSAPrivateKey privateKey = RSAUtil.getPrivateKey();
            Algorithm algorithm = Algorithm.RSA256(null, privateKey);
            token = JWT.create()
            .withIssuer("krishna")
            // .withIssuedAt(new Date()).withExpiresAt(new Date(System.currentTimeMillis() + 5000L))
            .sign(algorithm);
        } catch (JWTCreationException e){
            log.error(e.getMessage(),e);
        }
        return token;
    }

    public static DecodedJWT verifyToken(final String token){
        DecodedJWT decodedJWT = null;
        try{
            log.info("Verify token");
            RSAPublicKey publicKey = RSAUtil.getPublicKey();
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
            .withIssuer("krishna")
            .build();
            decodedJWT = jwtVerifier.verify(token);
        } catch(JWTVerificationException e){
            log.error(e.getMessage(),e);
        }
        return decodedJWT;
    }
}
