package com.jojo.my_letter.controller.page;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginPage {

    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String error) {

        if(error != null && error.equalsIgnoreCase("invalidUser")) {
            model.addAttribute("errorMessage", "누구시죠???");
        }

        return "login";
    }

}