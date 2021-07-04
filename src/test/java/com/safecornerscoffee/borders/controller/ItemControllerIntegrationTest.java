package com.safecornerscoffee.borders.controller;

import com.safecornerscoffee.borders.data.BookForm;
import com.safecornerscoffee.borders.domain.item.Book;
import com.safecornerscoffee.borders.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ItemControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void itemList() throws Exception {
        mockMvc.perform(get("/items"))
                .andDo(print())
                .andExpect(view().name("items/items"));
    }

    @Test
    public void createForm() throws Exception {
        mockMvc.perform(get("/items/new"))
                .andDo(print())
                .andExpect(model().attributeExists("form"))
                .andExpect(view().name("items/create-item"));
    }

    @Test
    public void createItem() throws Exception {

        BookForm dto = BookForm.builder()
                .name("domain guide")
                .price(1500)
                .stockQuantity(120)
                .author("mocha")
                .isbn("1234")
                .build();

        MockHttpServletRequestBuilder request = post("/items/new").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", dto.getName())
                .param("price", Integer.toString(dto.getPrice()))
                .param("stockQuantity", Integer.toString(dto.getStockQuantity()))
                .param("author", dto.getAuthor())
                .param("isbn", dto.getIsbn());

        mockMvc.perform(request)
                .andDo(print());

    }
}