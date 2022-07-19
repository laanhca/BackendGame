package com.av.backendgame.api.controller.service.loginscreen;

import com.av.backendgame.dynamodb.Table_AccountLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Service_LoginScreen_SendMail {
    public ResponseEntity<?> getRespone(String mail){

        int code = (int)Math.floor(Math.random()*(9999-1000+1)+1000);
        String ip = new HttpUtils().getClientIpAddressIfServletRequestExist();
        return new ResponseEntity<>(Table_AccountLogin.getInstance().sendMail(mail, code, ip), HttpStatus.OK);
    }
}
