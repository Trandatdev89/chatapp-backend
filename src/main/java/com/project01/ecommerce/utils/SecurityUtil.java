package com.project01.ecommerce.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jwt.SignedJWT;
import com.project01.ecommerce.entity.UserEntity;
import com.project01.ecommerce.enums.EnumException;
import com.project01.ecommerce.exception.CustomException.AppException;
import com.project01.ecommerce.repository.InvalidateTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SecurityUtil {

    public static final MacAlgorithm MAC_ALGORITHM= MacAlgorithm.HS256;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Autowired
    private InvalidateTokenRepository invalidateTokenRepository;

    @Autowired
    @Lazy
    private JwtEncoder jwtDecoder;

    public String generateToken(UserEntity user) {
        JwsHeader jwsHeader=JwsHeader.with(MAC_ALGORITHM).build();
        Instant now = Instant.now();

        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .claim("scope",user.getAuthorities().stream().map(item->item.toString()).distinct().collect(Collectors.joining(" ")))
                .issuedAt(now)
                .expiresAt(now.plus(expiration, ChronoUnit.SECONDS))
                .subject(String.valueOf(user.getId()))
                .id(UUID.randomUUID().toString())
                .build();

        String token=jwtDecoder.encode(JwtEncoderParameters.from(jwsHeader,jwtClaimsSet)).getTokenValue();

        return token;
    }

    public SignedJWT verifyToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT=SignedJWT.parse(token);
        JWSVerifier jwsVerifier=new MACVerifier(getSecretKey());
        boolean verified=signedJWT.verify(jwsVerifier);
        if(!(verified && signedJWT.getJWTClaimsSet().getExpirationTime().after(new Date()))){
            throw new AppException(EnumException.INVALID_TOKEN);
        }
        if((invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))){
            throw new AppException(EnumException.INVALID_TOKEN);
        }
        return signedJWT;
    }

    public SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(secretKey).decode();
        return new SecretKeySpec(keyBytes,0,keyBytes.length,MAC_ALGORITHM.getName());
    }

}
