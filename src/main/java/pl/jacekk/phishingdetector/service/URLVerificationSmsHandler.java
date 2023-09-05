package pl.jacekk.phishingdetector.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.jacekk.phishingdetector.model.SmsMessage;
import pl.jacekk.phishingdetector.repository.ContractRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class URLVerificationSmsHandler implements SmsHandler {
    private static final Pattern URL_PATTERN =
            Pattern.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))");
    private final ContractRepository repository;
    @Getter
    private SmsHandler next;
    @Override
    public void setNext(SmsHandler smsHandler) {

    }

    @Override
    public void handle(SmsMessage sms) {

    }

    protected boolean verifyServiceStatus(String msisdn) {
        var contract = repository.findByMsisdn(msisdn);
        if (contract.isEmpty()) return false;
        else if (contract.get().getHasActiveService() == null) return false;
        else return contract.get().getHasActiveService();
    }

    protected String findURL(String message) {
        var result = new ArrayList<String>();
        var matcher = URL_PATTERN.matcher(message);
        if (matcher.find()) return matcher.group();
        return null;
    }


}
