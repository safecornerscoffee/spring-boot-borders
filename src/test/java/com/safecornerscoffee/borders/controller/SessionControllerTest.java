package com.safecornerscoffee.borders.controller;

import com.safecornerscoffee.borders.domain.Member;
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

import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SessionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void signInForm() throws Exception {
        mockMvc.perform(get("/signin"))
                .andDo(print())
                .andExpect(model().attributeExists("signInForm"))
                .andExpect(view().name("signin/signin"));
    }

    @Test
    public void signIn() throws Exception {
        MockHttpServletRequestBuilder signInRequest = signInRequest();
        HttpSession httpSession = mockMvc.perform(signInRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/"))
                .andExpect(request().sessionAttribute("member", ))
    }

    @Test
    public void signUpForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andDo(print())
                .andExpect(model().attributeExists("signUpForm"))
                .andExpect(model().attributeExists("signup/signup"));
    }

    @Test
    public void signUp() {
        MockHttpServletRequestBuilder signUpRequest = signUpRequest();

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
}