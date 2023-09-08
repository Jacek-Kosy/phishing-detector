package pl.jacekk.phishingdetector.service;

import pl.jacekk.phishingdetector.model.SmsMessage;

public interface SmsHandler {
    void setNext(SmsHandler smsHandler);

    void handle(SmsMessage sms);
}
