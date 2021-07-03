package com.safecornerscoffee.borders.service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class StubPasswordEncoderTest {

    PasswordEncoder passwordEncoder = new StubPasswordEncoder();

    @Test
    public void mismatch() {
        Boolean result = passwordEncoder.match("apple", "cherry");

        assertThat(result).isFalse();
    }

    @Test
    public void match() {
        Boolean result = passwordEncoder.match("test", "test");

        assertThat(result).isTrue();
    }
}