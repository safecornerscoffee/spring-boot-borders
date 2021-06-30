package com.safecornerscoffee.borders.domain;


import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    String name;
    Address address;

    @Before
    public void beforeEach() {
        name = "mocha";
        address = Address.builder()
                .city("city")
                .street("street")
                .zipcode("224-235")
                .build();
    }

    @Test
    public void MemberNameShouldNotBeNull() {
        assertThatThrownBy(() -> {
            final Member member = Member.builder()
                    .name(null)
                    .address(address)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void MemberNameShouldNotBeEmpty() {
        assertThatThrownBy(() -> {
            final Member member = Member.builder()
                    .name("")
                    .address(address)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void MemberAddressShouldNotBeNull() {
        assertThatThrownBy(() -> {
            final Member member = Member.builder()
                    .name(name)
                    .address(null)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void MemberBuilder() {

        final Member member = Member.builder()
                .name(name)
                .address(address)
                .build();

        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getAddress()).isEqualTo(address);
    }
}