package com.safecornerscoffee.borders.domain.item;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CategoryTest {

    @Test
    public void CategoryNameMustNotBeEmpty() {
        assertThatThrownBy(() -> {
            final Category category = new Category("");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void CreateCategory() {
        final Category category = new Category("novel");

        assertThat(category.getName()).isEqualTo("novel");
    }
}