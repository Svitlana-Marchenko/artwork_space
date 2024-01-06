package com.system.artworkspace.collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {
    List<CollectionEntity> getCollectionEntitiesByOwnerId(Long id);
}