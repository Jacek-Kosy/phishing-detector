package pl.jacekk.phishingdetector.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contracts")
@AllArgsConstructor
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
}
