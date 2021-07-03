package com.safecornerscoffee.borders.controller;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.service.SessionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.http.MediaType;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionControllerTest {

    @InjectMocks
    SessionController sessionController;

    @Mock
    SessionService sessionService;

    MockMvc mockMvc;
    MockHttpSession mockHttpSession;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sessionController).build();
        mockHttpSession = new MockHttpSession();
    }

    @Test
    public void signInForm() throws Exception {
        mockMvc.perform(get("/signin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("signInForm"))
                .andExpect(view().name("signin/signin"));
    }

    @Test
    public void signIn() throws Exception {
        //given
        Member member = createMember();
        given(sessionService.signIn(anyString(), anyString())).willReturn(member);

        MockHttpServletRequestBuilder signInRequest = signInRequest();
        mockMvc.perform(signInRequest)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(request().sessionAttribute("member", notNullValue()));

        then(sessionService)
                .should()
                .signIn(anyString(), anyString());
    }

    @Test
    public void signUpForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andDo(print())
                .andExpect(model().attributeExists("signUpForm"))
                .andExpect(view().name("signup/signup"));
    }

    @Test
    public void signUp() throws Exception {
        //given
        Member member = createMember();
        given(sessionService.signUp(any(Member.class))).willReturn(member);

        MockHttpServletRequestBuilder signUpRequest = signUpRequest();
        mockMvc.perform(signUpRequest)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(request().sessionAttribute("member", notNullValue()));

        then(sessionService)
                .should()
                .signUp(any(Member.class));
    }

    @Test
    public void logout() throws Exception {
        Member member = createMember();
        mockMvc.perform(post("/logout").sessionAttr("member", member))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(request().sessionAttributeDoesNotExist("member"));
    }

    private MockHttpServletRequestBuilder signUpRequest() {
        return post("/signup").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "mocha@safecorners.io")
                .param("password", "mocha")
                .param("name", "mocha")
                .param("city", "city")
                .param("street", "street")
                .param("zipcode", "zipcode");
    }

    private MockHttpServletRequestBuilder signInRequest() {
        return post("/signin").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "mocha@safecorners.io")
                .param("password", "mocha");
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