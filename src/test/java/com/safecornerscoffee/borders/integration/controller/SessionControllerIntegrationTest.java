package com.safecornerscoffee.borders.integration.controller;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SessionControllerIntegrationTest {

    private final static Logger logger = LoggerFactory.getLogger(SessionControllerIntegrationTest.class);

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

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
        em.flush();

        MockHttpServletRequestBuilder signInRequest = signInRequest();
        mockMvc.perform(signInRequest)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
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
                .andExpect(redirectedUrl("/signin"));

        Member member = memberService.findOneByEmail("mocha@safecornerscofee.com");

        assertThat(member.getEmail()).isEqualTo("mocha@safecornerscofee.com");
        assertThat(member.getName()).isEqualTo("mocha");
        assertThat(member.getAddress()).isEqualTo(new Address("city", "street", "zipcode"));

    }

    private MockHttpServletRequestBuilder signUpRequest() {
        return post("/signup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf())
                .param("email", "mocha@safecornerscofee.com")
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
                .param("email", "mocha@safecornerscoffee.com")
                .param("password", "mocha");
    }

    private Member createMember() {
        return Member.builder()
                .email("mocha@safecornerscoffee.com")
                .password("mocha")
                .name("mocha")
                .address(new Address("city", "street", "zipcode"))
                .build();
    }
}