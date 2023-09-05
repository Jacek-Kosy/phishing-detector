package pl.jacekk.phishingdetector.repository;

import org.springframework.data.repository.CrudRepository;
import pl.jacekk.phishingdetector.entity.ContractEntity;

import java.util.Optional;

public interface ContractRepository extends CrudRepository<ContractEntity, Long> {
    Optional<ContractEntity> findByMsisdn(String msisdn);
}
