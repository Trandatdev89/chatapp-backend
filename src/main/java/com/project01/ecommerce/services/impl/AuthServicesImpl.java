package com.project01.ecommerce.services.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import com.project01.ecommerce.entity.InvalidTokenEntity;
import com.project01.ecommerce.entity.UserEntity;
import com.project01.ecommerce.entity.UserRole;
import com.project01.ecommerce.enums.EnumException;
import com.project01.ecommerce.exception.CustomException.AppException;
import com.project01.ecommerce.model.request.AuthenticateRequest;
import com.project01.ecommerce.model.request.RegisterRequest;
import com.project01.ecommerce.model.request.TokenRequest;
import com.project01.ecommerce.model.response.AuthenticateResponse;
import com.project01.ecommerce.model.response.IntrospecResponse;
import com.project01.ecommerce.model.response.UserResponseDTO;
import com.project01.ecommerce.repository.InvalidateTokenRepository;
import com.project01.ecommerce.repository.RoleRepository;
import com.project01.ecommerce.repository.UserRepository;
import com.project01.ecommerce.services.AuthServices;
import com.project01.ecommerce.utils.GoogleUtil;
import com.project01.ecommerce.utils.SecurityUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AuthServicesImpl implements AuthServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private GoogleUtil googleUtil;

    @Autowired
    private InvalidateTokenRepository invalidateTokenRepository;

    @Override
    public AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest) {
        UserEntity user = userRepository.findByUsername(authenticateRequest.getUsername())
                .orElseThrow(()-> new AppException(EnumException.USER_NOTFOUND));
        if(!passwordEncoder.matches(authenticateRequest.getPassword(), user.getPassword())) {
            throw new AppException(EnumException.PASSWORD_WRONG);
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(),authenticateRequest.getPassword());
        AuthenticationManager authenticationManager=authenticationManagerBuilder.getObject();
        Authentication authentication=authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = securityUtil.generateToken(user);
        return AuthenticateResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Override
    public IntrospecResponse introspecToken(TokenRequest tokenRequest){
        boolean isvalid= false;
        try {
            securityUtil.verifyToken(tokenRequest.getToken());
            isvalid = true;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return IntrospecResponse.builder()
                .valid(isvalid)
                .build();
    }

    @Override
    public void logout(TokenRequest tokenRequest) {
        try {
            SignedJWT signedJWT = securityUtil.verifyToken(tokenRequest.getToken());
            Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
            String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            String username = signedJWT.getJWTClaimsSet().getSubject();
            UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(EnumException.USER_NOTFOUND));

            InvalidTokenEntity inValidateToken = InvalidTokenEntity.builder()
                    .id(jwtId)
                    .token(tokenRequest.getToken())
                    .expirytime(expirationDate)
                    .user(user)
                    .build();

            invalidateTokenRepository.save(inValidateToken);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthenticateResponse refreshToken(TokenRequest tokenRequest) {
        try {
            SignedJWT signedJWT=securityUtil.verifyToken(tokenRequest.getToken());
            String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            String username = signedJWT.getJWTClaimsSet().getSubject();
            Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
            UserEntity user=userRepository.findByUsername(username).orElseThrow(() -> new AppException(EnumException.USER_NOTFOUND));

            InvalidTokenEntity inValidateToken = InvalidTokenEntity.builder()
                    .id(jwtId)
                    .expirytime(expirationDate)
                    .user(user)
                    .build();
            invalidateTokenRepository.save(inValidateToken);

            String token = securityUtil.generateToken(user);
            return AuthenticateResponse.builder()
                    .token(token)
                    .authenticated(true)
                    .build();

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new AppException(EnumException.USER_EXITSED);
        }
        List<UserRole> userRoles=new ArrayList<>();

        UserEntity userEntity=UserEntity.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(registerRequest.getPhone())
                .fullname(registerRequest.getFullname())
                .email(registerRequest.getEmail())
                .build();

        UserRole userRole=UserRole.builder()
                .role("MEMBER")
                .user(userEntity)
                .build();

        userRoles.add(userRole);
        userEntity.setRoles(userRoles);
        UserEntity userRegister=userRepository.save(userEntity);

    }



    @Override
    public AuthenticateResponse loginWithGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken, HttpServletResponse httpServletResponse) throws IOException {
        OAuth2User oAuth2User =oAuth2AuthenticationToken.getPrincipal();
        httpServletResponse.sendRedirect("http://localhost:3000/chatapp");
        return AuthenticateResponse.builder()
                .token(googleUtil.generateTokenGoogle(oAuth2User))
                .authenticated(true)
                .build();
    }

}
