package com.jojo.my_letter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/index")
    public String healthCheck1() {
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
