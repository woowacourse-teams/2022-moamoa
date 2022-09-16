package com.woowacourse.moamoa.auth.infrastructure;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.woowacourse.moamoa.auth.exception.RefreshTokenExpirationException;
import com.woowacourse.moamoa.auth.service.response.AccessTokenResponse;
import com.woowacourse.moamoa.auth.service.response.TokensResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final SecretKey accessKey;
    private final SecretKey refreshKey;
    private final long accessExpireLength;
    private final long refreshExpireLength;

    public JwtTokenProvider(
            @Value("${security.jwt.token.access-secret-key}") final String accessSecretKey,
            @Value("${security.jwt.token.access-expire-length}") final long accessExpireLength,
            @Value("${security.jwt.token.refresh-secret-key}") final String refreshSecretKey,
            @Value("${security.jwt.token.refresh-expire-length}") final long refreshExpireLength
    ) {
        this.accessKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes(UTF_8));
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecretKey.getBytes(UTF_8));
        this.accessExpireLength = accessExpireLength;
        this.refreshExpireLength = refreshExpireLength;
    }

    @Override
    public TokensResponse createToken(final Long payload) {
        final Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject(payload.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessExpireLength))
                .signWith(accessKey, HS256)
                .compact();

        String refreshToken =  Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpireLength))
                .signWith(refreshKey, HS256)
                .compact();

        return new TokensResponse(accessToken, refreshToken, accessExpireLength, refreshExpireLength);
    }

    @Override
    public String getPayload(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public String getPayloadWithExpiredToken(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    @Override
    public boolean validateToken(final String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token);

            Date tokenExpirationDate = claims.getBody().getExpiration();
            validateTokenExpiration(tokenExpirationDate);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public AccessTokenResponse recreationAccessToken(final Long memberId, final String refreshToken) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(refreshKey)
                .build()
                .parseClaimsJws(refreshToken);

        Date tokenExpirationDate = claims.getBody().getExpiration();
        validateTokenExpiration(tokenExpirationDate);

        return new AccessTokenResponse(createAccessToken(memberId), accessExpireLength);
    }

    private void validateTokenExpiration(Date tokenExpirationDate) {
        if (tokenExpirationDate.before(new Date())) {
            throw new RefreshTokenExpirationException();
        }
    }

    private String createAccessToken(final Long memberId) {
        final Date now = new Date();

        return Jwts.builder()
                .setSubject(Long.toString(memberId))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessExpireLength))
                .signWith(accessKey, HS256)
                .compact();
    }
}
