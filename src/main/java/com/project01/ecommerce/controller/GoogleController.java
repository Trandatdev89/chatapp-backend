//package com.project01.ecommerce.controller;
//
//import com.project01.ecommerce.model.response.ApiResponse;
//import com.project01.ecommerce.model.response.AuthenticateResponse;
//import com.project01.ecommerce.services.AuthServices;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//
//@RestController
//public class GoogleController {
//
//    @Autowired
//    private AuthServices authServices;
//
//
//    @GetMapping("/login/oauth2/code/google")
//    public ApiResponse<AuthenticateResponse> user(OAuth2AuthenticationToken oAuth2AuthenticationToken,HttpServletResponse response) throws IOException {
//
//        response.sendRedirect("http://localhost:3000/chatapp");
//        return ApiResponse.<AuthenticateResponse>builder()
//                .code(200)
//                .message("success!")
//                .data(authServices.loginWithGoogle(oAuth2AuthenticationToken))
//                .build();
//    }
//}
