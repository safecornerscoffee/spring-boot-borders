package com.safecornerscoffee.borders.service;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest {

    @InjectMocks
    SessionService sessionService;

    @Mock
    MemberService memberService;
    @Spy
    PasswordEncoder passwordEncoder = new StubPasswordEncoder();

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

        //when
        Member signedMember = sessionService.signIn(member.getEmail(), member.getPassword());

        //then
        verify(memberService).findOneByEmail(anyString());
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
        verify(memberService).join(any(Member.class));
        verify(memberService).findOne(any(Long.class));

    }
}