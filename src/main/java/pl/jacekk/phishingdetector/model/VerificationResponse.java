package pl.jacekk.phishingdetector.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record VerificationResponse (List<Score> scores) {
    public Map<ThreatType, ConfidenceLevel> toThreatTypeConfidenceLevelMap() {
        return scores.stream().collect(Collectors.toMap(Score::threatType, Score::confidenceLevel));
    }
}
