package com.jojo.my_letter.controller.page;

import com.jojo.my_letter.service.NewsLetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class ViewController {

    private final NewsLetterService newsLetterService;

    @GetMapping("/index")
    public String index() {
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

    @GetMapping("/write")
    public String write() {
        return "author/write";
    }

    @GetMapping("/author/newsletter/list")
    public String newsLetterList(Model model){
        model.addAttribute("newsletterHeaderList", newsLetterService.selectNewsLetterList());
        return "author/newsletter.list";
    }


}
