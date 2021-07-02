package com.safecornerscoffee.borders.controller;

import com.safecornerscoffee.borders.data.SignInForm;
import com.safecornerscoffee.borders.data.SignUpForm;
import com.safecornerscoffee.borders.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SessionController {
    private final MemberService memberService;

    @GetMapping("/signin")
    public String signInForm(Model model) {
        model.addAttribute("signInForm", new SignInForm());
        return "signin/signin";
    }

    @PostMapping("/signin")
    public String signIn() {
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signup/signup";
    }

    @PostMapping("/signup")
    public String signUp() {
        return "redirect:/";
    }
}
