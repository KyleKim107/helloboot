package com.kylesboot.helloboot;


import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;

public class HellobootApplication {

    public static void main(String[] args) {
        System.out.println("Hello Containerless StandAlone Server");

        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory(); // 톰캣 서브릿 웹서버를 만들어준다.
        // 만약 다른 서브렛 컨테이너 를 사용하고 싶다면 JettyServletWebServerFactory()
        WebServer webServer = serverFactory.getWebServer();
        webServer.start();
    }

}
