package pl.jacekk.phishingdetector.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.stereotype.Component;
import pl.jacekk.phishingdetector.model.SmsMessage;

@Component
public class EmbeddedKafkaConfig {
    @Bean
    EmbeddedKafkaBroker broker() {
        return new EmbeddedKafkaBroker(1)
                .kafkaPorts(9092)
                .brokerListProperty("spring.kafka.bootstrap-servers");
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("sms").partitions(1).replicas(1).build();
    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, SmsMessage> template) {
        return args -> template.send("sms", new SmsMessage("48797234594", "48797234594", "hello"));
    }
}
