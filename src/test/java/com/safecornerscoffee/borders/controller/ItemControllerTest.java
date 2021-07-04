package com.safecornerscoffee.borders.controller;

import com.safecornerscoffee.borders.data.UpdateItemForm;
import com.safecornerscoffee.borders.domain.item.Book;
import com.safecornerscoffee.borders.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {

    @InjectMocks
    ItemController itemController;

    @Mock
    ItemService itemService;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    public void createForm() throws Exception {

        mockMvc.perform(get("/items/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("form"))
                .andExpect(view().name("items/create-item"));
    }

    @Test
    public void create() throws Exception {
        //when
        ResultActions response = createItemRequest();

        //then
        then(itemService).should(atLeastOnce()).saveItem(any(Book.class));
        response.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items"));
    }

    @Test
    public void updateItemForm() throws Exception {
        //given
        Long itemId = 1L;
        Book book = createBook();
        given(itemService.findOne(itemId)).willReturn(book);

        //when
        ResultActions resultActions = mockMvc.perform(get("/items/" + itemId + "/edit"))
                .andDo(print());

        //then
        then(itemService).should().findOne(itemId);
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("form"))
                .andExpect(view().name("items/edit-item"));

    }

    @Test
    public void update() throws Exception {
        //given
        Long itemId = 1L;

        //when
        ResultActions response = updateItemRequest(itemId);

        //then
        response.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items"));

        then(itemService).should().updateItem(anyLong(), any(UpdateItemForm.class));
    }

    @Test
    public void items() throws Exception {
        given(itemService.findItems()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/items"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("items"))
                .andExpect(view().name("items/items"));
    }

    private Book createBook() {
        return Book.builder()
                .name("mocha recipe")
                .author("mocha")
                .isbn("998-764821")
                .price(12500)
                .stockQuantity(10)
                .build();
    }

    private ResultActions createItemRequest() throws Exception {
        return mockMvc.perform(post("/items/new").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "mocha recipe")
                .param("author", "mocha")
                .param("isbn", "998-764821")
                .param("price", "12500")
                .param("stockQuantity", "10"))
                .andDo(print());
    }

    private ResultActions updateItemRequest(Long itemId) throws Exception {
        return mockMvc.perform(post("/items/" + itemId + "/edit").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("name", "mocha recipe")
                .param("author", "mocha")
                .param("isbn", "998-764821")
                .param("price", "8500")
                .param("stockQuantity", "10"))
                .andDo(print());
    }

}