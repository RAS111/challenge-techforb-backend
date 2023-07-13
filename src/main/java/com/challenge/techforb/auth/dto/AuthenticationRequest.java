package com.challenge.techforb.auth.dto;

import lombok.*;

@Getter
@Setter
public class AuthenticationRequest {
    private String email;
    private String password;
}
