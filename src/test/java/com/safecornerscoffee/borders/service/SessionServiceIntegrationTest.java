package com.safecornerscoffee.borders.service;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SessionServiceIntegrationTest {

    @Autowired
    SessionService sessionService;
    @Autowired
    MemberService memberService;

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
        memberService.join(member);

        //when
        Member signedMember = sessionService.signIn(member.getEmail(), member.getPassword());

        //then
        assertThat(signedMember.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    public void signUp() {
        Member signedMember = sessionService.signUp(member);

        assertThat(signedMember).isNotNull();
        assertThat(signedMember.getId()).isNotNull();
        assertThat(signedMember).isEqualTo(member);
    }
}