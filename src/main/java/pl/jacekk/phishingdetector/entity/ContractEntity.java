package pl.jacekk.phishingdetector.entity;

import jakarta.persistence.*;
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
    private Long id;
    @Getter
    @Column(unique = true, nullable = false)
    private String msisdn;
    @Getter
    @Setter
    private Boolean hasActiveService;
}
