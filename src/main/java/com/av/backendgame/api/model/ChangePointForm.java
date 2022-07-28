package com.av.backendgame.api.model;

import io.swagger.annotations.ApiModelProperty;

public class ChangePointForm {
    @ApiModelProperty(required = true)
    private String token;
    @ApiModelProperty(required = true)

    private int point;

    public String getToken() {
        return token;
    }


    public int getPoint() {
        return point;
    }

}
