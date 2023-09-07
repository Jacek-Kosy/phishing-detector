package pl.jacekk.phishingdetector.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.jacekk.phishingdetector.model.VerificationRequest;
import pl.jacekk.phishingdetector.model.VerificationResponse;

@FeignClient(name = "threat-score", url = "${url-verification.api-url}")
public interface ThreatScoreClient {
    @RequestMapping(method = RequestMethod.POST, value = "/threats/score")
    VerificationResponse verifyURL(@RequestBody VerificationRequest verificationRequest);
}
