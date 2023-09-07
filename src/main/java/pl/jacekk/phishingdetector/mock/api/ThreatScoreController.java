package pl.jacekk.phishingdetector.mock.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jacekk.phishingdetector.model.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api-prefix}/threats")
public class ThreatScoreController {

    private final VerificationResponse bankResponse = new VerificationResponse(List.of(new Score(ThreatType.SOCIAL_ENGINEERING, ConfidenceLevel.EXTREMELY_HIGH), new Score(
            ThreatType.MALWARE, ConfidenceLevel.HIGHER), new Score(ThreatType.UNWANTED_SOFTWARE, ConfidenceLevel.HIGH)));
    private final VerificationResponse malwareResponse = new VerificationResponse(List.of(new Score(ThreatType.SOCIAL_ENGINEERING, ConfidenceLevel.HIGH), new Score(
            ThreatType.MALWARE, ConfidenceLevel.EXTREMELY_HIGH), new Score(ThreatType.UNWANTED_SOFTWARE, ConfidenceLevel.HIGH)));
    private final VerificationResponse normalSiteResponse = new VerificationResponse(List.of(new Score(ThreatType.SOCIAL_ENGINEERING, ConfidenceLevel.LOW), new Score(
            ThreatType.MALWARE, ConfidenceLevel.SAFE), new Score(ThreatType.UNWANTED_SOFTWARE, ConfidenceLevel.SAFE)));
    private final VerificationResponse defaultResponse = new VerificationResponse(List.of(new Score(ThreatType.SOCIAL_ENGINEERING, ConfidenceLevel.LOW), new Score(
            ThreatType.MALWARE, ConfidenceLevel.LOW), new Score(ThreatType.UNWANTED_SOFTWARE, ConfidenceLevel.LOW)));
    @PostMapping("/score")
    public VerificationResponse verifyURL(@RequestBody VerificationRequest verificationRequest) {
        log.info("Received an API request: {}", verificationRequest);
        switch (verificationRequest.uri()) {
            case "https://www.m-bonk.pl.ng/personal-data" -> {
                return bankResponse;
            }
            case "http://sciagnij-wirusa.pl" -> {
                return malwareResponse;
            }
            case "www.zwykla-strona.pl" -> {
                return normalSiteResponse;
            }
            default -> {
                return defaultResponse;
            }
        }
    }
}
