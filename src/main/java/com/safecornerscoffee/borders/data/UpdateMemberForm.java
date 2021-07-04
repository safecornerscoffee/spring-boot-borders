package com.safecornerscoffee.borders.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateMemberForm {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    @Builder
    public UpdateMemberForm(Long id, String email, String password, String name, String city, String street, String zipcode) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
