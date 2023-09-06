package com.example.finalproject_phase2.util.hash_password;

import com.example.finalproject_phase2.custom_exception.CustomNoSuchAlgorithmException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptPassword {
    public  String hashPassword(String password) {
        try {
            if (password.length()==0)throw new CustomNoSuchAlgorithmException("password is incorrect");
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException | CustomNoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public  boolean checkPassword(String password, String storedHash){
        String enteredHash = hashPassword(password);
        return storedHash.equals(enteredHash);
    }
}
