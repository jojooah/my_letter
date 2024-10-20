package com.jojo.my_letter.controller.page;

import com.jojo.my_letter.controller.service.LoginService;
import com.jojo.my_letter.model.entity.WeekDay;
import com.jojo.my_letter.service.CategoryService;
import com.jojo.my_letter.service.NewsLetterService;
import com.jojo.my_letter.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;


@Controller
@RequiredArgsConstructor
public class ViewController {

    private final NewsLetterService newsLetterService;
    private final CategoryService categoryService;
    private final LoginService loginService;
    private final ReplyService replyService;

    @GetMapping("/index")
    public String index(Model model) {
     //   List<NewsLetter> list = newsLetterService.selectNewsLetterListByWeekDay(WeekDay.Mon);
      //  model.addAttribute("newsLetterList", newsLetterService.selectNewsLetterListByWeekDay(WeekDay.Mon));
        return "common/index";
    }

    @GetMapping("/newsletter/list")
    public String newsletterItem(Model model, @RequestParam(required = false) Integer seq) {
        model.addAttribute("newsletterList", newsLetterService.selectNewsLetterList(seq));
        model.addAttribute("newsletterHeader", newsLetterService.selectNewsLetterHeader(seq));
        return "common/newsletter.list";
    }

    @GetMapping("/newsletter/item/{seq}")
    public String newsletterItem1(Model model, @PathVariable("seq") int seq) {
        model.addAttribute("loginUser", loginService.getMember());
        model.addAttribute("newsletter", newsLetterService.selectNewsLetter(seq));
        model.addAttribute("replyList", replyService.selectReplyListByNewsLetterSeq(seq));
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
    public String mypage(Model model) {
        model.addAttribute("member", loginService.getMember());
        return "user/myPage";
    }

    @GetMapping("/scrap")
    public String scrap() {
        return "scrap";
    }

    @GetMapping("/write")
    public String write(Model model, @RequestParam(required = false) Integer headerSeq, @RequestParam(required = false) Integer seq) {
        model.addAttribute("newsletterHeader", newsLetterService.selectNewsLetterHeader(headerSeq));
        if(seq != null){
            model.addAttribute("newsletter", newsLetterService.selectNewsLetter(seq));
        }
        return "author/write";
    }

    @GetMapping("/author/newsletter/header/list")
    public String newsLetterList(Model model) {
        model.addAttribute("weekDays", Arrays.asList(WeekDay.values()));
        model.addAttribute("newsletterHeaderList", newsLetterService.selectNewsLetterHeaderList());
        model.addAttribute("categoryList", categoryService.selectCategory());
        return "author/newsletter.header.list";
    }

}
