package com.safecornerscoffee.borders.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateItemForm {
    private String name;
    private int price;
    private int stockQuantity;

    @Builder
    public UpdateItemForm(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
