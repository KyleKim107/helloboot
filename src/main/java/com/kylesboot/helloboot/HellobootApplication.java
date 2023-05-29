package com.kylesboot.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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

        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory(); // Embeded 톰캣 서브릿 웹서버를 호출한.
        // 만약 다른 서브렛 컨테이너 를 사용하고 싶다면 JettyServletWebServerFactory()
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            HelloController helloController = new HelloController();

            servletContext.addServlet("frontcontroller", new HttpServlet() { // 서브랫 추가해주기
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    // 인증, 보안, 다국어, 공통기능

                    // hello와 GET요청을 서브랫과 매핑
                    if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) { // hello page와 Get요청일때만

                        String name = req.getParameter("name"); // 매핑
                        String printValue = helloController.hello(name);// 비즈니스 로직 바인딩

                        resp.setStatus(HttpStatus.OK.value()); // set status
                        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE); // set header
                        resp.getWriter().print(printValue); // set Body
                    } else if(req.getRequestURI().equals("/user")){ // user page
                        //
                    }else{
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }
                }
            }).addMapping("/*"); //프론트 컨트롤러는 모든 요청을 받아 드린다
        });
        webServer.start();
    }

}
