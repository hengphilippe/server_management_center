package com.dev.smc.servers.credentials;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CredentialUtil {
    private static final String ALGORITHM = "AES";
    @Value("${gdce.aes.key}")
    private static String keyGen;

    static byte[] decodedKey = Base64.getDecoder().decode(keyGen);
    static SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);

    // Encrypt a string
    public static String encrypt(String code)
    {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            System.out.println("Chiper : " +  cipher);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(code.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Code Encryption Failed.....");
            throw new RuntimeException(e);
        }
    }

    // Decrypt a string
    public static String decrypt(String encryptedCode) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedCode));
            return new String(decryptedData);
        } catch (Exception e) {
            System.out.println("Code Decryption Failed");
            throw new RuntimeException(e);
        }
    }

}
