## Building a Producer

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.
Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml down
$ docker-compose -f single-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.

### Access the container
```bash
$ docker exec -it kafka bash
```
#### Ensure that you are in the `/opt/kafka/bin` directory.

---

### 1: Simple Producer

1. Start the `SectionRunner.Consumer` with below config.

```java
SpringApplication.run(
        Consumer.class, "--section=sec03", "--config=01-consumer"
);
```

2. Start the `SectionRunner.Producer` with below config.

```java
SpringApplication.run(
        Producer.class, "--section=sec03", "--config=02-producer"
);
```

#### Observe
- The producer publishes messages periodically, based on `spring.cloud.stream.poller.fixed-delay`.
- The consumer receives those messages.

---

### 2: Producing & Consuming Messages with Keys

1. Access the kafka container and enter these commands.

```bash
$ ./kafka-topics.sh \
	--bootstrap-server localhost:9092 \
	--topic demo-topic \
	--delete

$ ./kafka-topics.sh \
	--bootstrap-server localhost:9092 \
	--topic demo-topic \
	--create \
	--partitions 2
```

2. Start the `SectionRunner.Consumer` with below config.

```java
SpringApplication.run(
        Consumer.class, "--section=sec03", "--config=03-message-consumer"
);
```

4. Start the `SectionRunner.Producer` with below config.

```java
SpringApplication.run(
        Producer.class, "--section=sec03", "--config=04-message-producer"
);
```

#### Observe
- The consumer receives messages with Kafka keys
- Custom message headers are also available