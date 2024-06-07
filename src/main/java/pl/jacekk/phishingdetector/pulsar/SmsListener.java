package pl.jacekk.phishingdetector.pulsar;

import lombok.RequiredArgsConstructor;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;
import pl.jacekk.phishingdetector.model.SmsMessage;
import pl.jacekk.phishingdetector.service.SmsVerificationService;

@Service
@RequiredArgsConstructor
public class SmsListener {
    private final SmsVerificationService smsVerificationService;

    @PulsarListener(subscriptionName = "phishing-detector", subscriptionType = SubscriptionType.Shared,
            topics = "persistent://public/phishing-detection/sms-source")
    public void listen(SmsMessage message) throws PulsarClientException {
        smsVerificationService.verifySms(message);
    }
}
