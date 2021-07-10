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

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .andExpect(redirectedUrl("/signin"));

        then(sessionService)
                .should()
                .signUp(any(Member.class));
    }

    private MockHttpServletRequestBuilder signUpRequest() {
        return post("/signup").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf())
                .param("email", "mocha@safecorners.io")
                .param("password", "mocha")
                .param("name", "mocha")
                .param("city", "city")
                .param("street", "street")
                .param("zipcode", "zipcode");
    }

    private MockHttpServletRequestBuilder signInRequest() {
        return post("/signin")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf())
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