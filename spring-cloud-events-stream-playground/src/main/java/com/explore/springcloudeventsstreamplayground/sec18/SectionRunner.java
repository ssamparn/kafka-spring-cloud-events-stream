package com.explore.springcloudeventsstreamplayground.sec18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SectionRunner {

    static void main() {
//        SpringApplication.run(SectionRunner.class, "--section=sec18", "--config=01-retry-fixed-delay");
//        SpringApplication.run(SectionRunner.class, "--section=sec18", "--config=02-retry-exponential-delay");
//        SpringApplication.run(SectionRunner.class, "--section=sec18", "--config=03-retryable-exceptions");
        SpringApplication.run(SectionRunner.class, "--section=sec18", "--config=04-dlq");
    }
}
