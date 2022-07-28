package com.av.backendgame.api.model;

import io.swagger.annotations.ApiModelProperty;

public class ChangeNameForm {
    @ApiModelProperty(required = true)
    private String token;
    @ApiModelProperty(required = true)
    private String name;

    public String getToken() {
        return token;
    }


    public String getName() {
        return name;
    }

}
