package com.jojo.my_letter.controller.page;

import com.jojo.my_letter.controller.service.LoginService;
import com.jojo.my_letter.model.entity.Category;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ViewController {

    private final NewsLetterService newsLetterService;
    private final CategoryService categoryService;
    private final LoginService loginService;
    private final ReplyService replyService;

    @GetMapping({"/index","/"})
    public String index(Model model) {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        WeekDay weekDay = WeekDay.fromCode(today.toString().substring(0,3));
        model.addAttribute("newsLetterHeaderList", newsLetterService.selectNewsLetterHeaderListByWeekDay(weekDay));
        model.addAttribute("weekDays", WeekDay.values());
        model.addAttribute("categoryList", categoryService.selectCategory());
        model.addAttribute("today",weekDay);
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

    @GetMapping("/weeks/{weekDay}")
    public String newsLetterHeaderByWeek(Model model,@PathVariable("weekDay") String weekDay) {
        model.addAttribute("newsLetterHeaderList", newsLetterService.selectNewsLetterHeaderListByWeekDay(WeekDay.fromCode(weekDay)));
        model.addAttribute("weekDays", WeekDay.values());
        model.addAttribute("today", WeekDay.fromCode(weekDay));
        model.addAttribute("categoryList", categoryService.selectCategory());
        return "common/weeks";
    }

    @GetMapping("/category/{cat1Code}")
    public String newsLetterHeaderByCategory(Model model, @PathVariable("cat1Code") String cat1Code
                                                        , @RequestParam(defaultValue = "0") int page
                                                        , @RequestParam(defaultValue = "9") int size) {
        List<Category> categories = categoryService.selectCategory();
        int totalItems = newsLetterService.countNewsLetterByCategory(cat1Code);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        model.addAttribute("newsLetterHeaderList", newsLetterService.selectNewsLetterHeaderListByCategory(cat1Code,page,size));
        model.addAttribute("categoryList", categoryService.selectCategory());
        model.addAttribute("category",categories.stream().filter(x->x.getCatCode().equals(cat1Code)).findAny().get());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        return "common/category";
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
