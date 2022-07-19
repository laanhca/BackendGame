package com.av.backendgame.api.controller;

import com.av.backendgame.BackendGameApplication;
import com.av.backendgame.api.controller.service.loginscreen.*;
import com.av.backendgame.api.model.MailLoginForm;
import com.av.backendgame.api.model.UsernameLoginForm;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(BackendGameApplication.LOGIN)
@RestController
public class Controller_LoginScreen {
    private static final String REG_WITH_USERNAME = "reg_with_username";
    private static final String SEND_MAIL = "send_mail";
    private static final String LOGIN_WITH_USERNAME = "login_with_username";
    private static final String LOGIN_WITH_MAIL = "login_with_mail";

    @PostMapping(value = REG_WITH_USERNAME, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setRegWithUsername(@RequestBody UsernameLoginForm loginForm){
        return new Service_LoginScreen_RegWithUsername().getRespone(loginForm);
    }

    @PostMapping(value = LOGIN_WITH_USERNAME, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setLoginWithUsername(@RequestBody UsernameLoginForm loginForm){
        return new Service_LoginScreen_LoginWithUsername().getRespone(loginForm);
    }

    @PostMapping(value = SEND_MAIL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setSendMail(@RequestBody String mail){
        return new Service_LoginScreen_SendMail().getRespone(mail);
    }

    @PostMapping(value = LOGIN_WITH_MAIL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setLoginWithMail(@RequestBody MailLoginForm loginForm){
        return new Service_LoginScreen_LoginWithMail().getRespone(loginForm);
    }



}
