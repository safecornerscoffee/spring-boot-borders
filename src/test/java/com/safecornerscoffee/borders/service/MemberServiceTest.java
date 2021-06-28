package com.safecornerscoffee.borders.service;

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
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void joinTest() {
        Member member = Member.builder().name("mocha").build();

        Long savedId = memberService.join(member);

        Member savedMember = memberRepository.findOne(member.getId());
        assertThat(savedMember).isEqualTo(member);
    }

    @Test
    public void duplicateMemberExceptionTest() {
        Member member = Member.builder().name("mocha").build();
        Member otherMember = Member.builder().name("mocha").build();

        assertThatThrownBy(() -> {
            memberService.join(member);
            memberService.join(otherMember);
        }).isInstanceOf(DuplicateMemberException.class);
    }
}