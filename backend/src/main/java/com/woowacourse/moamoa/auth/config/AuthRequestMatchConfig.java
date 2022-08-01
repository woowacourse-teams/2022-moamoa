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

    private final AuthenticationRequestMatcherBuilder authenticationRequestMatcherBuilder;

    @Bean
    public AuthenticationRequestMatcher authenticationRequestMatcher() {
        return authenticationRequestMatcherBuilder
                .setUpAuthenticationPath(HttpMethod.POST, "/api/studies", "/api/studies/\\d+/reviews")
                .setUpAuthenticationPath(HttpMethod.GET, "/api/my/studies")
                .build();
    }
}
