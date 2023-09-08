package pl.jacekk.phishingdetector.repository;

import org.springframework.data.repository.CrudRepository;
import pl.jacekk.phishingdetector.entity.LinkEntity;

import java.util.Optional;

public interface LinkRepository extends CrudRepository<LinkEntity, Integer> {
    Optional<LinkEntity> findByUrl(String url);
}
