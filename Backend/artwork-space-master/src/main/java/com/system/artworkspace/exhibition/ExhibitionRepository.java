package com.system.artworkspace.exhibition;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ExhibitionRepository extends JpaRepository<ExhibitionEntity, Long> {
    List<ExhibitionEntity> findByEndDateBefore(Date date);

    List <ExhibitionEntity> findByCuratorId(Long id);
}
