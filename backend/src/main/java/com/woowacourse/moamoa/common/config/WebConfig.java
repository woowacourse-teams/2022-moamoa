package com.woowacourse.moamoa.common.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String[] ALLOW_METHODS = {"GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "OPTIONS", "PATCH"};

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageableVerificationArgumentResolver());
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods(ALLOW_METHODS)
                .exposedHeaders(HttpHeaders.LOCATION);
    }
}
