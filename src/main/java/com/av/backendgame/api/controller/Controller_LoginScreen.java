package com.av.backendgame.api.controller;

import com.av.backendgame.BackendGameApplication;
import com.av.backendgame.api.controller.service.loginscreen.*;
import com.av.backendgame.api.model.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(BackendGameApplication.LOGIN)
@RestController
public class Controller_LoginScreen {
    private static final String REG_WITH_USERNAME = "reg_with_username";
    private static final String SEND_MAIL = "send_mail";
    private static final String LOGIN_WITH_USERNAME = "login_with_username";
    private static final String LOGIN_WITH_MAIL = "login_with_mail";
    private static final String UPDATE_AVT_ID = "update_avt_id";
    private static final String UPDATE_NAME_SHOW = "update_nameshow";
    private static final String UPDATE_POINT = "update_point";
    private static final String GET_NAME_SHOW = "get_nameshow";
    private static final String GET_POINT = "get_point";
    private static final String GET_LEADERBOARD = "get_leaderboard";

    @ApiOperation(value = "Register an account with username and password")
    @PostMapping(value = REG_WITH_USERNAME, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> regWithUsername(@RequestBody UsernameLoginForm loginForm){
        return new Service_LoginScreen_RegWithUsername().getRespone(loginForm);
    }
    @ApiOperation(value = "Login with username and password")
    @PostMapping(value = LOGIN_WITH_USERNAME, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginWithUsername(@RequestBody UsernameLoginForm loginForm){
        return new Service_LoginScreen_LoginWithUsername().getRespone(loginForm);
    }

    @ApiOperation(value = "Request to send verification code to mail")
    @PostMapping(value = SEND_MAIL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMail(@RequestBody String mail){
        return new Service_LoginScreen_SendMail().getRespone(mail);
    }

    @ApiOperation(value = "Login with mail and verify code")
    @PostMapping(value = LOGIN_WITH_MAIL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginWithMail(@RequestBody MailLoginForm loginForm){
        return new Service_LoginScreen_LoginWithMail().getRespone(loginForm);
    }

    @ApiOperation(value = "Use token to change avatar id")
    @PostMapping(value = UPDATE_AVT_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAvtId(@RequestBody ChangeAvatarIdForm changeForm){
        return new Service_LoginScreen_UpdateAvtId().getRespone(changeForm);
    }

    @ApiOperation(value = "Use token to change name show")
    @PostMapping(value = UPDATE_NAME_SHOW, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateNameShow(@RequestBody ChangeNameForm changeForm){
        return new Service_LoginScreen_UpdateNameShow().getRespone(changeForm);
    }

    @ApiOperation(value = "Use token to get name show")
    @PostMapping(value = GET_NAME_SHOW, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNameShow(@RequestBody String token){
        return new Service_LoginScreen_GetNameShow().getRespone(token);
    }
    @ApiOperation(value = "Use token to get point")
    @PostMapping(value = GET_POINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPoint(@RequestBody String token){
        return new Service_LoginScreen_GetPoint().getRespone(token);
    }

    @ApiOperation(value = "Use token to update point")
    @PostMapping(value = UPDATE_POINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePoint(@RequestBody ChangePointForm updateForm){
        return new Service_LoginScreen_UpdatePoint().getRespone(updateForm);
    }

    @ApiOperation(value = "Use token to update point")
    @GetMapping(value = GET_LEADERBOARD, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLeaderboard(){
        return new Service_LoginScreen_GetLeaderboard().getRespone();
    }



}
