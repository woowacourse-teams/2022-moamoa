package com.woowacourse.moamoa.auth.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.WebMVCTest;
import com.woowacourse.moamoa.auth.config.AuthenticationExtractor;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class TokenProviderTest extends WebMVCTest {

    private static final String TEST_SECRET_KEY = "9d0bd354d2a68141d2ced83c26fe3fb72046783c19e7b727a45804d7d80c96a1541f9decbc3833519bd168ff7735d15a0e0737f40b20977bece9d8c0220425a1";

    @DisplayName("만료된 토큰인지 확인한다")
    @Test
    void isExpiredToken() {
        final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer token");

        final String payload = AuthenticationExtractor.extract(mockHttpServletRequest);

        final Date now = new Date();
        final Date validity = new Date(now.getTime() + 3600000);

        final SecretKey keys = Keys.hmacShaKeyFor(TEST_SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        final String token = Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(new Date(now.getTime() - 100))
                .setExpiration(validity)
                .signWith(keys, SignatureAlgorithm.HS256)
                .compact();

        assertThat(tokenProvider.validateToken(token)).isFalse();
    }

    @DisplayName("유효한 토큰을 검증한다.")
    @Test
    void validateTokenByValidToken() {
        String token = tokenProvider.createToken(1L).getAccessToken();

        assertThat(tokenProvider.validateToken(token)).isTrue();
    }

    @DisplayName("유효하지 않은 토큰을 검증한다.")
    @Test
    void validateTokenByInvalidToken() {
        String token = tokenProvider.createToken(1L).getAccessToken();

        String invalidToken = token + "dummy";

        assertThat(tokenProvider.validateToken(invalidToken)).isFalse();
    }

    @DisplayName("JwtToken payload 검증한다.")
    @Test
    void validatePayload() {
        String token = tokenProvider.createToken(1L).getAccessToken();

        assertThat(tokenProvider.getPayload(token)).isEqualTo("1");
    }
    
    @DisplayName("JwtToken 형식을 검증한다.")
    @Test
    void validateJwtTokenFormat() {
        String token = tokenProvider.createToken(1L).getAccessToken();

        final String[] parts = token.split("\\.");

        assertThat(parts.length).isEqualTo(3);
    }
}
