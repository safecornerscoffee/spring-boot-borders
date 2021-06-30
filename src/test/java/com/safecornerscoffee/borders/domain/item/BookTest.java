package com.safecornerscoffee.borders.domain.item;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BookTest {

    @Test
    public void StockQuantityShouldBeGreaterThanOrEqualToZero() {
        assertThatThrownBy(() -> {
            final Book book = Book.builder()
                    .stockQuantity(-1)
                    .name("mocha recipe")
                    .price(8500)
                    .author("mocha")
                    .isbn("978-9482444292")
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void BookBuilder() {
        final Book book = Book.builder()
                .name("mocha recipe")
                .price(8500)
                .stockQuantity(10)
                .author("mocha")
                .isbn("978-9482444292")
                .build();

        assertThat(book.getName()).isEqualTo("mocha recipe");
        assertThat(book.getPrice()).isEqualTo(8500);
        assertThat(book.getStockQuantity()).isEqualTo(10);
        assertThat(book.getAuthor()).isEqualTo("mocha");
        assertThat(book.getIsbn()).isEqualTo("978-9482444292");
    }

}