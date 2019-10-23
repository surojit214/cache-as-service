# cache-as-service

Download Kafka, unzip and place it in C:\kafka_2.12-2.3.0 say:
1. Change zookeeper.properties in config folder:
    dataDir=C:/kafka_2.12-2.3.0/data/zookeeper(create data\zookeeper directory first)
2. Change server.properties in config folder:
    log.dirs=C:/kafka_2.12-2.3.0/data/kafka(create data\kafka directory first)

Add path to environment variable: C:\kafka_2.12-2.3.0\bin\windows

Start Zookeeper:
cd C:\kafka_2.12-2.3.0
zookeeper-server-start config\zookeeper.properties

Start Kafka:
cd C:\kafka_2.12-2.3.0
kafka-server-start config\server.properties

Create a kafka topic:
kafka-topics --create --bootstrap-server localhost:9092 --topic cache_test --partitions 3 --replication-factor 1

Create 3 spring boot servers:
1. CacheApplicationLeader:
    Program arg: --server.leader=true --server.port=8080 --spring.kafka.consumer.group-id=ct1
2. CacheApplicationSlave1:
    Program arg: --server.leader=false --server.port=8081 --spring.kafka.consumer.group-id=ct2
3. CacheApplicationSlave2:
    Program arg: --server.leader=false --server.port=8082 --spring.kafka.consumer.group-id=ct3
    
Start all 3 servers (1 Master, 2 Slaves: all writes through Master only, Read from any server)

Send a request through Postman of following format to Master running in port 8080:
POST: localhost:8080/service/cache
Body(raw:JSON): 
{
	"Key": "1234",
	"Value": "{name: Sam, age: 36}"
}

Send a GET request to any of the server:
localhost:8080/service/cache/1234 or localhost:8081/service/cache/1234 or localhost:8082/service/cache/1234
