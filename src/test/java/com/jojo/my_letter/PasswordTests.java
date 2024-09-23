package com.jojo.my_letter;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTests {

    @Test
    public void test() throws Exception {

        System.out.println(new BCryptPasswordEncoder().encode("12345"));
        System.out.println(new BCryptPasswordEncoder().encode("12345"));
        System.out.println(new BCryptPasswordEncoder().encode("12345"));
        System.out.println(new BCryptPasswordEncoder().encode("12345"));
        System.out.println(new BCryptPasswordEncoder().encode("12345"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches("12345", "$2a$10$0n8xwRZHyOqn8Nea4veIgOJ.GRQneKRDekN.XXoJQR90qFWWnsq3i")) {
            System.out.println("matched");
        }
    }
}