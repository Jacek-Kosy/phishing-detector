package pl.jacekk.phishingdetector.mock.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private final RabbitTemplate template;

    @PostMapping("/post")
    public void postSmsMessage(@RequestBody SmsMessage smsMessage) {
        log.info("Posting a new message to the sms topic...");
        template.convertAndSend("", "q.sms-source", smsMessage);
    }

}
