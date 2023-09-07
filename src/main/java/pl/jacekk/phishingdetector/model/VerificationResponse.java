package pl.jacekk.phishingdetector.model;

import java.util.Map;

public record VerificationResponse (Map<ThreatType, ConfidenceLevel> scores) {}
