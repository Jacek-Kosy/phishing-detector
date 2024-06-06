~/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic TutorialTopic
echo "Hello, World" | ~/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic TutorialTopic > /dev/null
~/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic TutorialTopic --from-beginning

./bin/kafka-topics.sh --create --topic test-topic --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092 --replication-factor 3 --partitions 64
./bin/kafka-topics.sh --topic test-topic --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092 --describe

./bin/kafka-console-producer.sh --topic test-topic --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092
./bin/kafka-console-consumer.sh --topic test-topic --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092 --from-beginning


./bin/kafka-topics.sh --create --topic sms-source --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092 --replication-factor 3 --partitions 3
./bin/kafka-topics.sh --create --topic sms-source-filtered
 \ --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092
 \ --replication-factor 3 --partitions 3
./bin/kafka-topics.sh --create --topic sms-source-rejected
 \ --bootstrap-server 172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092
 \ --replication-factor 3 --partitions 3

sudo docker run -e KAFKA_BOOTSTRAP_SERVERS=172.31.45.10:9092,172.31.44.7:9092,172.31.37.181:9092 -p 8080:8080 phishing-detector
sudo docker run -e RABBITMQ_PASSWORD=denaturat123 -e SPRING_AMQP_DESERIALIZATION_TRUST_ALL=true -p 8080:8080 phishing-detector

server.1=0.0.0.0:2888:3888
server.2=172.31.33.28:2888:3888
server.3=172.31.37.110:2888:3888

server.1=172.31.32.59:2888:3888
server.2=0.0.0.0:2888:3888
server.3=172.31.37.110:2888:3888

server.1=172.31.32.59:2888:3888
server.2=172.31.33.28:2888:3888
server.3=0.0.0.0:2888:3888

bin/pulsar-client --url pulsar+ssl://pc-a6ad1e00.aws-euc1-prod-snci-pool-slug.streamnative.aws.snio.cloud:6651 --auth-plugin org.apache.pulsar.client.impl.auth.AuthenticationToken --auth-params token:eyJhbGciOiJSUzI1NiIsImtpZCI6ImY1N2M5MDlmLTk5OTMtNTA2OC04ODI5LTllODAwOTkxNDEwOSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidXJuOnNuOnB1bHNhcjpvLTVwOWFyOnB1bHNhci1pbnN0YW5jZS0wMSJdLCJleHAiOjE3MjAyOTYwMjgsImh0dHBzOi8vc3RyZWFtbmF0aXZlLmlvL3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50QG8tNXA5YXIuYXV0aC5zdHJlYW1uYXRpdmUuY2xvdWQiLCJpYXQiOjE3MTc3MDQwMjksImlzcyI6Imh0dHBzOi8vcGMtYTZhZDFlMDAuYXdzLWV1YzEtcHJvZC1zbmNpLXBvb2wtc2x1Zy5zdHJlYW1uYXRpdmUuYXdzLnNuaW8uY2xvdWQvYXBpa2V5cy8iLCJqdGkiOiJhY2YwN2Q5NGMzMmQ0ZDcwOWMwZTBmMjRmMGE0NTBiNCIsInBlcm1pc3Npb25zIjpbImFkbWluIiwiYWNjZXNzIl0sInNjb3BlIjoiYWRtaW4gYWNjZXNzIiwic3ViIjoiVXJsM0VVT05NNWF5cDVYclBPY1FDV29JWnRLM1h0SW9AY2xpZW50cyJ9.aRk3lZjtC7UJHVu3iKwoC_Qud63NZYm2q9o29vsWjPUlldq2vk1Qu4gJz075D2HLL33UPOFZmzUVHwWYLNipexm5QXOswxjNIYHAzyB0hCMMUYVY3yhYKK9_ITc79PayJzIP1BuNUS045-24PvgWj9sY4X3s2jtuLB4mC7N04TpRsO2SFFIs1PcbBJuHjB584cb9fx07gJFkR4_L6xstJcvZuKk2yTOAVVhC0zTAXF0FyudaWMpcQgOkIIYzm9jwoxvSNMUpLScS4Fd32m3MyHAikM1YTsMqe3uhGbc9EF2GfeXO5kdc0xiZrwXN6OE40wxBNswHe-2FICM3txwadg produce public/phishing-detection/sms-source -m "{\"sender\":\"48532771711\",\"recipient\":\"48797234595\",\"message\":\"hello, http://sciagnij-wirusa.pl\"}" -s "\n"
