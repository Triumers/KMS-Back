package org.triumers.kmsback.common.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.triumers.kmsback.auth.command.domain.aggregate.enums.UserRole;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtUtil {// Git 충돌 복구용 주석

    private final SecretKey secretKey;
    private final Long expiredMs;

    @Autowired
    public JwtUtil(Environment env) {
        this.secretKey = new SecretKeySpec(
                Objects.requireNonNull(env.getProperty("token.secret")).getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
        this.expiredMs = Long.valueOf(Objects.requireNonNull(env.getProperty("token.expiration_time")));
    }

    public String getEmail(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public String createJwt(String email, UserRole role) {

        return Jwts.builder()
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
