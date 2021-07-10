package com.safecornerscoffee.borders.controller;

import com.safecornerscoffee.borders.data.SignInForm;
import com.safecornerscoffee.borders.data.SignUpForm;
import com.safecornerscoffee.borders.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signup/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid SignUpForm dto, BindingResult result) {

        if (result.hasErrors()) {
            return "signup/signup";
        }

        sessionService.signUp(dto.toMember());

        return "redirect:/signin";
    }

}
