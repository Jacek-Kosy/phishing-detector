package pl.jacekk.phishingdetector.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.jacekk.phishingdetector.model.SmsMessage;

@Slf4j
@Component
public class SmsConsumer {

    @KafkaListener(topics = "sms", groupId = "phishing-detector")
    public void listen(SmsMessage message) {
        log.info(message.toString());
    }
}
