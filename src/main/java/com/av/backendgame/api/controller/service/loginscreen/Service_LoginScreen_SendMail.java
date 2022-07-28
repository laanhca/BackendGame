package com.av.backendgame.api.controller.service.loginscreen;

import com.av.backendgame.dynamodb.Table_AccountLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Service_LoginScreen_SendMail {
    public ResponseEntity<?> getRespone(String mail){

        int code = (int)Math.floor(Math.random()*(9999-1000+1)+1000);
        String ip = new HttpUtils().getClientIpAddressIfServletRequestExist();
        Object result = Table_AccountLogin.getInstance().sendMail(mail, code, ip);
        if(result==null) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else if (result instanceof Boolean) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }
}
