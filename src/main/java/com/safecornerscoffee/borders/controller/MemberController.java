package com.safecornerscoffee.borders.controller;

import com.safecornerscoffee.borders.data.EditMemberForm;
import com.safecornerscoffee.borders.data.SignUpForm;
import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "members/create-member";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid SignUpForm dto, BindingResult result) {

        if (result.hasErrors()) {
            return "members/create-member";
        }

        // todo MemberAssembler
        Address address = new Address(dto.getCity(), dto.getStreet(), dto.getZipcode());
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .address(address)
                .build();

        memberService.join(member);

        return "redirect:/members";
    }

    @GetMapping("/members/{memberId}/edit")
    public String editForm(@PathVariable Long memberId, Model model) {
        Member member = memberService.findOne(memberId);
        EditMemberForm editMemberForm = EditMemberForm.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .city(member.getAddress().getCity())
                .street(member.getAddress().getStreet())
                .zipcode(member.getAddress().getZipcode())
                .build();

        model.addAttribute("editMemberForm", editMemberForm);

        return "members/edit-member";
    }

    @PostMapping("/members/{memberId}/edit")
    public String edit(@PathVariable Long memberId, @Valid EditMemberForm dto, BindingResult result) {

        if (result.hasErrors()) {
            return "members/edit-member";
        }

        Address address = new Address(dto.getCity(), dto.getStreet(), dto.getZipcode());
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .address(address)
                .build();

        memberService.update(member);

        return "redirect:/members";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/members";
    }
}
