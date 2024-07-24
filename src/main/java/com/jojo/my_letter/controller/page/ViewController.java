package com.jojo.my_letter.controller.page;

import com.jojo.my_letter.model.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;


@Controller
public class ViewController {

    @GetMapping("/index")
    public String healthCheck1(@RequestBody Model model) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication.getPrincipal())) return "index";
        Member member = (Member) authentication.getPrincipal();
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
