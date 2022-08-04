package com.woowacourse.moamoa.auth.config;

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
    public AuthenticationRequestMatcher authenticationRequestMatcher() {
        return new AuthenticationRequestMatcherBuilder()
                .addUpAuthenticationPath(HttpMethod.POST, "/api/studies", "/api/studies/\\d+/reviews", "/api/studies/\\d+/reviews/\\d+")
                .addUpAuthenticationPath(HttpMethod.GET, "/api/my/studies")
                .addUpAuthenticationPath(HttpMethod.PUT, "/api/studies/\\d+/reviews/\\d+")
                .addUpAuthenticationPath(HttpMethod.DELETE, "/api/studies/\\d+/reviews/\\d+")
                .build();
    }
}
