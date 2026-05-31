## Running Single Node Kafka Cluster using Docker Compose

Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml up
```
Wait until the Kafka container is fully up and running.

- You can use `-d` option to run in the detached mode.

## Accessing the Kafka container

#### Exec into the Kafka container

```bash
$ docker exec -it kafka bash
```

#### You will land in directory.

```
/opt/kafka/bin
```

### Kafka CLI tools

- List the available command-line tools

```bash
$ ls -l
```

#### You will see Kafka CLI scripts such as

> `kafka-topics.sh`
> `kafka-console-producer.sh`
> `kafka-console-consumer.sh`

### Important note about PATH

Since the directory `/opt/kafka/bin` is **not part of the `PATH` environment variable**. So, you must invoke tools/scripts using `./`:

```bash
$ ./kafka-topics.sh
```

### Kafka configuration files

- Kafka configuration files are located at

```
/opt/kafka/config
```

- Key files you may notice:

* `server.properties`
* `broker.properties`
* `controller.properties`
* `producer.properties`
* `consumer.properties`

### Official configuration reference

Please refer to the [official documentation](https://kafka.apache.org/42/configuration/)