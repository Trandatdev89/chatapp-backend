package com.project01.ecommerce.services;

import com.project01.ecommerce.model.request.AuthenticateRequest;
import com.project01.ecommerce.model.request.RegisterRequest;
import com.project01.ecommerce.model.request.TokenRequest;
import com.project01.ecommerce.model.response.AuthenticateResponse;
import com.project01.ecommerce.model.response.IntrospecResponse;
import com.project01.ecommerce.model.response.UserResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.io.IOException;
import java.util.List;

public interface AuthServices {

    AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest);
    IntrospecResponse introspecToken(TokenRequest tokenRequest);
//    List<UserResponseDTO> getAllUsers();
    void logout(TokenRequest tokenRequest);
    AuthenticateResponse refreshToken(TokenRequest tokenRequest);
    void register(RegisterRequest registerRequest);
//    UserResponseDTO getMyInfo();
    AuthenticateResponse loginWithGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken, HttpServletResponse httpServletResponse) throws IOException;
}
