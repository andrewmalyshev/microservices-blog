package com.gl.idp.users.service;

import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Service
public class EncryptionService {
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't',
            'K', 'e', 'y' };
    public String encrypt(String subject) throws Exception {
        subject = subject.trim();
        String encryptedString = "";
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = cipher.doFinal(subject.getBytes());
        encryptedString = new BASE64Encoder().encode(encVal);
//        System.out.println(encryptedString);
        return encryptedString;
    }
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        return key;
    }

}
