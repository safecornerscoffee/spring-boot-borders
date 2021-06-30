package com.safecornerscoffee.borders.domain.item;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Book extends Item {

    private String author;
    private String isbn;


    @Builder
    public Book(String name, int price, int stockQuantity, String author, String isbn) {
        Assert.hasText(name, "name must not be empty");
        Assert.isTrue(price > 0, "price must be greater than 0");
        Assert.isTrue(stockQuantity >= 0, "stockQuantity must be greater than or equal to 0");
        Assert.hasText(author, "author must not be empty");
        Assert.hasText(isbn, "isbn must not be empty");

        this.setName(name);
        this.setPrice(price);
        this.setStockQuantity(stockQuantity);
        this.setAuthor(author);
        this.setIsbn(isbn);
    }
}
