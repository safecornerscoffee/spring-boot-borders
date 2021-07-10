package com.safecornerscoffee.borders.domain;

import com.safecornerscoffee.borders.domain.order.Order;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Member implements Serializable, UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NaturalId
    private String email;

    @NotEmpty
    private String password;

    private Boolean enabled;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Authority> authorities = new ArrayList<>();

    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String email, String password, String name, Address address) {
        Assert.hasText(email, "email must be not empty ");
        Assert.hasText(password, "password must be not empty");
        Assert.hasText(name, "name must be not empty");
        Assert.notNull(address, "address must not be null");

        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.enabled = true;
    }

    public void addAuthority(Authority authority) {
        authorities.add(authority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
