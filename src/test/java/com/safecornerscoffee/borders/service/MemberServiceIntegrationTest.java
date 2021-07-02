package com.safecornerscoffee.borders.service;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.exception.DuplicateMemberException;
import com.safecornerscoffee.borders.repository.MemberRepository;
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
public class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void joinTest() {
        //given
        Address address = Address.builder().city("city").street("street").zipcode("zipcode").build();
        Member member = Member.builder().email("mocha@safecorners.io").password("mocha").name("mocha").address(address).build();

        //when
        Long savedId = memberService.join(member);

        //then
        Member savedMember = memberRepository.findOne(member.getId());
        assertThat(savedMember).isEqualTo(member);
    }

    @Test
    public void duplicateMemberExceptionTest() {
        //given
        Address address = Address.builder().city("city").street("street").zipcode("zipcode").build();
        Member member = Member.builder().email("mocha@safecorners.io").password("mocha").name("mocha").address(address).build();
        Member anotherMember = Member.builder().email("mocha@safecorners.io").password("cappuccino").name("cappuccino").address(address).build();

        //when
        assertThatThrownBy(() -> {
            memberService.join(member);
            memberService.join(anotherMember);
        }).isInstanceOf(DuplicateMemberException.class);
    }
}