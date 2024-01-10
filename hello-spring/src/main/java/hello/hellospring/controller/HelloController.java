package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    // 웹 어플리케이션에서 /hello 라고 들어오면 이 메소드 호출
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");  // data: hello!!
        return "hello";  // hello.html
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody  // http body에 데이터를 직접 넣어준다는 뜻!
    public String helloString(@RequestParam("name") String name) {
        // name이 spring이면 "hello spring"
        return "hello " + name;  // 문자가 요청한 클라이언트에 그대로 내려감. 템플릿엔진과의 차이: view 이런게 없음.
    }
    
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {  // Hello 라는 객체
        Hello hello = new Hello();
        hello.setName(name);
        return hello;  // 객체를 return
    }

    // static 클래스로 만들면, 클래스(HelloController) 안에서 클래스(Hello) 사용 가능
    static class Hello {
        private String name;
        
        // 프로퍼티 접근 방식
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
