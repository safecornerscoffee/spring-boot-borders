package com.safecornerscoffee.borders.service;

public interface PasswordEncoder {
    String encode(String password);

    boolean match(String password, String hashedPassword);
}
