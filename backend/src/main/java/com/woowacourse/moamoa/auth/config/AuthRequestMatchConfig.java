package com.woowacourse.moamoa.auth.config;

import static org.springframework.http.HttpMethod.*;

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
                .addUpAuthenticationPath(POST,
                        "/api/studies/\\d+/reviews",
                        "/api/studies/\\d+/reviews/\\d+",
                        "/api/studies/\\w+/\\w+/articles",
                        "/api/studies")
                .addUpAuthenticationPath(GET,
                        "/api/my/studies",
                        "/api/members/me",
                        "/api/members/me/role",
                        "/api/studies/\\w+/community/articles/\\w+",
                        "/api/studies/\\w+/community/articles",
                        "/api/studies/\\d+/reference-room/links"
                )
                .addUpAuthenticationPath(HttpMethod.PUT,
                        "/api/studies/\\d+/reviews/\\d+",
                        "/api/studies/\\d+/reference-room/links/\\d+",
                        "/api/study/\\d+"
                )
                .addUpAuthenticationPath(HttpMethod.DELETE,
                        "/api/studies/\\d+/reviews/\\d+",
                        "/api/studies/\\w+/\\w+/articles/\\w+")
                .build();
    }
}
