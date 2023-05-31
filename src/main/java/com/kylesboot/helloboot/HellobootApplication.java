package com.kylesboot.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

public class HellobootApplication {

    public static void main(String[] args) {
        System.out.println("Hello Containerless StandAlone Server");
        // Spring Container를 생성해 보자. GenericApplicationContext가 결정적으로 사용된다.
        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext(){
            // 서브릿 컨테이너가 만들어 지는 과정을 스프링 컨테이너가 초기화 되는 과정 중에 진행해 보려고 한다....
            // 기존 코드도 잘 작동 하지만 스프링이 이런 방식을 채택해서 한번 따라해 보려고 한다.
            @Override
            protected void onRefresh() {
                super.onRefresh();

                TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory(); // Embeded 톰캣 서브릿 컨테이너를 호출한다.
                // 만약 다른 서브렛 컨테이너 를 사용하고 싶다면 JettyServletWebServerFactory()
                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet", // Dispatcher Servlet은 프론트 컨트롤러 역할이다
                            new DispatcherServlet(this) // applicationContext받아서 dispatcherServlet은 빈을 다 뒤져서 요청을 처리할 수 있게 맵핑해준다.
                    ).addMapping("/*"); //프론트 컨트롤러는 모든 요청을 받아 드린다
                });
                webServer.start();

            }
        }; // 스프링 컨테이너 생성 후 빈 등록
        applicationContext.registerBean(HelloController.class);
        applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.refresh(); // 구성정보를 다시 초기화 해준다.

    }

}
