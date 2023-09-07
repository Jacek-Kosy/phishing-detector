package pl.jacekk.phishingdetector.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Boolean hasActiveService;

    public ContractEntity(String msisdn, Boolean hasActiveService) {
        this.msisdn = msisdn;
        this.hasActiveService = hasActiveService;
    }
}
