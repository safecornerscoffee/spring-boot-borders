package com.safecornerscoffee.borders.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;

    @Builder
    public Member(String username) {
        this.username = username;
    }
}
