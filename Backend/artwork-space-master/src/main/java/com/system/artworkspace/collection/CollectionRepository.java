package com.system.artworkspace.collection;

import com.system.artworkspace.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findCollectionByCollectioneer(User user);
    void deleteCollectionById(Long id);

}
