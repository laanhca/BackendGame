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
        String mail = loginForm.getMail();
        int code = loginForm.getCode();
        Object result = Table_AccountLogin.getInstance().loginWithMail(mail, code);
        if(result!= null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if (result instanceof Boolean) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
}
