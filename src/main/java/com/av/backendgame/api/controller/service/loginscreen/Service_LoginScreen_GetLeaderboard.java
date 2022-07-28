package com.av.backendgame.api.controller.service.loginscreen;

import com.av.backendgame.dynamodb.Table_Leaderboard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Service_LoginScreen_GetLeaderboard {
    public ResponseEntity<?> getRespone() {
        return new ResponseEntity<>(Table_Leaderboard.getInstance().getLeaderboard(), HttpStatus.OK);
    }
}
