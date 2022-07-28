package com.av.backendgame.api.controller.service.loginscreen;

import com.av.backendgame.api.model.UsernameLoginForm;
import com.av.backendgame.dynamodb.Table_AccountLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class Service_LoginScreen_RegWithUsername {

    public ResponseEntity<?> getRespone(UsernameLoginForm loginForm){
        String credentials = loginForm.getCredentials();
        String password = loginForm.getPassword();
        String ip = new HttpUtils().getClientIpAddressIfServletRequestExist();
        Object result = Table_AccountLogin.getInstance().regWithUserName(credentials, password, ip);
        if(result!= null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
}
