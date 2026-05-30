package com.explore.springcloudeventsstreamplayground.sec12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

public class SectionRunner {

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class DigitalDeliveryConsumer {
        static void main() {
            SpringApplication.run(
                    DigitalDeliveryConsumer.class, "--section=sec12", "--config=01-digital-consumer"
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class FedExDeliveryConsumer {
        static void main() {
            SpringApplication.run(
                    FedExDeliveryConsumer.class, "--section=sec12", "--config=02-fedex-consumer"
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class UspsDeliveryConsumer {
        static void main() {
            SpringApplication.run(
                    UspsDeliveryConsumer.class, "--section=sec12", "--config=03-usps-consumer"
            );
        }
    }

    @EnableScheduling
    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.processor")
    static class Processor {
        static void main() {
            SpringApplication.run(
                    Processor.class, "--section=sec12", "--config=04-processor"
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.producer")
    static class Producer {
        static void main() {
            SpringApplication.run(
                    Producer.class, "--section=sec12", "--config=05-producer"
            );
        }
    }
}
