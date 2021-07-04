package com.safecornerscoffee.borders.helper;

public interface PasswordEncoder {

    String generateFromPassword(String password);

    boolean compareHashAndPassword(String password, String hashedPassword);
}
