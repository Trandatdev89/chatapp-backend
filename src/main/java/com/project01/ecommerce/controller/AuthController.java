package com.project01.ecommerce.controller;


import com.project01.ecommerce.model.request.AuthenticateRequest;
import com.project01.ecommerce.model.request.RegisterRequest;
import com.project01.ecommerce.model.request.TokenRequest;
import com.project01.ecommerce.model.response.ApiResponse;
import com.project01.ecommerce.model.response.AuthenticateResponse;
import com.project01.ecommerce.model.response.IntrospecResponse;
import com.project01.ecommerce.model.response.UserResponseDTO;
import com.project01.ecommerce.services.AuthServices;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthServices authServices;

    @PostMapping("/login")
    public ApiResponse<AuthenticateResponse> login(@RequestBody @Valid AuthenticateRequest authenticateRequest) {
        return ApiResponse.<AuthenticateResponse>builder()
                .code(200)
                .message("success!")
                .data(authServices.authenticate(authenticateRequest))
                .build();
    }

    @PostMapping("/introspectoken")
    public ApiResponse<IntrospecResponse> IntrospecToken(@RequestBody @Valid TokenRequest tokenRequest) {
        return ApiResponse.<IntrospecResponse>builder()
                .code(200)
                .message("success!")
                .data(authServices.introspecToken(tokenRequest))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody @Valid TokenRequest tokenRequest) {
        authServices.logout(tokenRequest);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("success!")
                .build();
    }

    @PostMapping("/resfresh-token")
    public ApiResponse<AuthenticateResponse> ResfreshToken(@RequestBody @Valid TokenRequest tokenRequest) {
        return ApiResponse.<AuthenticateResponse>builder()
                .code(200)
                .message("success!")
                .data(authServices.refreshToken(tokenRequest))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse register(@RequestBody @Valid RegisterRequest registerRequest) {

        authServices.register(registerRequest);
        return ApiResponse.<UserResponseDTO>builder()
                .code(200)
                .message("success!")
                .build();
    }

    @GetMapping("/google")
    public ApiResponse<AuthenticateResponse> user(OAuth2AuthenticationToken oAuth2AuthenticationToken,HttpServletResponse httpServletResponse) throws IOException{

        return ApiResponse.<AuthenticateResponse>builder()
                .code(200)
                .message("success!")
                .data(authServices.loginWithGoogle(oAuth2AuthenticationToken,httpServletResponse))
                .build();
    }
}
