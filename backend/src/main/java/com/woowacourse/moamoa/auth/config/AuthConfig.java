package com.woowacourse.moamoa.auth.config;

import com.woowacourse.moamoa.auth.controller.AuthenticatedMemberResolver;
import com.woowacourse.moamoa.auth.controller.interceptor.AuthenticationInterceptor;
import com.woowacourse.moamoa.auth.controller.interceptor.PathMatcherContainer;
import com.woowacourse.moamoa.auth.controller.interceptor.PathMatcherInterceptor;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private final AuthenticatedMemberResolver authenticatedMemberResolver;

    private final PathMatcherContainer pathMatcherContainer;
    private final TokenProvider jwtTokenProvider;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedMemberResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**");
    }

    private HandlerInterceptor loginInterceptor() {
        return new PathMatcherInterceptor(new AuthenticationInterceptor(jwtTokenProvider), pathMatcherContainer)
                .excludePathPattern("/**", HttpMethod.OPTIONS)
                .includePathPattern("/api/studies/**", HttpMethod.POST)
                .includePathPattern("/api/studies/**", HttpMethod.PUT)
                .includePathPattern("/api/study/\\d+", HttpMethod.PUT)
                .includePathPattern("/api/studies/**", HttpMethod.DELETE)
                .includePathPattern("/api/members/me/**", HttpMethod.GET)
                .includePathPattern("/api/auth/refresh", HttpMethod.GET)
                .includePathPattern("/api/my/studies", HttpMethod.GET);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
