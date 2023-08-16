package com.krishna.jwtapp.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RSAUtil {
    public static KeyPair getGenerateRSAKeyPair(){
        KeyPair keyPair = null;
        try {
            log.info("Prepare keypair");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            keyPair = keyGen.genKeyPair();
        } catch(NoSuchAlgorithmException e){
            log.error(e.getMessage(),e);
        }
        return keyPair;
    }

    public static RSAPublicKey getPublicKey(){
        RSAPublicKey publicKey = null;
        try{
        File publicKeyFile = new File("keyfiles/public.key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        publicKey = (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch(IOException e){
            log.error(e.getMessage(),e);
        } catch(InvalidKeySpecException | NoSuchAlgorithmException e){
            log.error(e.getMessage(),e);
        }
        return publicKey;
    }

    public static RSAPrivateKey getPrivateKey(){
        RSAPrivateKey privateKey = null;
        try{
        File privateKeyFile = new File("keyfiles/private.key");
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
        privateKey = (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch(IOException e){
            log.error(e.getMessage(),e);
        } catch(InvalidKeySpecException | NoSuchAlgorithmException e){
            log.error(e.getMessage(),e);
        }
        return privateKey;
    }
}
