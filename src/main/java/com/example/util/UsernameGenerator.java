package com.example.util;

import java.util.Random;

public class UsernameGenerator {
    public static String generateFromEmail(String email) {
        int atIndex = email.indexOf('@');
        String username = email.substring(0, atIndex);
        Random random = new Random();
        int randomNumber = random.nextInt(900) + 100;
        username += randomNumber;
        return username;
    }
}
