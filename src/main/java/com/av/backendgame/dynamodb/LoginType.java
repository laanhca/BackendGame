package com.av.backendgame.dynamodb;

public enum LoginType {
    USERNAME(1),
    MAIL(2),
    GUEST(3);

    private final int value;
    private LoginType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
