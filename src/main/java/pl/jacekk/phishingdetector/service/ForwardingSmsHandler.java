package pl.jacekk.phishingdetector.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Component;
import pl.jacekk.phishingdetector.model.SmsMessage;

@Slf4j
@Component
@AllArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ForwardingSmsHandler implements SmsHandler {

    @Getter
    private SmsHandler next;
    private final PulsarTemplate<SmsMessage> template;
    @Override
    public void setNext(SmsHandler smsHandler) {
        next = smsHandler;
    }

    @Override
    public void handle(SmsMessage sms) throws PulsarClientException {
        log.info("Forwarding SMS: {} to the sms-source-filtered topic...", sms);
        template.send("persistent://public/phishing-detection/sms-source-filtered", sms);
        if (next != null) next.handle(sms);
    }
}
