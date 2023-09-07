package pl.jacekk.phishingdetector.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.jacekk.phishingdetector.entity.ContractEntity;
import pl.jacekk.phishingdetector.model.SmsMessage;
import pl.jacekk.phishingdetector.repository.ContractRepository;

import java.time.LocalDateTime;

@Slf4j
@Component
@AllArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationSmsHandler implements SmsHandler {
    private final ContractRepository repository;
    @Getter
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
        var sender = sms.sender();
        if (sms.recipient().equals(registrationNumber)) {
            if (sms.message().equals(registerMessage)) register(sender);
            else if (sms.message().equals(unregisterMessage)) unregister(sender);
            else log.info("Invalid registration message from MSISDN: {}!", sender);
        } else if (next != null) next.handle(sms);
    }

    protected void register(String msisdn) {
        var contract = repository.findByMsisdn(msisdn);
        contract.ifPresentOrElse(contractEntity -> {
            var serviceStatus = contractEntity.getHasActiveService();
            if (!serviceStatus) activateService(contractEntity);
            else log.info("The service is already active for MSISDN: {}", msisdn);
        }, () -> repository.save(new ContractEntity(msisdn, true)));
    }

    protected void unregister(String msisdn) {
        var contract = repository.findByMsisdn(msisdn);
        contract.ifPresentOrElse(contractEntity -> {
            var serviceStatus = contractEntity.getHasActiveService();
            if (serviceStatus) deactivateService(contractEntity);
            else log.info("The service is already deactivated for MSISDN: {}", msisdn);
        }, () -> log.info("MSISDN: {} has never been registered for the service", msisdn));
    }


    protected void activateService(ContractEntity contractEntity) {
        contractEntity.setHasActiveService(true);
        contractEntity.setLastUpdated(LocalDateTime.now());
        repository.save(contractEntity);
        log.info("MSISDN: {} is now registered for the service", contractEntity.getMsisdn());
    }

    protected void deactivateService(ContractEntity contractEntity) {
        contractEntity.setHasActiveService(false);
        contractEntity.setLastUpdated(LocalDateTime.now());
        repository.save(contractEntity);
        log.info("MSISDN: {} is no longer registered for the service", contractEntity.getMsisdn());
    }
}
