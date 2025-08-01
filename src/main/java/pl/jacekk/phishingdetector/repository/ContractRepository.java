package pl.jacekk.phishingdetector.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.jacekk.phishingdetector.entity.ContractEntity;

import java.util.Optional;

public interface ContractRepository extends CrudRepository<ContractEntity, Integer> {
    
    @Cacheable(value = "contractCache", key = "#msisdn")
    @Query("SELECT c FROM ContractEntity c WHERE c.msisdn = :msisdn")
    Optional<ContractEntity> findByMsisdn(@Param("msisdn") String msisdn);
    
    @Cacheable(value = "activeContractCache", key = "#msisdn")
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ContractEntity c WHERE c.msisdn = :msisdn AND c.hasActiveService = true")
    boolean existsByMsisdnAndActiveService(@Param("msisdn") String msisdn);
}
