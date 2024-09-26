package com.jojo.my_letter.controller.page;

import com.jojo.my_letter.model.entity.NewsLetter;
import com.jojo.my_letter.model.entity.NewsLetterHeader;
import com.jojo.my_letter.service.CategoryService;
import com.jojo.my_letter.service.NewsLetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ViewController {

    private final NewsLetterService newsLetterService;
    private final CategoryService categoryService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/newsletter/list")
    public String newsletterItem(Model model, @RequestParam(required = false) Integer seq) {
        model.addAttribute("newsletterList", newsLetterService.selectNewsLetterList(seq));
        model.addAttribute("newsletterHeader", newsLetterService.selectNewsLetterHeader(seq));
        return "common/newsletter.list";
    }

    @GetMapping("/newsletter/item/{seq}")
    public String newsletterItem1(Model model, @PathVariable("seq") int seq) {
        model.addAttribute("newsletter", newsLetterService.selectNewsLetter(seq));
        return "common/newsletter";
    }

    @GetMapping("/newsletter/item/content/{seq}")
    public String newsletterContent(Model model, @PathVariable("seq") int seq) {
        model.addAttribute("newsletter", newsLetterService.selectNewsLetter(seq));
        return "common/newsletter.content";
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
    public String write(Model model, @RequestParam(required = false) Integer seq) {
        model.addAttribute("newsletterHeader", newsLetterService.selectNewsLetterHeader(seq));
        return "author/write";
    }

    @GetMapping("/author/newsletter/header/list")
    public String newsLetterList(Model model) {
        model.addAttribute("newsletterHeaderList", newsLetterService.selectNewsLetterHeaderList());
        model.addAttribute("categoryList", categoryService.selectCategory());
        return "author/newsletter.header.list";
    }

}
