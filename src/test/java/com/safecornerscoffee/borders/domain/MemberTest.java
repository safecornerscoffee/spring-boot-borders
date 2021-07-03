package com.safecornerscoffee.borders.domain;


import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    String email;
    String password;
    String name;
    Address address;

    @Before
    public void beforeEach() {
        email = "mocha@safecorners.io";
        password = "mocha";
        name = "mocha";
        address = Address.builder()
                .city("city")
                .street("street")
                .zipcode("zipcode")
                .build();
    }

    @Test
    public void MemberEmailShouldBeNotEmpty() {
        assertThatThrownBy(() -> {
            final Member member = Member.builder()
                    .email(null)
                    .password(password)
                    .name(name)
                    .address(address)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> {
            final Member member = Member.builder()
                    .email("")
                    .password(password)
                    .name(name)
                    .address(address)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void MemberPasswordShouldBeNotEmpty() {
        assertThatThrownBy(() -> {
            final Member member = Member.builder()
                    .email(email)
                    .password(null)
                    .name(name)
                    .address(address)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> {
            final Member member = Member.builder()
                    .email(email)
                    .password("")
                    .name(name)
                    .address(address)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void MemberNameShouldNotBeEmpty() {
        assertThatThrownBy(() -> {
            final Member member = Member.builder()
                    .email(email)
                    .password(password)
                    .name("")
                    .address(null)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void MemberAddressShouldBeNotNull() {
        assertThatThrownBy(() -> {
            final Member member = Member.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .address(null)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void MemberBuilder() {

        final Member member = Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .address(address)
                .build();

        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getPassword()).isEqualTo(password);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getAddress()).isEqualTo(address);
    }
}