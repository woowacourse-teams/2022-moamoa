package com.woowacourse.moamoa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class MoamoaApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MoamoaApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            log.debug("hello !");
        };
    }
}
