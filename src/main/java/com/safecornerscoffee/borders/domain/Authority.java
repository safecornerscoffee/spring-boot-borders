package com.safecornerscoffee.borders.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
//@Table(name = "authorities", indexes = @Index(name = "ix_auth_username", columnList = "member_id, authority", unique = true))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Authority {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Member member;

    private String name;

    public Authority(Member member, String name) {
        this.member = member;
        this.name = name;
    }
}
