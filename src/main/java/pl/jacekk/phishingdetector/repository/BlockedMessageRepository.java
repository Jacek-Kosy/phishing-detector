package pl.jacekk.phishingdetector.repository;

import org.springframework.data.repository.CrudRepository;
import pl.jacekk.phishingdetector.entity.BlockedMessageEntity;

public interface BlockedMessageRepository extends CrudRepository<BlockedMessageEntity, Long> {
}
