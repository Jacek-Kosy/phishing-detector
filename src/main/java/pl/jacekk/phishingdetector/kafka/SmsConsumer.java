package pl.jacekk.phishingdetector.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.jacekk.phishingdetector.model.SmsMessage;
import pl.jacekk.phishingdetector.service.RegistrationSmsHandler;
import pl.jacekk.phishingdetector.service.SmsVerificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsConsumer {
    private final SmsVerificationService smsVerificationService;


    @KafkaListener(topics = "sms-source", groupId = "phishing-detector")
    public void listen(SmsMessage message) {
        smsVerificationService.verifySms(message);
    }
}
