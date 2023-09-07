package pl.jacekk.phishingdetector.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.jacekk.phishingdetector.model.ConfidenceLevel;
import pl.jacekk.phishingdetector.model.ThreatType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "links")
@AllArgsConstructor
@NoArgsConstructor
public class LinkEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank
    private String url;

    @Getter(AccessLevel.NONE)
    @ElementCollection
    @CollectionTable(name = "links_threats")
    private Map<ThreatType, ConfidenceLevel> threatRisks = new HashMap<>();


    public Map<ThreatType, ConfidenceLevel> getThreatRisks() {
        return Collections.unmodifiableMap(threatRisks);
    }

    public void addThreatRisks(Map<ThreatType, ConfidenceLevel> threatMap) {
        threatRisks.putAll(threatMap);
    }

    public LinkEntity(String url, Map<ThreatType, ConfidenceLevel> threatMap) {
        this.url = url;
        addThreatRisks(threatMap);
    }

}
