package com.safecornerscoffee.borders.integration.controller;

import com.safecornerscoffee.borders.data.SignUpForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerIntegrationTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void createMemberForm() throws Exception {
        mockMvc.perform(get("/members/new"))
                .andDo(print())
                .andExpect(model().attributeExists("signUpForm"))
                .andExpect(view().name("members/create-member"));
    }

    @Test
    public void create() throws Exception {
        MockHttpServletRequestBuilder signUpRequest = signUpRequest();

        mockMvc.perform(signUpRequest)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/members"));

    }

    @Test
    public void memberList() throws Exception {
        mockMvc.perform(get("/members"))
                .andDo(print())
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
}