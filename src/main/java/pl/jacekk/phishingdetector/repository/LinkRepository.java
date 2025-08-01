package pl.jacekk.phishingdetector.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.jacekk.phishingdetector.entity.LinkEntity;

import java.util.Optional;

public interface LinkRepository extends CrudRepository<LinkEntity, Integer> {
    
    @Cacheable(value = "linkCache", key = "#url")
    @Query("SELECT l FROM LinkEntity l WHERE l.url = :url")
    Optional<LinkEntity> findByUrl(@Param("url") String url);
    
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM LinkEntity l WHERE l.url = :url")
    boolean existsByUrl(@Param("url") String url);
}
