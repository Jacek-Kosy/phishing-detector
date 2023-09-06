package pl.jacekk.phishingdetector.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jacekk.phishingdetector.repository.ContractRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class URLVerificationSmsHandlerTest {
    private final ContractRepository contractRepository = Mockito.mock(ContractRepository.class);
    private final URLVerificationSmsHandler urlVerificationSmsHandler = new URLVerificationSmsHandler(contractRepository);

    @Test
    void shouldReturnAllMatchingURLs() {
        var message = """
                Ala ma kota
                www.wp.pl
                Kot ma https://www.m-bonk.pl.ng/personal-data Alę
                https://wikipedia.org
                Matteo:http://localhost:8080
                """;
        var result = urlVerificationSmsHandler.findURL(message);
        var expected = List.of("www.wp.pl", "https://wikipedia.org", "http://localhost:8080", "https://www.m-bonk.pl.ng/personal-data");
        assertTrue(result.containsAll(expected));
    }

    @Test
    void shouldReturnEmptyListIfNoURLsWereFound() {
        var message = """
                Dzień dobry.
                W związku z audytem nadzór finansowy w naszym banku proszą o potwierdzanie danych pod adresem.
                """;
        var result = urlVerificationSmsHandler.findURL(message);
        assertTrue(result.isEmpty());
    }
}