package com.safecornerscoffee.borders.service;

import org.springframework.stereotype.Component;

@Component
public class StubPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String password) {
        return password;
    }

    @Override
    public boolean match(String password, String hashedPassword) {
        return password.equals(hashedPassword);
    }
}
