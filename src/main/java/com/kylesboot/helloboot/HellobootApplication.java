package com.kylesboot.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.http.HttpHeaders;
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
            servletContext.addServlet("Hello", new HttpServlet() { // 서브랫 추가해주기
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    String name = req.getParameter("name");


                    resp.setStatus( HttpStatus.OK.value() ); // set status
                    resp.setHeader( HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE); // set header
                    resp.getWriter().print("Hello " + name); // set Body
                }
            }).addMapping("/hello"); //요청이 들어오면 매핑을 해준다.
        });
        webServer.start();
    }

}
