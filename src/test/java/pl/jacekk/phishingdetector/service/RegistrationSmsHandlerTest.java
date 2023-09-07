package pl.jacekk.phishingdetector.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import pl.jacekk.phishingdetector.entity.ContractEntity;
import pl.jacekk.phishingdetector.model.SmsMessage;
import pl.jacekk.phishingdetector.repository.ContractRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationSmsHandlerTest {
    private final ContractRepository repository = Mockito.mock(ContractRepository.class);
    private final RegistrationSmsHandler registrationSmsHandler = new RegistrationSmsHandler(repository, null,
            "1234", "START", "STOP");

    @Test
    void shouldSetNextHandler() {
        var handler = Mockito.mock(SmsHandler.class);
        var registrationHandler = new RegistrationSmsHandler(repository);
        registrationHandler.setNext(handler);
        assertNotNull(registrationHandler.getNext());
    }

    @Test
    void shouldPassToTheNextHandlerIfTheRecipientIsNotRegistrationNumber() {
        var nextHandler = Mockito.mock(SmsHandler.class);
        registrationSmsHandler.setNext(nextHandler);
        var sms = new SmsMessage("48123456789", "2345", "START");
        registrationSmsHandler.handle(sms);
        Mockito.verify(nextHandler).handle(sms);
    }

    @Test
    void shouldRegisterIfServiceStatusIsNull() {
        var msisdn = "48123456789";
        var contract = new ContractEntity(msisdn, null);
        Mockito.when(repository.findByMsisdn("48123456789")).thenReturn(Optional.of(contract));
        registrationSmsHandler.register(msisdn);
        var argumentCaptor = ArgumentCaptor.forClass(ContractEntity.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
        assertEquals("48123456789", argumentCaptor.getValue().getMsisdn());
        assertTrue(argumentCaptor.getValue().getHasActiveService());
    }

    @Test
    void shouldRegisterIfServiceStatusIsFalse() {
        var msisdn = "48123456789";
        var contract = new ContractEntity(msisdn, false);
        Mockito.when(repository.findByMsisdn("48123456789")).thenReturn(Optional.of(contract));
        registrationSmsHandler.register(msisdn);
        var argumentCaptor = ArgumentCaptor.forClass(ContractEntity.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
        assertEquals("48123456789", argumentCaptor.getValue().getMsisdn());
        assertTrue(argumentCaptor.getValue().getHasActiveService());
    }

    @Test
    void shouldNotRegisterIfServiceStatusIsTrue() {
        var msisdn = "48123456789";
        var contract = new ContractEntity(msisdn, true);
        Mockito.when(repository.findByMsisdn("48123456789")).thenReturn(Optional.of(contract));
        registrationSmsHandler.register(msisdn);
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldUnregisterIfServiceStatusIsTrue() {
        var msisdn = "48123456789";
        var contract = new ContractEntity(msisdn, true);
        Mockito.when(repository.findByMsisdn("48123456789")).thenReturn(Optional.of(contract));
        registrationSmsHandler.unregister(msisdn);
        var argumentCaptor = ArgumentCaptor.forClass(ContractEntity.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
        assertEquals("48123456789", argumentCaptor.getValue().getMsisdn());
        assertFalse(argumentCaptor.getValue().getHasActiveService());
    }

    @Test
    void shouldNotUnregisterIfServiceStatusIsNull() {
        var msisdn = "48123456789";
        var contract = new ContractEntity(msisdn, null);
        Mockito.when(repository.findByMsisdn("48123456789")).thenReturn(Optional.of(contract));
        registrationSmsHandler.unregister(msisdn);
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldNotUnregisterIfServiceStatusIsFalse() {
        var msisdn = "48123456789";
        var contract = new ContractEntity(msisdn, false);
        Mockito.when(repository.findByMsisdn("48123456789")).thenReturn(Optional.of(contract));
        registrationSmsHandler.unregister(msisdn);
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldActivateService() {
        var contract = new ContractEntity("48123456789", false);
        registrationSmsHandler.activateService(contract);
        var argumentCaptor = ArgumentCaptor.forClass(ContractEntity.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
        assertTrue(argumentCaptor.getValue().getHasActiveService());
    }

    @Test
    void shouldDeactivateService() {
        var contract = new ContractEntity("48123456789", true);
        registrationSmsHandler.deactivateService(contract);
        var argumentCaptor = ArgumentCaptor.forClass(ContractEntity.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
        assertFalse(argumentCaptor.getValue().getHasActiveService());
    }
}