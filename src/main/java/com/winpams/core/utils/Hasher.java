package com.winpams.core.utils;

import com.winpams.core.Config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    public static String hashPassword(String password) {
        String salt = Config.get("SALT");

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            String saltedPassword = salt + password;

            md.update(saltedPassword.getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes)
                sb.append(String.format("%02x", b));


            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        String hashOfInput = hashPassword(password);

        return hashOfInput.equals(hashedPassword);
    }
}
