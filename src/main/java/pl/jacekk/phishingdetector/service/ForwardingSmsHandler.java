package pl.jacekk.phishingdetector.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pl.jacekk.phishingdetector.model.SmsMessage;

@Slf4j
@Component
@AllArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ForwardingSmsHandler implements SmsHandler {

    @Getter
    private SmsHandler next;
    private final KafkaTemplate<String, SmsMessage> template;
    @Override
    public void setNext(SmsHandler smsHandler) {
        next = smsHandler;
    }

    @Override
    public void handle(SmsMessage sms) {
        log.info("Forwarding SMS: {} to the filtered-sms-sink topic...", sms);
        template.send("filtered-sms-sink", sms.recipient(), sms);
        if (next != null) next.handle(sms);
    }
}
