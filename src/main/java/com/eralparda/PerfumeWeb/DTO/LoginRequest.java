package com.eralparda.PerfumeWeb.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
