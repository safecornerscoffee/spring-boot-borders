package com.safecornerscoffee.borders.security.password;

public class NoOpPasswordEncoder implements PasswordEncoder {

    @Override
    public String generateFromPassword(String password) {
        return password;
    }

    @Override
    public boolean compareHashAndPassword(String password, String hashedPassword) {
        return password.equals(hashedPassword);
    }

}
