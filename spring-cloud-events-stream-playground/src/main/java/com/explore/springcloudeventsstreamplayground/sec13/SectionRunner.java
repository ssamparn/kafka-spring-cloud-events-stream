package com.explore.springcloudeventsstreamplayground.sec13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class SectionRunner {

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class Consumer {
        static void main() {
            SpringApplication.run(
                    com.explore.springcloudeventsstreamplayground.sec13.SectionRunner.Consumer.class, "--section=sec13", "--config=01-simple-consumer"
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.producer")
    static class Producer {
        static void main() {
            SpringApplication.run(
                    com.explore.springcloudeventsstreamplayground.sec13.SectionRunner.Producer.class, "--section=sec13", "--config=02-simple-producer"
            );
        }
    }
}

