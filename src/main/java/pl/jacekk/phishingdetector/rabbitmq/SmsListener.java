package pl.jacekk.phishingdetector.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pl.jacekk.phishingdetector.model.SmsMessage;
import pl.jacekk.phishingdetector.service.SmsVerificationService;

@Service
@RequiredArgsConstructor
public class SmsListener {
    private final SmsVerificationService smsVerificationService;

    @RabbitListener(queues = {"q.sms-source"})
    public void listen(SmsMessage message) {
        smsVerificationService.verifySms(message);
    }
}
