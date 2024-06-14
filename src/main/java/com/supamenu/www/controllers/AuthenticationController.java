package com.supamenu.www.controllers;

import com.supamenu.www.dtos.auth.RequestVerificationDTO;
import com.supamenu.www.dtos.auth.VerifyUserDTO;
import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.services.interfaces.AuthenticationService;
import com.supamenu.www.utils.JWTAuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.supamenu.www.dtos.auth.SignInDTO;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JWTAuthenticationResponse>> login(@Valid @RequestBody SignInDTO signInDTO) {
        return authenticationService.login(signInDTO);
    }

    @PostMapping("/request-verification")
    public ResponseEntity<ApiResponse<Object>> requestVerification(@Valid @RequestBody RequestVerificationDTO requestVerificationDTO) {
        return authenticationService.requestVerification(requestVerificationDTO);
    }

    @PostMapping("/verify-account")
    public ResponseEntity<ApiResponse<Object>> verifyAccount(@Valid @RequestBody VerifyUserDTO requestVerificationDTO) {
        return authenticationService.verifyAccount(requestVerificationDTO);
    }
}
