package pl.jacekk.phishingdetector.model;

import java.util.List;

public record VerificationRequest(String uri, List<ThreatType> threatTypes, Boolean allowScan) {}
