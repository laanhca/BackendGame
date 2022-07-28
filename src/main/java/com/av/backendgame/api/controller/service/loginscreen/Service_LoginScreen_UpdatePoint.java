package com.av.backendgame.api.controller.service.loginscreen;

import com.av.backendgame.api.model.ChangePointForm;
import com.av.backendgame.dynamodb.Table_Leaderboard;
import com.av.backendgame.dynamodb.Table_UserData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Service_LoginScreen_UpdatePoint {

    public ResponseEntity<?> getRespone(ChangePointForm updateForm) {
        long userId = Table_UserData.getInstance().getUserId(updateForm.getToken());
        if(userId == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        Table_Leaderboard.getInstance().updateLeaderboard(updateForm.getToken(), updateForm.getPoint());
        return new ResponseEntity<>(Table_UserData.getInstance().updatePoint(userId, updateForm.getPoint()), HttpStatus.OK);
    }
}
