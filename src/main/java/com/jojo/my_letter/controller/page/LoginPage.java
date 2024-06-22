package com.jojo.my_letter.controller.page;

import com.jojo.my_letter.controller.service.impl.LoginService;
import com.jojo.my_letter.model.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginPage {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        return "login";
    }


    @GetMapping("/join")
    public String join(Model model,@RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        return "join";
    }

    @PostMapping("/join")
    public String join(Member member) {
        try {
            if(loginService.join(member)){
               return "redirect:/login";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
           return "redirect:/join?error="+e.getMessage();
        }
        return "redirect:/join?error=에러발생. 관리자에게 문의하세요";
    }
}