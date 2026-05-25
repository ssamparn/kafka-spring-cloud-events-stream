## Building a Consumer

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

## 1: Simple Consumer

#### Open a terminal and check that the topic `demo-topic` does not exist yet.

```bash
$ docker exec -it kafka bash

$ ./kafka-topics.sh --bootstrap-server localhost:9092 --list
```

#### Start the `SectionRunner` with below config

```java
SpringApplication.run(
    SectionRunner.class,
    "--section=sec01",
    "--config=01-simple-consumer"
);
```

#### What to observe?

* Spring automatically connects to `localhost:9092`.
* An **anonymous consumer group** is used since no group name is configured.
* `demo-topic` is auto-created.

> To disable auto-topic creation, set `auto.create.topics.enable=false` in `server.properties` of the Kafka server.

#### Produce messages using the console producer
```bash
$ ./kafka-console-producer.sh \
   --bootstrap-server localhost:9092 \
   --topic demo-topic \
   --timeout 0
```

> `--timeout 0` ensures messages are sent immediately without buffering.


## 2: Consume From Beginning

#### Start the `SectionRunner` with below config

```java
SpringApplication.run(
    SectionRunner.class,
    "--section=sec01",
    "--config=02-from-beginning"
);
```

## 3: Consumer Group Name

1. Start the `SectionRunner` with below config.

```java
SpringApplication.run(
    SectionRunner.class,
    "--section=sec01",
    "--config=03-consumer-group"
);
```

## 4: Consuming Multiple Topics

#### Start the `SectionRunner` with below config.

```java
SpringApplication.run(
    SectionRunner.class,
    "--section=sec01",
    "--config=04-multiple-topics"
);
```

#### Open **two separate terminals** and produce messages to two topics

```bash
$ ./kafka-console-producer.sh \
   --bootstrap-server localhost:9092 \
   --topic demo-topic-1 \
   --timeout 0

$ ./kafka-console-producer.sh \
   --bootstrap-server localhost:9092 \
   --topic demo-topic-2 \
   --timeout 0
```