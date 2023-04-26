package com.yellow.api;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class PasswordTest {

    public static void main(String[] args) {
        final String encode = new BCryptPasswordEncoder().encode("test$123");
        System.out.println(encode);
    }
}
