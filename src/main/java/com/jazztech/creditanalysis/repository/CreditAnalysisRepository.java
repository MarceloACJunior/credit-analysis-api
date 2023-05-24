package com.jazztech.creditanalysis.repository;

import com.jazztech.creditanalysis.repository.entity.CreditAnalysisEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditAnalysisRepository extends JpaRepository<CreditAnalysisEntity, UUID> {

    List<CreditAnalysisEntity> findByClientId(UUID clientId);

}
