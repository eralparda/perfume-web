package com.eralparda.PerfumeWeb.Exception;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String message){
        super(message);
    }
}
