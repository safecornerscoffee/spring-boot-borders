package com.safecornerscoffee.borders.service;

import com.safecornerscoffee.borders.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SessionService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public Member signIn(String email, String password) {
        Member member = memberService.findOneByEmail(email);

        matches(password, member.getPassword());

        return member;
    }

    private void matches(String password, String hashedPassword) {

        if (!passwordEncoder.matches(password, hashedPassword)) {
            // todo convert to NotFoundMember or InvalidEmailOrPassword Exception
            throw new IllegalStateException("invalid email or password.");
        }
    }

    @Transactional
    public Member signUp(Member member) {
        Long memberId = memberService.join(member);
        return memberService.findOne(memberId);
    }
}
