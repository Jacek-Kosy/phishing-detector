package pl.jacekk.phishingdetector.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jacekk.phishingdetector.repository.ContractRepository;

import static org.junit.jupiter.api.Assertions.*;

class URLVerificationSmsHandlerTest {
    private final ContractRepository contractRepository = Mockito.mock(ContractRepository.class);
    private final URLVerificationSmsHandler urlVerificationSmsHandler = new URLVerificationSmsHandler(contractRepository);

    @Test
    void findURL() {
        var message = """
                Ala ma kota
                www.wp.pl
                Kot ma Alę
                https://wikipedia.org
                Matteo:http://localhost:8080
                """;
        var result = urlVerificationSmsHandler.findURL(message);
        System.out.println(result);
        assertEquals("www.wp.pl", result);
    }
}