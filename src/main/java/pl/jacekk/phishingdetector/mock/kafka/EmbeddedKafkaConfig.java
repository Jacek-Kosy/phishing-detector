package pl.jacekk.phishingdetector.mock.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.stereotype.Component;

@Component
public class EmbeddedKafkaConfig {
    @Bean
    EmbeddedKafkaBroker broker() {
        return new EmbeddedKafkaBroker(1)
                .kafkaPorts(9092)
                .brokerListProperty("spring.kafka.bootstrap-servers");
    }

    @Bean
    public NewTopic sourceTopic() {
        return TopicBuilder.name("sms-source").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic sinkTopic() {
        return TopicBuilder.name("filtered-sms-sink").partitions(1).replicas(1).build();
    }

}
