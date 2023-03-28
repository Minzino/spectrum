package com.spectrum.common.auth.provider;

import com.spectrum.common.auth.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private final static String INVALID_TOKEN_MESSAGE = "유효하지 않은 토큰입니다.";
    private final SecretKey secretKey;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    private JwtProvider(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
        this.accessTokenValidityInMilliseconds = jwtProperties.getAccessTokenExpireLength();
        this.refreshTokenValidityInMilliseconds = jwtProperties.getRefreshTokenExpireLength();
    }

    public String createAccessToken(String payload) {
        return createToken(String.valueOf(payload), accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken(String payload) {
        return createToken(String.valueOf(payload), refreshTokenValidityInMilliseconds);
    }

    public String createToken(String payload, long expireLength) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey)
            .compact();
    }

    public String getPayload(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        } catch (JwtException e) {
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE, e);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE, e);
        }
    }
}
