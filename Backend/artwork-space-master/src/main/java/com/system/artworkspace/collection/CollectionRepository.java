package com.system.artworkspace.collection;

import com.system.artworkspace.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findCollectionByOwner(User user);
    void deleteCollectionById(Long id);

}
