package pl.jacekk.phishingdetector.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "contracts")
@NoArgsConstructor
public class ContractEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Getter
    @NotBlank
    private String msisdn;
    @Getter
    @Setter
    @Column(nullable = false)
    private Boolean hasActiveService;

    @Setter
    private LocalDateTime lastUpdated;

    public ContractEntity(String msisdn, Boolean hasActiveService) {
        this.msisdn = msisdn;
        this.hasActiveService = hasActiveService;
        this.lastUpdated = LocalDateTime.now();
    }
}
