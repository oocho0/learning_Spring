package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //localhost:8080으로 들어오면 home이 자동으로 호출되어 localhose:8080/home으로 이동하게 ㄴ
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
