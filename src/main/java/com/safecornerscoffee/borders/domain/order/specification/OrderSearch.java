package com.safecornerscoffee.borders.domain.order.specification;

import com.safecornerscoffee.borders.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
