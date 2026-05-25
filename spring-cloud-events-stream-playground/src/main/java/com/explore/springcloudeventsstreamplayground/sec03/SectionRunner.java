package com.explore.springcloudeventsstreamplayground.sec03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class SectionRunner {

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class Consumer {
        static void main() {
            SpringApplication.run(
//                    Consumer.class, "--section=sec03", "--config=01-consumer" // config for simple string consumer
                    Consumer.class, "--section=sec03", "--config=03-message-consumer" // config for kafka message consumer
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.producer")
    static class Producer {
        static void main() {
            SpringApplication.run(
//                    Producer.class, "--section=sec03", "--config=02-producer" // config for simple string producer
                    Producer.class, "--section=sec03", "--config=04-message-producer" // config for kafka message producer
            );
        }
    }
}