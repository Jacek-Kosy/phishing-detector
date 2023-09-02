package pl.jacekk.phishingdetector.model;

public record SmsMessage(String sender, String recipient, String message) {
}
