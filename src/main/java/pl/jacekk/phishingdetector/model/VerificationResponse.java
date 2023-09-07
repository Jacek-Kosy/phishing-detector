package pl.jacekk.phishingdetector.model;

import java.util.List;

public record VerificationResponse (List<Score> scores) {}
