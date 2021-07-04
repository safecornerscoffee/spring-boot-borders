package com.safecornerscoffee.borders.service;

import com.safecornerscoffee.borders.helper.PasswordEncoder;
import com.safecornerscoffee.borders.helper.StubPasswordEncoder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class StubPasswordEncoderTest {

    PasswordEncoder passwordEncoder = new StubPasswordEncoder();

    @Test
    public void mismatch() {
        Boolean result = passwordEncoder.compareHashAndPassword("apple", "cherry");

        assertThat(result).isFalse();
    }

    @Test
    public void match() {
        Boolean result = passwordEncoder.compareHashAndPassword("test", "test");

        assertThat(result).isTrue();
    }
}