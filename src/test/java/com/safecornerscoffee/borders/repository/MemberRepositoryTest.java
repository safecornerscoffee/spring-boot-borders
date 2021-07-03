package com.safecornerscoffee.borders.repository;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
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
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void createMember() {
        //given
        Address address = Address.builder().city("city").street("street").zipcode("zipcode").build();
        Member member = Member.builder().email("mocha@safecorners.io").password("mocha").name("mocha").address(address).build();

        //when
        memberRepository.save(member);

        //then
        Member findMember = memberRepository.findOne(member.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember).isEqualTo(member);
        assertThat(findMember).isSameAs(member);
    }

    @Test
    public void findOneByEmail() {
        //given
        Address address = Address.builder().city("city").street("street").zipcode("zipcode").build();
        Member member = Member.builder().email("mocha@safecorners.io").password("mocha").name("mocha").address(address).build();

        //when
        memberRepository.save(member);

        //then
        Member findMember = memberRepository.findOneByEmail(member.getEmail());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(findMember).isEqualTo(member);
        assertThat(findMember).isSameAs(member);
    }
}