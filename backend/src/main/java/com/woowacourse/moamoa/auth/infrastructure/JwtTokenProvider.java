package com.woowacourse.moamoa.auth.infrastructure;

import com.woowacourse.moamoa.auth.exception.TokenExpirationException;
import com.woowacourse.moamoa.auth.service.response.TokenResponseWithRefresh;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60; // 7일

    private final SecretKey key;
    private final long validityInMilliseconds;

    public JwtTokenProvider(
            @Value("${security.jwt.token.secret-key}") final String secretKey,
            @Value("${security.jwt.token.expire-length}") final long validityInMilliseconds
    ) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    @Override
    public TokenResponseWithRefresh createToken(final Long payload) {
        final Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject(payload.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken =  Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new TokenResponseWithRefresh(accessToken, refreshToken);
    }

    @Override
    public String getPayload(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public boolean validateToken(final String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Date tokenExpirationDate = claims.getBody().getExpiration();
            validateTokenExpiration(tokenExpirationDate);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private void validateTokenExpiration(Date tokenExpirationDate) {
        if (tokenExpirationDate.before(new Date())) {
            throw new TokenExpirationException();
        }
    }

    @Override
    public String recreationAccessToken(final Long githubId, final String refreshToken) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(refreshToken);

        Date tokenExpirationDate = claims.getBody().getExpiration();
        validateTokenExpiration(tokenExpirationDate);

        return createAccessToken(githubId);
    }

    private String createAccessToken(final Long githubId) {
        final Date now = new Date();

        return Jwts.builder()
                .setSubject(Long.toString(githubId))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
