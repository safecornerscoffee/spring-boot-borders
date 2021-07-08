package com.safecornerscoffee.borders.helper;

import com.safecornerscoffee.borders.security.password.BcryptPasswordEncoder;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

public class BcryptPasswordEncoderTest {

    PasswordEncoder passwordEncoder = new BcryptPasswordEncoder();

    @Test
    public void compareWithInvalidPassword() {
        //given
        String password = "mocha";
        String hashedPassword = passwordEncoder.encode(password);
        String invalidPassword = "cappuccino";

        //when
        boolean isMatch = passwordEncoder.matches(invalidPassword, hashedPassword);

        assertThat(isMatch).isFalse();
    }

    @Test
    public void compareHashAndPassword() {
        //given
        String password = "mocha";
        String hashedPassword = passwordEncoder.encode(password);

        //when
        boolean isMatch = passwordEncoder.matches(password, hashedPassword);

        assertThat(isMatch).isTrue();
    }
}