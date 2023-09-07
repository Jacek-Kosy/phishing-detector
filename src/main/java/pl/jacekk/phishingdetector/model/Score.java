package pl.jacekk.phishingdetector.model;

public record Score(ThreatType threatType, ConfidenceLevel confidenceLevel) {
}
