package com.av.backendgame.api.controller.service.loginscreen;

import com.av.backendgame.dynamodb.Table_UserData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Service_LoginScreen_GetPoint {
    public ResponseEntity<?> getRespone(String token) {
        long userId = Table_UserData.getInstance().getUserId(token);
        if(userId == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(Table_UserData.getInstance().getPoint(userId), HttpStatus.OK);
    }
}
