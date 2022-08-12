package com.woowacourse.moamoa.auth.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import com.woowacourse.moamoa.auth.controller.matcher.AuthenticationRequestMatcher;
import com.woowacourse.moamoa.auth.controller.matcher.AuthenticationRequestMatcherBuilder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AuthRequestMatchConfig {

    @Bean
    public AuthenticationRequestMatcher authenticationRequestMatcher() {
        return new AuthenticationRequestMatcherBuilder()
                .addUpAuthenticationPath(POST, "/api/studies", "/api/studies/\\d+/reviews", "/api/studies/\\d+/reviews/\\d+")
                .addUpAuthenticationPath(GET, "/api/my/studies", "/api/members/me", "/api/members/me/role")
                .addUpAuthenticationPath(PUT, "/api/studies/\\d+/reviews/\\d+")
                .addUpAuthenticationPath(DELETE, "/api/studies/\\d+/reviews/\\d+")
                .build();
    }
}
