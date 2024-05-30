package com.jojo.my_letter;

import com.jojo.my_letter.model.result.RestResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

//fixme
// API 는 @RestController 를 사용합니다.
// 화면 관련된 부분은 @Controller 를 사용하게 됩니다. 단 이경우에 API 를 활용하려면 @ResponseBody 를 사용해야 합니다.
// @RestController 와 @Controller 의 차이점을 알아야 합니다.

//todo
// 백엔드 코드를 더 올려주시면 살펴보겠습니다 :)
// 제 생각에는 우선 만약에 코드 내에서 예외가 발생하는 경우를 어떻게 처리할 수 있는지를 고민해보셔서 올려주시면 좋을 것 같습니다.
@Controller
public class ViewController {

    @GetMapping("/index")
    public String healthCheck1() {
        return "index";
    }

    @GetMapping("/newsletter/item")
    public String newsletterItem() {
        // fixme 하실 계획이셨는지는 모르겠으나 웬만하면 하나의 파일보다는 페이지 구성을 fragment 를 활용해서 역할별로 header, footer, body 등으로 나누어서 구성하는게 좋습니다.
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
