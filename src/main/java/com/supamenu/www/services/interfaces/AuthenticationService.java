package com.supamenu.www.services.interfaces;

import com.supamenu.www.dtos.auth.RequestVerificationDTO;
import com.supamenu.www.dtos.auth.SignInDTO;
import com.supamenu.www.dtos.auth.VerifyUserDTO;
import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.utils.JWTAuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<ApiResponse<JWTAuthenticationResponse>> login(SignInDTO signInDTO);

    public ResponseEntity<ApiResponse<Object>> requestVerification(RequestVerificationDTO requestVerificationDTO);

    public ResponseEntity<ApiResponse<Object>> verifyAccount(VerifyUserDTO verifyAccountDTO);
}
