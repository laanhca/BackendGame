package com.av.backendgame.api.controller.service.loginscreen;

import com.av.backendgame.api.model.ChangeAvatarIdForm;
import com.av.backendgame.dynamodb.Table_UserData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Service_LoginScreen_UpdateAvtId {
    public ResponseEntity<?> getRespone(ChangeAvatarIdForm form){
        long userId = Table_UserData.getInstance().getUserId(form.getToken());
        if(userId == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(Table_UserData.getInstance().changeAvtId(userId, form.getAvtId()), HttpStatus.OK);
    }
}
