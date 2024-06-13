package com.supamenu.www.utils;

import com.supamenu.www.models.User;
import lombok.Data;

@Data
public class JWTAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private User user;

    public JWTAuthenticationResponse(String accessToken , User user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}
