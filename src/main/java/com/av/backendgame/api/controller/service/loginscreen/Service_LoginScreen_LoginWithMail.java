package com.av.backendgame.api.controller.service.loginscreen;

import com.av.backendgame.api.model.MailLoginForm;
import com.av.backendgame.dynamodb.Table_AccountLogin;
import com.av.backendgame.dynamodb.Table_UserData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public class Service_LoginScreen_LoginWithMail {

    public ResponseEntity<?> getRespone(MailLoginForm loginForm){
        String mail = loginForm.mail;
        int code = loginForm.code;
        return new ResponseEntity<>(Table_AccountLogin.getInstance().loginWithMail(mail, code), HttpStatus.OK);
    }
}
