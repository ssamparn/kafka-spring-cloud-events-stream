package com.explore.springcloudeventsstreamplayground.sec16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class SectionRunner {

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class DigitalDeliveryConsumer {
        static void main() {
            SpringApplication.run(
                    DigitalDeliveryConsumer.class, "--section=sec16", "--config=01-digital-consumer"
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class PhysicalDeliveryConsumer {
        static void main() {
            SpringApplication.run(
                    PhysicalDeliveryConsumer.class, "--section=sec16", "--config=02-physical-consumer"
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.processor")
    static class Processor {
        static void main() {
            SpringApplication.run(
                    Processor.class, "--section=sec16", "--config=03-processor", "--processing.mode=ordered"
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.producer")
    static class Producer {
        static void main() {
            SpringApplication.run(
                    Producer.class, "--section=sec16", "--config=04-producer"
            );
        }
    }
}
