package com.blueyonder.ecomapp.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class LoginRequest {
    private final String username;
    private final String password;
}
