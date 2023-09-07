package pl.jacekk.phishingdetector.service;

import feign.FeignException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.jacekk.phishingdetector.entity.LinkEntity;
import pl.jacekk.phishingdetector.feign.ThreatScoreClient;
import pl.jacekk.phishingdetector.model.*;
import pl.jacekk.phishingdetector.repository.ContractRepository;
import pl.jacekk.phishingdetector.repository.LinkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@Component
@AllArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class URLVerificationSmsHandler implements SmsHandler {
    private static final Pattern URL_PATTERN =
            Pattern.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))");



    private final ContractRepository contractRepository;
    private final LinkRepository linkRepository;
    private final ThreatScoreClient threatScoreClient;

    @Getter
    private SmsHandler next;

    @Value("${url-verification.confidence-threshold}")
    private String confidenceThresholdText;

    private ConfidenceLevel confidenceThreshold;

    @PostConstruct
    private void init() {
        try {
            confidenceThreshold = ConfidenceLevel.valueOf(confidenceThresholdText);
            log.info("Confidence threshold set to: {}", confidenceThreshold);
        } catch (IllegalArgumentException ex) {
            log.warn("Setting confidence threshold from the configuration failed. Default level - HIGHER will be used.");
            confidenceThreshold = ConfidenceLevel.HIGHER;
        }
    }

    @Override
    public void setNext(SmsHandler smsHandler) {
        next = smsHandler;
    }

    @Override
    @Transactional
    public void handle(SmsMessage sms) {
        var urls = findURLs(sms.message());
        if (!urls.isEmpty() && verifyServiceStatus(sms.recipient())) {
            log.info("Message: {} qualifies for malicious URL verification", sms.message());
            if (verifyURLs(urls)) log.info("Blocking a message with malicious URL: {}", sms.message());
            else if (next != null) next.handle(sms);
        } else if (next != null) next.handle(sms);
    }

    protected boolean verifyServiceStatus(String msisdn) {
        var contract = contractRepository.findByMsisdn(msisdn);
        if (contract.isEmpty()) return false;
        else if (contract.get().getHasActiveService() == null) return false;
        else return contract.get().getHasActiveService();
    }

    protected List<String> findURLs(String message) {
        var result = new ArrayList<String>();
        var matcher = URL_PATTERN.matcher(message);
        while (matcher.find()) result.add(matcher.group());
        return result;
    }

    protected boolean checkConfidenceThreshold(Map<ThreatType, ConfidenceLevel> scores) {
        return scores.values().stream()
                .anyMatch(confidenceLevel -> confidenceLevel.compareTo(confidenceThreshold) >= 0);
    }

    protected boolean verifyURLByAPI(String url) {
        try {
            var request = new VerificationRequest(url, List.of(ThreatType.SOCIAL_ENGINEERING, ThreatType.MALWARE, ThreatType.UNWANTED_SOFTWARE), true);
            var response = threatScoreClient.verifyURL(request);
            persistVerifiedLink(url, response);
            log.info("Verified URL: {} and persisted the response in the database", url);
            return checkConfidenceThreshold(response.toThreatTypeConfidenceLevelMap());
        } catch (FeignException ex) {
            log.warn("Failed to verify due to an exception during API call: {}", ex.getMessage());
            return false;
        }
    }

    protected void persistVerifiedLink(String url, VerificationResponse response) {
        var linkEntity = new LinkEntity(url, response.toThreatTypeConfidenceLevelMap());
        linkRepository.save(linkEntity);
    }


    protected boolean verifyURLUsingHistoricalData(LinkEntity linkEntity) {
        log.info("Verifying URL: {} in the database...", linkEntity.getUrl());
        return checkConfidenceThreshold(linkEntity.getScores());
    }


    protected boolean verifyURLs(List<String> urls) {
        return urls.stream().anyMatch(this::verifyURL);
    }

    protected boolean verifyURL(String url) {
        var savedLink = linkRepository.findByUrl(url);
        return savedLink.map(this::verifyURLUsingHistoricalData).orElseGet(() -> verifyURLByAPI(url));
    }

}
