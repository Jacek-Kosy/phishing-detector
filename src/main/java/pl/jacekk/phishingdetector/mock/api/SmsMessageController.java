package pl.jacekk.phishingdetector.mock.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, SmsMessage> template;

    @PostMapping("/post")
    public void postSmsMessage(@RequestBody SmsMessage smsMessage) {
        log.info("Posting a new message to the sms topic...");
        template.send("sms-source", smsMessage.sender(), smsMessage);
    }

}
