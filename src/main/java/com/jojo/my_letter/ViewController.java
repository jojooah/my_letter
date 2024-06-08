package com.jojo.my_letter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Member;

@Controller
public class ViewController {

    @GetMapping("/index")
    public String healthCheck1(Model model) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getDetails();
        member.getName();
        model.addAttribute("isLogin", true);
        model.addAttribute("member", member);
        return "index";
    }

    @GetMapping("/newsletter/item")
    public String newsletterItem() {
       return "shop";
    }

    @GetMapping("/newsletter/item/1")
    public String newsletterItem1() {

        return "shop-detail";
    }

    @GetMapping("/weeks/monday")
    public String monday() {

        return "weeks";
    }

    @GetMapping("/checkout")
    public String checkout() {

        return "chackout";
    }

    @GetMapping("/mypage")
    public String mypage() {

        return "myPage";
    }

    @GetMapping("/scrap")
    public String scrap() {

        return "scrap";
    }
}
