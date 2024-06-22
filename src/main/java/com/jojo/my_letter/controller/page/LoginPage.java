package com.jojo.my_letter.controller.page;

import com.jojo.my_letter.controller.service.impl.LoginService;
import com.jojo.my_letter.model.entity.Member;
import com.jojo.my_letter.model.result.RestErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showJoinForm(Model model, @RequestParam(required = false) String error) {
        if (!model.containsAttribute("member")) {
            model.addAttribute("member", new Member());
        }
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        return "join"; // 뷰 이름
    }


    @PostMapping("/join")
    public String join(Member member, RedirectAttributes redirectAttributes) {
        try {
            if (loginService.join(member)) {
                return "redirect:/login";
            }
        } catch (RestErrorException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getRestError().getMessage());
            redirectAttributes.addFlashAttribute("member", member);
            return "redirect:/join";
        } catch (Exception e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("member", member);
            return "redirect:/join";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "에러 발생. 관리자에게 문의하세요.");
        redirectAttributes.addFlashAttribute("member", member);
        return "redirect:/join";
    }
}