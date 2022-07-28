package com.av.backendgame.api.controller.service.loginscreen;

import com.av.backendgame.api.model.UsernameLoginForm;
import com.av.backendgame.dynamodb.Table_AccountLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class Service_LoginScreen_LoginWithUsername {
    public ResponseEntity<?> getRespone(UsernameLoginForm loginForm){
        String credentials = loginForm.getCredentials();
        String password = loginForm.getPassword();
        Object result= Table_AccountLogin.getInstance().loginWithUserName(credentials, password);
        if(result!= null){
            return new ResponseEntity<>( result, HttpStatus.OK);
        } else if (result instanceof Boolean) {
            return new ResponseEntity<>( null, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
    }
}
