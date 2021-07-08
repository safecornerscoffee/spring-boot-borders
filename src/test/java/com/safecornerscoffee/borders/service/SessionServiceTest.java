package com.safecornerscoffee.borders.service;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.security.password.PasswordEncoder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest {

    @InjectMocks
    SessionService sessionService;

    @Mock
    MemberService memberService;
    @Mock
    PasswordEncoder passwordEncoder;

    Address address;
    Member member;

    @Before
    public void setup() {
        address = Address.builder().city("city").street("street").zipcode("zipcode").build();
        member = Member.builder().email("mocha@safecorners.io").password("mocha").name("mocha").address(address).build();
    }

    @Test
    public void signIn() {
        //given
        given(memberService.findOneByEmail(member.getEmail())).willReturn(member);
        given(passwordEncoder.compareHashAndPassword(anyString(), anyString())).willReturn(true);

        //when
        Member signedMember = sessionService.signIn(member.getEmail(), member.getPassword());

        //then
        then(memberService).should().findOneByEmail(anyString());
        then(passwordEncoder).should().compareHashAndPassword(anyString(), anyString());
        assertThat(signedMember).isNotNull();
        assertThat(signedMember.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    public void signUp() {
        //given
        given(memberService.join(member)).willReturn(1L);
        given(memberService.findOne(1L)).willReturn(member);

        //when
        Member signUpMember = sessionService.signUp(member);

        //then
        then(memberService).should().join(any(Member.class));
        then(memberService).should().findOne(any(Long.class));

    }
}