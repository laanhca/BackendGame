package com.av.backendgame.api.model;

import io.swagger.annotations.ApiModelProperty;

public class ChangeAvatarIdForm {
    @ApiModelProperty(required = true)
    private String token;
    @ApiModelProperty(required = true)

    private int avtId;

    public String getToken() {
        return token;
    }

    public int getAvtId() {
        return avtId;
    }

}
