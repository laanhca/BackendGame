package com.av.backendgame.api.controller.service.loginscreen;

import com.av.backendgame.api.model.MailLoginForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public class Service_LoginScreen_LoginWithMail {

    public ResponseEntity<?> getRespone(MailLoginForm loginForm){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
