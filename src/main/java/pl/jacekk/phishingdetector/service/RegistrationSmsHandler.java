package pl.jacekk.phishingdetector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.jacekk.phishingdetector.entity.ContractEntity;
import pl.jacekk.phishingdetector.model.SmsMessage;
import pl.jacekk.phishingdetector.repository.ContractRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationSmsHandler implements SmsHandler {
    private final ContractRepository repository;
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
            if (sms.message().equals(registerMessage)) register(sms.sender());
            else if (sms.message().equals(unregisterMessage)) unregister(sms.sender());
            else log.warn("Invalid message!");
        }
        log.info("Passing the message to the next handler...");
        if (next != null) next.handle(sms);
    }

    protected void register(String msisdn) {
        var contract = repository.findByMsisdn(msisdn);
        contract.ifPresentOrElse(contractEntity -> {
            var serviceStatus = contractEntity.getHasActiveService();
            if (serviceStatus == null || !serviceStatus) activateService(contractEntity);
            else log.info("The service is already active for MSISDN: {}", msisdn);
        }, () -> log.info("MSISDN: {} is not from our network", msisdn));
    }

    protected void unregister(String msisdn) {
        var contract = repository.findByMsisdn(msisdn);
        contract.ifPresentOrElse(contractEntity -> {
            var serviceStatus = contractEntity.getHasActiveService();
            if (serviceStatus) deactivateService(contractEntity);
            else log.info("The service is already deactivated for MSISDN: {}", msisdn);
        }, () -> log.info("MSISDN: {} is not from our network", msisdn));
    }


    protected void activateService(ContractEntity contractEntity) {
        contractEntity.setHasActiveService(true);
        repository.save(contractEntity);
        log.info("MSISDN: {} is now registered for the service", contractEntity.getMsisdn());
    }

    protected void deactivateService(ContractEntity contractEntity) {
        contractEntity.setHasActiveService(false);
        repository.save(contractEntity);
        log.info("MSISDN: {} is no longer registered for the service", contractEntity.getMsisdn());
    }
}
