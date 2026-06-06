package com.explore.springcloudeventsstreamplayground.sec17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SectionRunner {

    static void main() {
        SpringApplication.run(SectionRunner.class, "--section=sec17", "--config=01-consumer");
    }
}
