package com.jojo.my_letter.controller.page;

import com.jojo.my_letter.controller.service.impl.LoginService;
import com.jojo.my_letter.model.entity.Member;
import com.jojo.my_letter.model.result.RestErrorException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.jojo.my_letter.utils.Utils.toJson;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginPage {

    private final LoginService loginService;

    /**
     * 로그인 페이지
     * @param model 로그인 정보
     * @param error 에러 메시지
     * @param session 에러 메시지
     * @return
     */
    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String error, HttpSession session) {
        if (error != null) {
            String errorMessage = (String) session.getAttribute("errorMessage");
            model.addAttribute("errorMessage", errorMessage);
        }
        return "login";
    }

    /**
     * 회원가입 페이지
     * @param model 회원가입 정보
     * @param error 에러 메시지
     * @return
     */
    @GetMapping("/join")
    public String showJoinForm(Model model, @RequestParam(required = false) String error) {
        if (!model.containsAttribute("member")) {
            model.addAttribute("member", new Member());
        }
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        return "join";
    }

    /**
     * 회원가입
     * todo 1. 자동로그인 안시켜주는 정책. (선택)
     * todo 2. 자동로그인 시켜준다면, 로그인을 시켜주고 메인페이지로 리다이렉트 시켜주는 정책. (선택했으면 좋겠습니다.)
     * @param member 회원가입 정보
     * @param redirectAttributes 리다이렉트 시킬 때, 데이터를 넘겨주는 객체
     * @return
     */
    @PostMapping("/join")
    public String join(Member member, RedirectAttributes redirectAttributes) {
        try {
            final Member joined = loginService.join(member);
            log.info("회원가입 성공 : {}, redirect:/login", toJson(joined));
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("member", member);
            log.error("회원가입 실패 : {}, member: {}, redirect:/join", e.getMessage(), toJson(member));
            return "redirect:/join";
        }
    }
}