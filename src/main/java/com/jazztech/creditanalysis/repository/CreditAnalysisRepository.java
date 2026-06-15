package com.jazztech.creditanalysis.repository;

import com.jazztech.creditanalysis.repository.entity.CreditAnalysisEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditAnalysisRepository extends MongoRepository<CreditAnalysisEntity, UUID> {

    List<CreditAnalysisEntity> findByClientId(UUID clientId);

}
