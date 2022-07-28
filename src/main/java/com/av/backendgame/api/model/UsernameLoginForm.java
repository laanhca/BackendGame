package com.av.backendgame.api.model;

import io.swagger.annotations.ApiModelProperty;

public class UsernameLoginForm {
    @ApiModelProperty(required = true)
    private String credentials;
    @ApiModelProperty(required = true)
    private String password;

    public String getCredentials() {
        return credentials;
    }


    public String getPassword() {
        return password;
    }

}
