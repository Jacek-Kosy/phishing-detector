package pl.jacekk.phishingdetector.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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
    @OneToMany(mappedBy = "contract")
    private Set<BlockedMessageEntity> blockedMessages;

    public ContractEntity(String msisdn, Boolean hasActiveService) {
        this.msisdn = msisdn;
        this.hasActiveService = hasActiveService;
    }
}
