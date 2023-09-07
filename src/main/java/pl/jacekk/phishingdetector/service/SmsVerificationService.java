package pl.jacekk.phishingdetector.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jacekk.phishingdetector.model.SmsMessage;

@Component
@RequiredArgsConstructor
public class SmsVerificationService {
    private final RegistrationSmsHandler registrationSmsHandler;
    private final URLVerificationSmsHandler urlVerificationSmsHandler;

    @PostConstruct
    private void init() {
        registrationSmsHandler.setNext(urlVerificationSmsHandler);
    }

    public void verifySms(SmsMessage message) {
        registrationSmsHandler.handle(message);
    }
}
