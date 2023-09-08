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
    private final ForwardingSmsHandler forwardingSmsHandler;

    @PostConstruct
    private void init() {
        registrationSmsHandler.setNext(urlVerificationSmsHandler);
        urlVerificationSmsHandler.setNext(forwardingSmsHandler);
    }

    public void verifySms(SmsMessage message) {
        registrationSmsHandler.handle(message);
    }
}
