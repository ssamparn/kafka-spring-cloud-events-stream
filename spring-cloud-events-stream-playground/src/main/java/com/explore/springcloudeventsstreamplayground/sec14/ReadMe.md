## Batch Processing

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.
Navigate to the path where the **multi-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/multi-node-cluster/
$ docker-compose -f multi-node-kafka-cluster.yml down
$ docker-compose -f multi-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.

#### Exec into the Kafka container

```bash
$ docker exec -it kafka1 bash
$ docker exec -it kafka2 bash
$ docker exec -it kafka3 bash
```

#### 1: Start the **Consumer** application.

* The consumer is configured to **consume messages in batch mode**.
* The application exposes a bean that consumes `List<T>`.

```java
SpringApplication.run(
        Consumer.class, "--section=sec14", "--config=01-consumer"
);
```

#### 2: Start the **Producer** application.

* The producer is configured to **buffer messages and send them in batches**.

```java
SpringApplication.run(
        Producer.class, "--section=sec14", "--config=02-producer"
);
```

**Observe:**

* The producer sends **1 million messages**.
* The consumer processes **up to 1,000 messages in a single poll**.