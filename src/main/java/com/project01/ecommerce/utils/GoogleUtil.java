package com.project01.ecommerce.utils;


import com.project01.ecommerce.entity.UserEntity;
import com.project01.ecommerce.entity.UserRole;
import com.project01.ecommerce.repository.RoleRepository;
import com.project01.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GoogleUtil {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    public String generateTokenGoogle(OAuth2User oAuth2User) {
        if (!userRepository.existsByGoogleid(oAuth2User.getAttribute("sub"))) {
            UserEntity user = UserEntity.builder()
                    .username(oAuth2User.getAttribute("name"))
                    .googleid(oAuth2User.getAttribute("sub"))
                    .fullname(oAuth2User.getAttribute("name"))
                    .email(oAuth2User.getAttribute("email"))
                    .picture(oAuth2User.getAttribute("picture"))
                    .build();

            user.setRoles(List.of(UserRole.builder().role("MEMBER").user(user).build()));
            userRepository.save(user);

        }
        UserEntity user = userRepository.findByGoogleid(oAuth2User.getAttribute("sub"));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(oAuth2User.getAttribute("name"),"123456");
        Authentication authentication=authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        String token = securityUtil.generateToken(user);
        return token;
    }
}
