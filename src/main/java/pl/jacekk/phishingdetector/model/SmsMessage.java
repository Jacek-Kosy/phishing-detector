package pl.jacekk.phishingdetector.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsMessage implements Serializable {
    private String sender;
    private String recipient;
    private String message;

    public String sender() {
        return this.sender;
    }

    public String recipient() {
        return this.recipient;
    }

    public String message() {
        return this.message;
    }
}
