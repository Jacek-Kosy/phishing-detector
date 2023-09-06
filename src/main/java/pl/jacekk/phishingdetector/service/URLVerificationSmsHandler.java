package pl.jacekk.phishingdetector.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.jacekk.phishingdetector.model.SmsMessage;
import pl.jacekk.phishingdetector.repository.ContractRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class URLVerificationSmsHandler implements SmsHandler {
    private static final Pattern URL_PATTERN =
            Pattern.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))");
    private final ContractRepository repository;
    @Getter
    private SmsHandler next;

    @Override
    public void setNext(SmsHandler smsHandler) {
        next = smsHandler;
    }

    @Override
    public void handle(SmsMessage sms) {
        var urls = findURLs(sms.message());
        if (!urls.isEmpty() && verifyServiceStatus(sms.recipient())) {
            log.info("Message: {} qualifies for malicious URL verification", sms.message());
        } else if (next != null) next.handle(sms);
    }

    protected boolean verifyServiceStatus(String msisdn) {
        var contract = repository.findByMsisdn(msisdn);
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


}
