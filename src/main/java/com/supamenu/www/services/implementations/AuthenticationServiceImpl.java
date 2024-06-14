package com.supamenu.www.services.implementations;

import com.supamenu.www.dtos.auth.RequestVerificationDTO;
import com.supamenu.www.dtos.auth.SignInDTO;
import com.supamenu.www.dtos.auth.VerifyUserDTO;
import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.enumerations.otp.EOTPStatus;
import com.supamenu.www.enumerations.otp.EOTPType;
import com.supamenu.www.exceptions.CustomException;
import com.supamenu.www.models.OTP;
import com.supamenu.www.repositories.IOTPRepository;
import com.supamenu.www.repositories.IUserRepository;
import com.supamenu.www.security.JwtTokenProvider;
import com.supamenu.www.security.UserPrincipal;
import com.supamenu.www.services.interfaces.AuthenticationService;
import com.supamenu.www.utils.JWTAuthenticationResponse;
import com.supamenu.www.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.supamenu.www.exceptions.BadRequestAlertException;
import com.supamenu.www.models.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserServiceImpl userService;
    private final MailServiceImpl mailService;
    private final IUserRepository userRepository;
    private final IOTPRepository otpRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationProvider authenticationProvider;
    private final TemplateEngine templateEngine;

    @Override
    public ResponseEntity<ApiResponse<JWTAuthenticationResponse>> login(SignInDTO signInDTO) {
        try {
            String jwt = null;
            UserPrincipal userPrincipal = null;
            User user = null;
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword());
            Authentication authentication = authenticationProvider.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwt = jwtTokenProvider.generateAccessToken(authentication);
            userPrincipal = UserUtils.getLoggedInUser();
            user = userService.findUserById(userPrincipal.getId());
            return ApiResponse.success("Successfully logged in", HttpStatus.OK, new JWTAuthenticationResponse(jwt, user));
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }


    @Override
    public ResponseEntity<ApiResponse<Object>> requestVerification(RequestVerificationDTO requestVerificationDTO) {
        try {
            User user = userRepository.findUserByEmail(requestVerificationDTO.getEmail()).orElseThrow(() -> new BadRequestAlertException("User with provided email not found"));
            int otpDuration = 20; // 20 minutes
            OTP otpEntity = new OTP(EOTPType.ACCOUNT_VERIFICATION, user, otpDuration);
            otpRepository.save(otpEntity);
            Context context = new Context();
            context.setVariable("firstName", user.getFirstName());
            context.setVariable("otp", otpEntity.getCode());
            String content = templateEngine.process("verification-email", context);
            mailService.sendEmail(user.getEmail(), "Verification Email", content, true);
            return ApiResponse.success("Verification email sent successfully", HttpStatus.OK, null);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> verifyAccount(VerifyUserDTO verifyAccountDTO) {
        try {
            User user = userRepository.findUserByEmail(verifyAccountDTO.getEmail()).orElseThrow(() -> new BadRequestAlertException("User with provided email not found"));
            OTP otp = otpRepository.findOTPByCodeAndUserAndType(verifyAccountDTO.getCode(), user, EOTPType.ACCOUNT_VERIFICATION).orElseThrow(() -> new BadRequestAlertException("Invalid OTP"));
            if (otp.isValid()) {
                user.setVerified(true);
                otp.setStatus(EOTPStatus.USED);
                userRepository.save(user);
                otpRepository.save(otp);
                Context context = new Context();
                context.setVariable("firstName", user.getFirstName());
                String content = templateEngine.process("verification-success-email", context);
                mailService.sendEmail(user.getEmail(), "Account Verified", content, true);
                return ApiResponse.success("Account verified successfully", HttpStatus.OK, null);
            }
            otp.setStatus(EOTPStatus.EXPIRED);
            otpRepository.save(otp);
            return ApiResponse.error("OTP has expired", HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }
}
