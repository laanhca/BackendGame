package com.av.backendgame.api.controller.service.loginscreen;

import com.av.backendgame.api.model.UsernameLoginForm;
import com.av.backendgame.dynamodb.Table_AccountLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class Service_LoginScreen_LoginWithUsername {
    public ResponseEntity<?> getRespone(UsernameLoginForm loginForm){
        String credentials = loginForm.credentials;
        String password = loginForm.password;
        return new ResponseEntity<>(Table_AccountLogin.getInstance().loginWithUserName(credentials, password), HttpStatus.OK);
    }
}
