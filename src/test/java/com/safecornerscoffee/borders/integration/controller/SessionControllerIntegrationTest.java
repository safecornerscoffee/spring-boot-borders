package com.safecornerscoffee.borders.integration.controller;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SessionControllerIntegrationTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
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
        memberService.join(member);

        MockHttpServletRequestBuilder signInRequest = signInRequest();
        HttpSession httpSession = mockMvc.perform(signInRequest)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(request().sessionAttribute("member", notNullValue()))
                .andReturn()
                .getRequest()
                .getSession();

        Member sessionUser = (Member) httpSession.getAttribute("member");

        assertThat(sessionUser.getEmail()).isEqualTo(member.getEmail());
        assertThat(sessionUser.getName()).isEqualTo(member.getName());

    }

    @Test
    public void signUpForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("signUpForm"))
                .andExpect(view().name("signup/signup"));
    }

    @Test
    public void signUp() throws Exception {
        MockHttpServletRequestBuilder signUpRequest = signUpRequest();
        mockMvc.perform(signUpRequest)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(request().sessionAttribute("member", notNullValue()));

        Member member = memberService.findOneByEmail("mocha@safecorners.io");

        assertThat(member.getEmail()).isEqualTo("mocha@safecorners.io");
        assertThat(member.getName()).isEqualTo("mocha");
        assertThat(member.getAddress()).isEqualTo(new Address("city", "street", "zipcode"));

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