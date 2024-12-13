package ru.kpfu.itis.util;

import jakarta.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
    private PasswordUtil(){}
    public static String encrypt(String password){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digets = md.digest();
            return DatatypeConverter.printHexBinary(digets);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}