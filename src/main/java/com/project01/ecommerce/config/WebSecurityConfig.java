package com.project01.ecommerce.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.project01.ecommerce.enums.EnumException;
import com.project01.ecommerce.exception.CustomException.AppException;
import com.project01.ecommerce.model.request.TokenRequest;
import com.project01.ecommerce.services.AuthServices;
import com.project01.ecommerce.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    @Lazy
    private SecurityUtil securityUtil;

    @Autowired
    private AuthServices authServices;

    private final String[] PUBLIC_MATCHERS = {
            "/auth/**","/login","/","/ws/**","/avatar/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request->request.requestMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated())
                .oauth2ResourceServer(auth->auth
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                                .decoder(jwtDecoder()))
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())

                        .accessDeniedHandler(new JwtAccessDeniedEntryPoint()))
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

       return http.build();
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder nimbusJwtDecoder=NimbusJwtDecoder
                .withSecretKey(securityUtil.getSecretKey())
                .macAlgorithm(SecurityUtil.MAC_ALGORITHM)
                .build();
        return token -> {
            try{
                var responseToken=authServices.introspecToken(TokenRequest.builder().token(token).build());
                if(!responseToken.isValid()){
                    throw new AppException(EnumException.INVALID_TOKEN);
                }
                var tokenDecode = nimbusJwtDecoder.decode(token);
                return tokenDecode;
            }catch (Exception e){
                System.out.println(e.getMessage());
                throw e;
            }
        };


    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("https://chatapp-frontend-eight-plum.vercel.app/"); // Thay vì "*", chỉ định rõ origin
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*"); // Đảm bảo cho phép mọi header
        corsConfiguration.setAllowCredentials(true); // Cho phép gửi credentials (token, cookies,...)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public NimbusJwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(securityUtil.getSecretKey()));
    }
}
