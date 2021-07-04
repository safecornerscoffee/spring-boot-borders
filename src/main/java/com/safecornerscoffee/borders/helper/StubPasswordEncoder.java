package com.safecornerscoffee.borders.helper;

import org.springframework.stereotype.Component;

public class StubPasswordEncoder implements PasswordEncoder {

    @Override
    public String generateFromPassword(String password) {
        return password;
    }

    @Override
    public boolean compareHashAndPassword(String password, String hashedPassword) {
        return password.equals(hashedPassword);
    }

}
