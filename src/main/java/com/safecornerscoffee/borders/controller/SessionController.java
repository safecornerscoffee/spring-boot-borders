package com.safecornerscoffee.borders.controller;

import com.safecornerscoffee.borders.data.SignInForm;
import com.safecornerscoffee.borders.data.SignUpForm;
import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.service.MemberService;
import com.safecornerscoffee.borders.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/signin")
    public String signInForm(Model model) {
        model.addAttribute("signInForm", new SignInForm());
        return "signin/signin";
    }

//    @PostMapping("/signin")
//    public String signIn(SignInForm dto, HttpSession httpSession) {
//
//        Member member = sessionService.signIn(dto.getEmail(), dto.getPassword());
//
//        httpSession.setAttribute("member", member);
//
//        return "redirect:/";
//    }

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signup/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid SignUpForm dto, HttpSession httpSession, BindingResult result) {

        if (result.hasErrors()) {
            return "signup/signup";
        }

        Member member = Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .address(new Address(dto.getCity(), dto.getStreet(), dto.getZipcode()))
                .build();

        Member signedMember = sessionService.signUp(member);

        httpSession.setAttribute("member", signedMember);

        return "redirect:/";
    }

//    @PostMapping("/logout")
//    public String logout(HttpSession httpSession) {
//        httpSession.invalidate();
//        return "redirect:/";
//    }

//    @GetMapping("/login")
//    public String loginForm() {
//        return "signin/signin";
//    }

}
