package com.krishna.jwtapp.utils;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class RSAUtil {
    public static KeyPair getGenerateRSAKeyPair(){
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            keyPair = keyGen.genKeyPair();
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return keyPair;
    }

    public static RSAPublicKey getRSAPublickey(){
        RSAPublicKey publicKey = null;
        try{
        File publicKeyFile = new File("keyfiles/public.key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        publicKey = (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch(IOException e){
            e.printStackTrace();
        } catch(InvalidKeySpecException | NoSuchAlgorithmException e){
            
        }
        return publicKey;
    }

    public static RSAPrivateKey getRSAPrivatekey(){
        RSAPrivateKey privateKey = null;
        try{
        File privateKeyFile = new File("keyfiles/private.key");
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
        privateKey = (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch(IOException e){
            e.printStackTrace();
        } catch(InvalidKeySpecException | NoSuchAlgorithmException e){
            
        }
        return privateKey;
    }
}
