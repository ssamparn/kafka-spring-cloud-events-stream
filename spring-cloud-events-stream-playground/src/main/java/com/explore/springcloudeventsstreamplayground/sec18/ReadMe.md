## Manual Acknowledgement

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.
Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml down
$ docker-compose -f single-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.

## 1: Retry with Fixed Delay

### Start the Consumer
```java
@SpringBootApplication
public class SectionRunner {

   static void main() {
      SpringApplication.run(SectionRunner.class, "--section=sec18", "--config=01-retry-fixed-delay");
   }
}
```

### Open two terminals

### Terminal 1 – Console Producer

```bash
$ docker exec -it kafka bash

$ ./kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --timeout 0
```

### Terminal 2 – Consumer Group Status

```bash
$ ./kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --describe \
    --group demo-group
```

### Demo & Observe

1. **Produce messages:** `1, 2, 3, 4`

   * Application processes messages successfully
   * Messages are acknowledged
   * Offsets are committed
   * **LAG = 0**

2. **Produce message:** `0`

   * Application throws an exception
   * Spring Cloud Stream retries the message (based on `consumer.maxAttempts`)
   * Observe **delivery attempt** header on each retry
   * Offset is **not committed** during retries
   * After retries are exhausted:

      * Exception is logged
      * Offset is committed

3. **Produce messages again:** `1, 2, 3, 4`

   * Normal processing resumes
   * Offsets are committed


## 2: Retry with Exponential Delay

### Start the Consumer

```java
@SpringBootApplication
public class SectionRunner {

   static void main() {
      SpringApplication.run(SectionRunner.class, "--section=sec18", "--config=02-retry-exponential-delay");
   }
}
```

### Open Console Producer

```bash
$ docker exec -it kafka bash

$ ./kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --timeout 0
```

### Demo & Observe

1. **Produce messages:** `1, 2, 3, 4`

   * Successful processing
   * Offsets committed
   * **LAG = 0**

2. **Produce message:** `0`

   * Application throws an exception
   * Message is retried
   * Retry delays increase exponentially based on:

     ```
     consumer.back-off-multiplier
     ```
   * Observe increasing time gaps between retries

## 3: Retryable vs Non-Retryable Exceptions

### Start the Consumer

```java
@SpringBootApplication
public class SectionRunner {

   static void main() {
      SpringApplication.run(SectionRunner.class, "--section=sec18", "--config=03-retryable-exceptions");
   }
}
```

### Open Console Producer

```bash
$ docker exec -it kafka bash

$ ./kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --timeout 0
```

### Demo & Observe

1. **Produce messages:** `1, 2, 3, 4`

   * Successful processing
   * Offsets committed

2. **Produce message:** `0`

   * `InputValidationException` is thrown
   * Message is **not retried**

3. **Produce messages:** `6, 7, 8`

   * `ServiceUnavailableException` is thrown
   * Message **is retried** based on retry configuration

## 4: Dead Letter Queue (DLQ)

### Start the Consumer

```java
@SpringBootApplication
public class SectionRunner {

   static void main() {
      SpringApplication.run(SectionRunner.class, "--section=sec18", "--config=04-dlq");
   }
}
```

### Open Console Producer

```bash
$ docker exec -it kafka bash

$ ./kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --timeout 0
```

### Open DLQ Consumer (New Terminal)

```bash
$ ./kafka-console-consumer.sh \
   --bootstrap-server localhost:9092 \
   --topic demo-topic-dlq \
   --property print.headers=true \
   --property print.key=true \
   --property print.value=true
```

### Demo & Observe

1. **Produce messages:** `1, 2, 3, 4`

   * Successful processing
   * Offsets committed
   * **LAG = 0**

2. **Produce message:** `0`

   * `InputValidationException` occurs
   * No retries
   * Message is sent to the **DLQ**
   * Observe:

      * Original payload
      * Exception details in headers

3. **Produce messages:** `6, 7, 8`

   * `ServiceUnavailableException` occurs
   * Retries are attempted
   * After retries are exhausted:

      * Message is sent to the **DLQ**
      * Headers contain retry and failure metadata
