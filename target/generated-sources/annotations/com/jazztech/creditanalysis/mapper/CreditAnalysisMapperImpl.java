package com.jazztech.creditanalysis.mapper;

import com.jazztech.creditanalysis.controller.request.CreditAnalysisRequest;
import com.jazztech.creditanalysis.controller.response.CreditAnalysisResponse;
import com.jazztech.creditanalysis.model.CreditAnalysisModel;
import com.jazztech.creditanalysis.repository.entity.CreditAnalysisEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-19T12:06:28-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class CreditAnalysisMapperImpl implements CreditAnalysisMapper {

    @Override
    public CreditAnalysisModel modelFromRequest(CreditAnalysisRequest creditAnalysisRequest) {
        if ( creditAnalysisRequest == null ) {
            return null;
        }

        CreditAnalysisModel.CreditAnalysisModelBuilder creditAnalysisModel = CreditAnalysisModel.builder();

        creditAnalysisModel.clientId( creditAnalysisRequest.clientId() );
        creditAnalysisModel.requestedAmount( creditAnalysisRequest.requestedAmount() );
        creditAnalysisModel.monthlyIncome( creditAnalysisRequest.monthlyIncome() );

        return creditAnalysisModel.build();
    }

    @Override
    public CreditAnalysisEntity entityFromModel(CreditAnalysisModel creditAnalysisModel) {
        if ( creditAnalysisModel == null ) {
            return null;
        }

        CreditAnalysisEntity.CreditAnalysisEntityBuilder creditAnalysisEntity = CreditAnalysisEntity.builder();

        creditAnalysisEntity.clientId( creditAnalysisModel.clientId() );
        creditAnalysisEntity.approved( creditAnalysisModel.approved() );
        creditAnalysisEntity.approvedLimit( creditAnalysisModel.approvedLimit() );
        creditAnalysisEntity.requestedAmount( creditAnalysisModel.requestedAmount() );
        creditAnalysisEntity.monthlyIncome( creditAnalysisModel.monthlyIncome() );
        creditAnalysisEntity.withdraw( creditAnalysisModel.withdraw() );
        creditAnalysisEntity.annualInterest( creditAnalysisModel.annualInterest() );

        return creditAnalysisEntity.build();
    }

    @Override
    public CreditAnalysisResponse responseFromEntity(CreditAnalysisEntity creditAnalysisEntity) {
        if ( creditAnalysisEntity == null ) {
            return null;
        }

        UUID id = null;
        Boolean approved = null;
        BigDecimal approvedLimit = null;
        BigDecimal withdraw = null;
        Double annualInterest = null;
        UUID clientId = null;
        LocalDateTime date = null;

        id = creditAnalysisEntity.getId();
        approved = creditAnalysisEntity.getApproved();
        approvedLimit = creditAnalysisEntity.getApprovedLimit();
        withdraw = creditAnalysisEntity.getWithdraw();
        annualInterest = creditAnalysisEntity.getAnnualInterest();
        clientId = creditAnalysisEntity.getClientId();
        date = creditAnalysisEntity.getDate();

        CreditAnalysisResponse creditAnalysisResponse = new CreditAnalysisResponse( id, approved, approvedLimit, withdraw, annualInterest, clientId, date );

        return creditAnalysisResponse;
    }

    @Override
    public List<CreditAnalysisResponse> listOfModelFromEntity(List<CreditAnalysisEntity> creditAnalysisEntities) {
        if ( creditAnalysisEntities == null ) {
            return null;
        }

        List<CreditAnalysisResponse> list = new ArrayList<CreditAnalysisResponse>( creditAnalysisEntities.size() );
        for ( CreditAnalysisEntity creditAnalysisEntity : creditAnalysisEntities ) {
            list.add( responseFromEntity( creditAnalysisEntity ) );
        }

        return list;
    }
}
