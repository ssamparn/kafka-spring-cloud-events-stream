## Building a Reactive Processor

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.
Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml down
$ docker-compose -f single-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.

---

### 1: 1 → 1 Mapping

Start the **Consumer** application.

```java
SpringApplication.run(
        Consumer.class, "--section=sec08", "--config=01-consumer"
);
```

Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class, "--section=sec08", "--config=03-payment-processor"
);
```

Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec08", "--config=02-producer"
);
```

**Observe:**

* For each order event received, the processor emits a payment event.
* The consumer consumes the payment events.

---

### 2: 1 → 0/1 Mapping (Filter)

Start the **Consumer** application.

```java
SpringApplication.run(
        Consumer.class, "--section=sec08", "--config=01-consumer"
);
```

Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class, "--section=sec08", "--config=04-shipment-processor"
);
```

Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec08", "--config=02-producer"
);
```

**Observe:**

* The processor emits a shipment event only for physical orders.
* Digital orders are filtered out.

---

### 3: 1 → N Mapping

Start the **Consumer** application.

```java
SpringApplication.run(
        Consumer.class, "--section=sec08", "--config=01-consumer"
);
```

Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class, "--section=sec08", "--config=05-notification-processor"
);
```

Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec08", "--config=02-producer"
);
```

**Observe:**

* For every order event, the processor emits two notification events.