package com.woowacourse.moamoa.common.config;

import com.woowacourse.moamoa.MoamoaApplication;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
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
                .allowedOrigins("https://dev.moamoa.space", "https://moamoa.space")
                .allowedMethods(ALLOW_METHODS)
                .exposedHeaders(HttpHeaders.LOCATION)
                .allowCredentials(true);
    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(MoamoaApplication.class);
    }
}
