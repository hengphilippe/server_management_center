package com.dev.smc.servers.credentials;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class CredentialUtil {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private SecretKey key;

    @Value("${gdce.aes.key}")
    private String keyGen;

    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(keyGen);
        this.key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    private IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public String encrypt(String code) {
        try {
            IvParameterSpec iv = generateIv();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encryptedData = cipher.doFinal(code.getBytes("UTF-8"));
            byte[] encryptedDataWithIv = new byte[iv.getIV().length + encryptedData.length];
            System.arraycopy(iv.getIV(), 0, encryptedDataWithIv, 0, iv.getIV().length);
            System.arraycopy(encryptedData, 0, encryptedDataWithIv, iv.getIV().length, encryptedData.length);
            return Base64.getEncoder().encodeToString(encryptedDataWithIv);
        } catch (Exception e) {
            throw new RuntimeException("Code Encryption Failed", e);
        }
    }

    public String decrypt(String encryptedCode) {
        try {
            byte[] encryptedDataWithIv = Base64.getDecoder().decode(encryptedCode);

            byte[] iv = new byte[16];
            System.arraycopy(encryptedDataWithIv, 0, iv, 0, iv.length);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            byte[] encryptedData = new byte[encryptedDataWithIv.length - iv.length];
            
            System.arraycopy(encryptedDataWithIv, iv.length, encryptedData, 0, encryptedData.length);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] decryptedData = cipher.doFinal(encryptedData);
            return new String(decryptedData, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Code Decryption Failed", e);
        }
    }
}
