## Concurrent Message Processing with Spring Cloud Stream (Application Level Concurrency)

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.
Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml down
$ docker-compose -f single-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.

#### Exec into the Kafka container

```bash
$ docker exec -it kafka bash
```

## 1: Unordered Concurrent Message Processing

#### 1: Start the **Digital Delivery Consumer** application.

```java
SpringApplication.run(
        DigitalDeliveryConsumer.class, "--section=sec16", "--config=01-digital-consumer"
);
```

#### 2: Start the **Physical Delivery Consumer** application.

```java
SpringApplication.run(
        PhysicalDeliveryConsumer.class, "--section=sec16", "--config=02-physical-consumer"
);
```

#### 3: Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class, "--section=sec16", "--config=03-processor", "--processing.mode=unordered"
);
```

#### 4: Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec16", "--config=04-producer"
);
```

**Observe:**

* The producer publishes one order event every **10 ms**.
* The processor builds a delivery object for each order, which takes **200 ms per event**.
* The processor consumes records **in batch mode**, and each record is processed independently using **virtual threads**, allowing it to keep up with the producer.
* Events are routed to different consumers based on the **product type**.

---

##  2: Ordered Concurrent Message Processing

In this mode, the processor groups records based on a **custom key**.
Each group is processed **concurrently**, while items **within a group** are processed **sequentially** to preserve ordering.

Start the **Processor** application in **ordered mode**.

```java
SpringApplication.run(
        Processor.class, "--section=sec16", "--config=03-processor", "--processing.mode=ordered"
);
```