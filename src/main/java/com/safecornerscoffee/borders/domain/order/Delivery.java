package com.safecornerscoffee.borders.domain.order;


import com.safecornerscoffee.borders.domain.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "deliveries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Delivery(Address address, DeliveryStatus status) {
        this.address = address;
        this.status = status;
    }
}