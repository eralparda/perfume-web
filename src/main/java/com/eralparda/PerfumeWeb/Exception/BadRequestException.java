package com.eralparda.PerfumeWeb.Exception;

public class BadRequestException extends RuntimeException{
    //SERVİCE GÖRE YAZILIR
    public BadRequestException(String message){
        super(message);
    }
}
