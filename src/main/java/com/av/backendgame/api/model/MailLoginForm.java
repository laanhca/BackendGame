package com.av.backendgame.api.model;

import io.swagger.annotations.ApiModelProperty;

public class MailLoginForm {
    @ApiModelProperty(required = true)
    private String mail;
    @ApiModelProperty(required = true)
    private int code;

    public String getMail() {
        return mail;
    }


    public int getCode() {
        return code;
    }

}
