package com.jazztech.creditanalysis.mapper;

import com.jazztech.creditanalysis.controller.request.CreditAnalysisRequest;
import com.jazztech.creditanalysis.controller.response.CreditAnalysisResponse;
import com.jazztech.creditanalysis.model.CreditAnalysisModel;
import com.jazztech.creditanalysis.repository.entity.CreditAnalysisEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditAnalysisMapper {
    CreditAnalysisModel modelFromRequest(CreditAnalysisRequest creditAnalysisRequest);

    CreditAnalysisEntity entityFromModel(CreditAnalysisModel creditAnalysisModel);

    CreditAnalysisResponse responseFromEntity(CreditAnalysisEntity creditAnalysisEntity);

    List<CreditAnalysisResponse> listOfModelFromEntity(List<CreditAnalysisEntity> creditAnalysisEntities);
}
