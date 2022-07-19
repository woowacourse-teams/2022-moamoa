package com.woowacourse.moamoa.auth.config;

import com.woowacourse.moamoa.auth.controller.AuthenticationArgumentResolver;
import com.woowacourse.moamoa.auth.controller.AuthenticationInterceptor;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfiguration implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final AuthenticationArgumentResolver authenticationArgumentResolver;

    public AuthConfiguration(
            final AuthenticationInterceptor authenticationInterceptor,
            final AuthenticationArgumentResolver authenticationArgumentResolver) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.authenticationArgumentResolver = authenticationArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .excludePathPatterns("/**");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
