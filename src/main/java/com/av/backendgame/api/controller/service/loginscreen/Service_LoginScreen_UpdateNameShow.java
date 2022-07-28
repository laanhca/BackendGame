package com.av.backendgame.api.controller.service.loginscreen;

import com.av.backendgame.api.model.ChangeNameForm;
import com.av.backendgame.dynamodb.Table_UserData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Service_LoginScreen_UpdateNameShow {
    public ResponseEntity<?> getRespone(ChangeNameForm form){
        long userId = Table_UserData.getInstance().getUserId(form.getToken());
        if(userId == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(Table_UserData.getInstance().changeNameShow(userId, form.getName()), HttpStatus.OK);
    }
}
