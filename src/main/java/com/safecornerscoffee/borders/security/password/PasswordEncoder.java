package com.safecornerscoffee.borders.security.password;

public interface PasswordEncoder {

    String generateFromPassword(String password);

    boolean compareHashAndPassword(String password, String hashedPassword);
}
