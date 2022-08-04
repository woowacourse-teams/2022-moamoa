package com.woowacourse.moamoa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class MoamoaApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MoamoaApplication.class, args);
    }
}
