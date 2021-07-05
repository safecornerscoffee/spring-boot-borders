package com.safecornerscoffee.borders.controller;

import com.safecornerscoffee.borders.service.ItemService;
import com.safecornerscoffee.borders.service.MemberService;
import com.safecornerscoffee.borders.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    @InjectMocks
    OrderController orderController;
    @Mock
    OrderService orderService;
    @Mock
    MemberService memberService;
    @Mock
    ItemService ItemService;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

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
        Long memberId = 1L;
        Long itemId = 1L;
        int count = 2;
        MockHttpServletRequestBuilder request = post("/orders/new").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("memberId", memberId.toString())
                .param("itemId", itemId.toString())
                .param("count", Integer.toString(count));

        mockMvc.perform(request).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders"));

        then(orderService).should().order(memberId, itemId, count);
    }

    @Test
    public void cancelOrder() throws Exception {
        Long orderId = 2L;

        mockMvc.perform(post("/orders/" + orderId.toString() + "/cancel"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders"));

        then(orderService).should(atLeastOnce()).cancelOrder(orderId);
    }
}