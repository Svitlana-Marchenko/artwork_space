package com.system.artworkspace.exhibition;

import com.system.artworkspace.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
    List<Exhibition> findExhibitionsByCurator(User user);
}
