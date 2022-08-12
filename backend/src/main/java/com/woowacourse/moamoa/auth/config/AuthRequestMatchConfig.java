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
import org.springframework.http.HttpMethod;

@Configuration
@AllArgsConstructor
public class AuthRequestMatchConfig {

    @Bean
    public AuthenticationRequestMatcher authenticationRequestMatcher1() {
        return new AuthenticationRequestMatcherBuilder()
                .addUpAuthenticationPath(HttpMethod.POST, "/api/studies", "/api/studies/\\d+/reviews",
                        "/api/studies/\\d+/reviews/\\d+", "/api/studies/\\w+/community/articles")
                .addUpAuthenticationPath(HttpMethod.GET, "/api/my/studies", "/api/members/me", "/api/members/me/role",
                        "/api/studies/\\w+/community/articles/\\w+", "/api/studies/\\w+/community/articles")
                .addUpAuthenticationPath(HttpMethod.PUT, "/api/studies/\\d+/reviews/\\d+")
                .addUpAuthenticationPath(HttpMethod.DELETE, "/api/studies/\\d+/reviews/\\d+",
                        "/api/studies/\\w+/community/articles/\\w+")
                .build();
    }

    @Bean
    public AuthenticationRequestMatcher authenticationRequestMatcher() {
        return new AuthenticationRequestMatcherBuilder()
                .addUpAuthenticationPath(HttpMethod.POST, "/api/studies", "/api/studies/\\d+/reviews",
                        "/api/studies/\\d+/reviews/\\d+", "/api/studies/\\w+/community/articles",
                        "/api/studies")
                .addUpAuthenticationPath(HttpMethod.GET, "/api/my/studies", "/api/members/me", "/api/members/me/role",
                        "/api/studies/\\w+/community/articles/\\w+", "/api/studies/\\w+/community/articles")
                .addUpAuthenticationPath(HttpMethod.PUT, "/api/studies/\\d+/reviews/\\d+")
                .addUpAuthenticationPath(HttpMethod.DELETE, "/api/studies/\\d+/reviews/\\d+",
                        "/api/studies/\\w+/community/articles/\\w+")
                .build();
    }
}
