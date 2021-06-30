package com.safecornerscoffee.borders.domain;

import com.safecornerscoffee.borders.domain.order.Order;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String name, Address address) {
        Assert.hasText(name, "name must not be empty ");
        Assert.notNull(address, "address must not be null");

        this.name = name;
        this.address = address;
    }
}
