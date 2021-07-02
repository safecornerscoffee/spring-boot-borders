package com.safecornerscoffee.borders.data;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class SignUpForm {

    @NotEmpty(message = "이메일 주소를 입력해주세요.")
    private String email;
    private String password;
    private String city;
    private String street;
    private String zipcode;
}
