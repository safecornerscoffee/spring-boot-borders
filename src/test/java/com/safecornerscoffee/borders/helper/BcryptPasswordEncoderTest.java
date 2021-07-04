package com.safecornerscoffee.borders.helper;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BcryptPasswordEncoderTest {

    PasswordEncoder passwordEncoder = new BcryptPasswordEncoder();

    @Test
    public void compareWithInvalidPassword() {
        //given
        String password = "mocha";
        String hashedPassword = passwordEncoder.generateFromPassword(password);
        String invalidPassword = "cappuccino";

        //when
        boolean isMatch = passwordEncoder.compareHashAndPassword(invalidPassword, hashedPassword);

        assertThat(isMatch).isFalse();
    }

    @Test
    public void compareHashAndPassword() {
        //given
        String password = "mocha";
        String hashedPassword = passwordEncoder.generateFromPassword(password);

        //when
        boolean isMatch = passwordEncoder.compareHashAndPassword(password, hashedPassword);

        assertThat(isMatch).isTrue();
    }
}