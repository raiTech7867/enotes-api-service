package com.raiTech.exception;

public class JwtTokenExpireException extends RuntimeException{

    public JwtTokenExpireException(String message) {
        super(message);
    }
}
