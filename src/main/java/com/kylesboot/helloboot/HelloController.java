package com.kylesboot.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequestMapping("/hello")
public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    //  대안 1  이 애노테이션만(@GetMapping("/hello")) 있으면 플젝이 커지면 나중에 디스패쳐가 못찾는다.그래서 RequestMapping를 추가해준다. ex> myapp/hello
    // 대안 2 아니면 Get으로 나온건 이 메소드를 실행 시킨다
    @GetMapping
    @ResponseBody // 리턴 타입을 단순 스트링이 아닌 뷰 타입이 되게 해준 %% RestController를 사용하면 이 애노테이션이 불필요.
    public String hello(String name){
        return helloService.sayHello( Objects.requireNonNull(name) );
    }
}
