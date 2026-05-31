## Kafka Fault Tolerance in Action

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

#### 1: Create a topic with **1 partition** and **3 replicas**.

```bash
$ ./kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic \
  --create \
  --partitions 1 \
  --replication-factor 3
```

#### 2: Start the **Consumer** application.

```java
SpringApplication.run(
        Consumer.class, "--section=sec13", "--config=01-simple-consumer"
);
```

#### 3: Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec13", "--config=02-simple-producer"
);
```

**Observe:**

* Once the producer starts producing messages, ensure that the consumer is consuming them.
* Now bring the Kafka containers (`kafka1` or `kafka2` or `kafka3`) up and down one by one & observe the behavior.
* You will notice that the **application continues to work without interruption**, demonstrating Kafka’s fault tolerance.