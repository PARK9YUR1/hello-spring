package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    // 웹 어플리케이션에서 /hello 라고 들어오면 이 메소드 호출
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");  // data: hello!!
        return "hello";  // hello.html
    }
}
