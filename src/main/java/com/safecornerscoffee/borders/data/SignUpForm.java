package com.safecornerscoffee.borders.data;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class SignUpForm {

    @NotEmpty(message = "이메일 주소를 입력해주세요.")
    private String email;
    private String password;
    private String name;
    private String city;
    private String street;
    private String zipcode;


    public Member toMember() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .address(new Address(city, street, zipcode))
                .build();
    }
}
