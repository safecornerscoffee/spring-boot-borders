package com.safecornerscoffee.borders.service;

import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.exception.DuplicateMemberException;
import com.safecornerscoffee.borders.helper.PasswordEncoder;
import com.safecornerscoffee.borders.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    public final MemberRepository memberRepository;
    public final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        member.setPassword(passwordEncoder.generateFromPassword(member.getPassword()));
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if (!findMembers.isEmpty()) {
            throw new DuplicateMemberException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public Member findOneByEmail(String email) {
        return memberRepository.findOneByEmail(email);
    }
}
