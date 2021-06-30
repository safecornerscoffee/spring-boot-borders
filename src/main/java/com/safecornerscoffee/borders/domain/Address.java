package com.safecornerscoffee.borders.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    @Builder
    public Address(String city, String street, String zipcode) {
        Assert.hasText(city, "city must not be empty");
        Assert.hasText(street, "street must not be empty");
        Assert.hasText(zipcode, "zipcode must not be empty");

        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}