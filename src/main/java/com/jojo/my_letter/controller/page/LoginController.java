package com.jojo.my_letter.controller.page;

import com.jojo.my_letter.controller.service.impl.LoginService;
import com.jojo.my_letter.model.entity.Member;
import com.jojo.my_letter.model.result.RestErrorException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.jojo.my_letter.utils.Utils.toJson;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final AuthenticationManager authenticationManager; // AuthenticationManager 주입

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
     * @param member 회원가입 정보
     * @param redirectAttributes 리다이렉트 시킬 때, 데이터를 넘겨주는 객체
     * @return
     */
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute Member member, BindingResult bindingResult,RedirectAttributes redirectAttributes,HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "/join";
        }
        try {
            String unEncodedPwd = member.getPassword(); // 나중에 인코딩되어서 비교
            final Member joined = loginService.join(member);

            // 자동 로그인 처리
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(joined.getId(), unEncodedPwd);
            Authentication authentication = authenticationManager.authenticate(authToken);
            session.setAttribute("FROM_JOIN_PAGE", true);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("회원가입 성공 : {}, redirect:/", toJson(joined));
            return "redirect:/";
        }
        catch (RestErrorException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getRestError().getMessage());
            redirectAttributes.addFlashAttribute("member", member);
            log.error("회원가입 실패1 : {}, member: {}, redirect:/join", e.getRestError().getMessage(), toJson(member));
            return "redirect:/join";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("member", member);
            log.error("회원가입 실패2 : {}, member: {}, redirect:/join", e.getMessage(), toJson(member));
            return "redirect:/join";
        }
    }

}