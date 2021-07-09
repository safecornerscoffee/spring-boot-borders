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
    @JoinColumn(name = "member_id")
    private Member member;

    private String authority;

    public Authority(Member member, String authority) {
        this.member = member;
        this.authority = authority;
    }
}
