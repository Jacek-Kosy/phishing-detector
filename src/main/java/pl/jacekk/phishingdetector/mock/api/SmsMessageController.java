package pl.jacekk.phishingdetector.mock.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jacekk.phishingdetector.model.SmsMessage;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix}/sms")
public class SmsMessageController {
    private final PulsarTemplate<SmsMessage> template;

    @PostMapping("/post")
    public void postSmsMessage(@RequestBody SmsMessage smsMessage) throws PulsarClientException {
        log.info("Posting a new message to the sms topic...");
        template.send("persistent://public/phishing-detection/sms-source", smsMessage);
    }

}
