package com.safecornerscoffee.borders.controller;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class MemberControllerTest {

    @InjectMocks
    MemberController memberController;
    @Mock
    MemberService memberService;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    public void createForm() throws Exception {
        mockMvc.perform(get("/members/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("signUpForm"))
                .andExpect(view().name("members/create-member"));
    }

    @Test
    public void create() throws Exception {
        MockHttpServletRequestBuilder signUpRequest = signUpRequest();

        ResultActions resultActions = mockMvc.perform(signUpRequest);

        resultActions.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/members"));

    }

    @Test
    public void editForm() throws Exception {
        Long memberId = 1L;
        given(memberService.findOne(memberId)).willReturn(createMember());

        mockMvc.perform(get("/members/" + memberId + "/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("member"))
                .andExpect(view().name("members/edit-member"));

        then(memberService).should().findOne(anyLong());
    }

    @Test
    public void edit() throws Exception {

        MockHttpServletRequestBuilder editRequest = signUpRequest();

        mockMvc.perform(editRequest)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/members"));
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(get("/members"))
                .andDo((print()))
                .andExpect(model().attributeExists("members"))
                .andExpect(view().name("members/members"));
    }

    private MockHttpServletRequestBuilder signUpRequest() {
        return post("/members/new").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "mocha@safecorners.io")
                .param("password", "mocha")
                .param("name", "mocha")
                .param("city", "city")
                .param("street", "street")
                .param("zipcode", "zipcode");
    }

    private Member createMember() {
        return Member.builder()
                .email("mocha@safecorners.io")
                .password("mocha")
                .name("mocha")
                .address(new Address("city", "street", "zipcode"))
                .build();
    }
}