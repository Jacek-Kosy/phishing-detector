package pl.jacekk.phishingdetector.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.jacekk.phishingdetector.model.SmsMessage;

@Slf4j
@Component
public class RegistrationSmsHandler implements SmsHandler {
    private SmsHandler next;

    @Value("${registration.phone-number}")
    private String registrationNumber;
    @Value("${registration.register-message}")
    private String registerMessage;
    @Value("${registration.unregister-message}")
    private String unregisterMessage;


    @Override
    public void setNext(SmsHandler smsHandler) {
        next = smsHandler;
    }

    @Override
    public void handle(SmsMessage sms) {
        if (sms.recipient().equals(registrationNumber)) {
            if (sms.message().equals(registerMessage)) log.info("{} is now registered for the service", sms.sender());
            else if (sms.message().equals(unregisterMessage)) log.info("{} is no longer registered for the service", sms.sender());
            else log.warn("Invalid message!");
        }
        log.info("Passing the message to the next handler...");
        if (next != null) next.handle(sms);
    }
}
