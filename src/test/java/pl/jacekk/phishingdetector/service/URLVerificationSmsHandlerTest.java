package pl.jacekk.phishingdetector.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jacekk.phishingdetector.feign.ThreatScoreClient;
import pl.jacekk.phishingdetector.model.ConfidenceLevel;
import pl.jacekk.phishingdetector.model.ThreatType;
import pl.jacekk.phishingdetector.repository.ContractRepository;
import pl.jacekk.phishingdetector.repository.LinkRepository;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class URLVerificationSmsHandlerTest {
    private final ContractRepository contractRepository = Mockito.mock(ContractRepository.class);
    private final LinkRepository linkRepository = Mockito.mock(LinkRepository.class);
    private final ThreatScoreClient threatScoreClient = Mockito.mock(ThreatScoreClient.class);
    private final URLVerificationSmsHandler urlVerificationSmsHandler = new URLVerificationSmsHandler(contractRepository, linkRepository, threatScoreClient, null, "HIGHER", ConfidenceLevel.HIGHER);

    @Test
    void shouldReturnAllMatchingURLs() {
        var message = """
                Ala ma kota
                www.wp.pl
                Kot ma https://www.m-bonk.pl.ng/personal-data Alę
                https://wikipedia.org
                Matteo:http://localhost:8080
                """;
        var result = urlVerificationSmsHandler.findURLs(message);
        var expected = List.of("www.wp.pl", "https://wikipedia.org", "http://localhost:8080", "https://www.m-bonk.pl.ng/personal-data");
        assertTrue(result.containsAll(expected));
    }

    @Test
    void shouldReturnEmptyListIfNoURLsWereFound() {
        var message = """
                Dzień dobry.
                W związku z audytem nadzór finansowy w naszym banku proszą o potwierdzanie danych pod adresem.
                """;
        var result = urlVerificationSmsHandler.findURLs(message);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnTrueIfMaxConfidenceLevelHigherThanTheThreshold() {
        var threatMap = Map.of(ThreatType.MALWARE, ConfidenceLevel.HIGH, ThreatType.SOCIAL_ENGINEERING, ConfidenceLevel.VERY_HIGH, ThreatType.UNWANTED_SOFTWARE, ConfidenceLevel.SAFE);
        assertTrue(urlVerificationSmsHandler.checkConfidenceThreshold(threatMap));
    }

    @Test
    void shouldReturnTrueIfMaxConfidenceLevelEqualToTheThreshold() {
        var threatMap = Map.of(ThreatType.MALWARE, ConfidenceLevel.HIGHER, ThreatType.SOCIAL_ENGINEERING, ConfidenceLevel.LOW, ThreatType.UNWANTED_SOFTWARE, ConfidenceLevel.SAFE);
        assertTrue(urlVerificationSmsHandler.checkConfidenceThreshold(threatMap));
    }

    @Test
    void shouldReturnFalseIfMaxConfidenceLevelLowerThanTheThreshold() {
        var threatMap = Map.of(ThreatType.MALWARE, ConfidenceLevel.HIGH, ThreatType.SOCIAL_ENGINEERING, ConfidenceLevel.LOW, ThreatType.UNWANTED_SOFTWARE, ConfidenceLevel.SAFE);
        assertFalse(urlVerificationSmsHandler.checkConfidenceThreshold(threatMap));
    }
}