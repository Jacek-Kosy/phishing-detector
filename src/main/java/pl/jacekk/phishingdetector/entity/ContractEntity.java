package pl.jacekk.phishingdetector.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "contracts", indexes = {
    @Index(name = "idx_contract_msisdn", columnList = "msisdn"),
    @Index(name = "idx_contract_service_status", columnList = "hasActiveService")
})
@NoArgsConstructor
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    @NotBlank
    @Column(unique = true)
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
