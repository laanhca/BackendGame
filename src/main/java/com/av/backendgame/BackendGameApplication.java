package com.av.backendgame;

import com.av.backendgame.Security.RSASecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class BackendGameApplication {
    public static final String LOGIN = "/api/login_screen";

    public static void main(String[] args) {
        SpringApplication.run(BackendGameApplication.class, args);
    }

}
