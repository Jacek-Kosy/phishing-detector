package pl.jacekk.phishingdetector.service;

import org.apache.pulsar.client.api.PulsarClientException;
import pl.jacekk.phishingdetector.model.SmsMessage;

public interface SmsHandler {
    void setNext(SmsHandler smsHandler);

    void handle(SmsMessage sms) throws PulsarClientException;
}
