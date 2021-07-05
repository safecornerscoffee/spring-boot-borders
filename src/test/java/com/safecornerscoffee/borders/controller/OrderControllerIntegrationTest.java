package com.safecornerscoffee.borders.controller;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.domain.item.Book;
import com.safecornerscoffee.borders.domain.item.Item;
import com.safecornerscoffee.borders.domain.order.Order;
import com.safecornerscoffee.borders.service.ItemService;
import com.safecornerscoffee.borders.service.MemberService;
import com.safecornerscoffee.borders.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    OrderService orderService;

    @Autowired
    MockMvc mockMvc;


    @Test
    public void createForm() throws Exception {

        mockMvc.perform(get("/orders/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("members"))
                .andExpect(model().attributeExists("items"))
                .andExpect(view().name("orders/create-order"));
    }

    @Test
    public void order() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("mocha recipe", 8500, 120);
        int count = 2;

        MockHttpServletRequestBuilder request = post("/orders/new").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("memberId", member.getId().toString())
                .param("itemId", item.getId().toString())
                .param("count", Integer.toString(count));

        //when
        ResultActions resultActions = mockMvc.perform(request).andDo(print());

        //then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders"));

    }

    @Test
    public void orderList() throws Exception {
        //given
        Member member = createMember();
        Item book = createBook("how-to-cancel-order", 120, 5);
        orderService.order(member.getId(), book.getId(), 1);
        orderService.order(member.getId(), book.getId(), 1);
        orderService.order(member.getId(), book.getId(), 1);

        //when
        ResultActions resultActions = mockMvc.perform(get("/orders")).andDo(print());

        //then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", hasSize(3)))
                .andExpect(view().name("orders/orders"))
                .andReturn();
    }

    @Test
    public void cancelOrder() throws Exception {
        Member member = createMember();
        Item book = createBook("how-to-cancel-order", 120, 5);
        Long orderId = orderService.order(member.getId(), book.getId(), 3);

        mockMvc.perform(post("/orders/" + orderId.toString() + "/cancel"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders"));

    }

    private Member createMember() {
        Member member = Member.builder()
                .email("mocha@safecorners.io")
                .password("mocha")
                .name("mocha")
                .address(new Address("city", "street", "zipcode"))
                .build();

        memberService.join(member);

        return member;
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Book book = Book.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .author("mocha")
                .isbn("9482")
                .build();
        itemService.saveItem(book);

        return book;
    }
}