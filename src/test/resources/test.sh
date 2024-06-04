~/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic TutorialTopic
echo "Hello, World" | ~/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic TutorialTopic > /dev/null
~/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic TutorialTopic --from-beginning

./bin/kafka-topics.sh --create --topic test-topic --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092 --replication-factor 3 --partitions 64
./bin/kafka-topics.sh --topic test-topic --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092 --describe

./bin/kafka-console-producer.sh --topic test-topic --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092
./bin/kafka-console-consumer.sh --topic test-topic --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092 --from-beginning


./bin/kafka-topics.sh --create --topic sms-source
 \ --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092
 \ --replication-factor 3 --partitions 3
./bin/kafka-topics.sh --create --topic sms-source-filtered
 \ --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092
 \ --replication-factor 3 --partitions 3
./bin/kafka-topics.sh --create --topic sms-source-rejected
 \ --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092
 \ --replication-factor 3 --partitions 3

sudo docker run -e KAFKA_BOOTSTRAP_SERVERS=172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092 -p 8080:8080 phishing-detector