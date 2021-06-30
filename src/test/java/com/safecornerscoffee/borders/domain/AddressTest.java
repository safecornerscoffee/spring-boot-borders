package com.safecornerscoffee.borders.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class AddressTest {

    @Test
    public void CityShouldNotBeEmpty() {
        assertThatThrownBy(() -> {
            final Address address = Address.builder()
                    .city("")
                    .street("street")
                    .zipcode("zipcode")
                    .build();

        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void StreetShouldNotBeEmpty() {
        assertThatThrownBy(() -> {
            final Address address = Address.builder()
                    .city("city")
                    .street("")
                    .zipcode("zipcode")
                    .build();

        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ZipcodeShouldNotBeEmpty() {
        assertThatThrownBy(() -> {
            final Address address = Address.builder()
                    .city("city")
                    .street("street")
                    .zipcode("")
                    .build();

        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void AddressBuilder() {
        final Address address = Address.builder()
                .city("city")
                .street("street")
                .zipcode("zipcode")
                .build();

        assertThat(address.getCity()).isEqualTo("city");
        assertThat(address.getStreet()).isEqualTo("street");
        assertThat(address.getZipcode()).isEqualTo("zipcode");
    }
}