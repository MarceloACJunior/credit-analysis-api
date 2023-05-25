package com.jazztech.creditanalysis.service;

import com.jazztech.creditanalysis.apiclient.ClientDto.ClientDto;
import com.jazztech.creditanalysis.controller.request.CreditAnalysisRequest;
import com.jazztech.creditanalysis.model.CreditAnalysisModel;

import java.math.BigDecimal;
import java.util.UUID;

public class Factory {

    public static CreditAnalysisRequest creditAnalysisRequestFactory(){
        return CreditAnalysisRequest.builder()
                .clientId(UUID.fromString("03df448f-73e7-44f0-bfd1-66c120d7adde"))
                .requestedAmount(BigDecimal.valueOf(7500))
                .monthlyIncome(BigDecimal.valueOf(50000))
                .build();
    }

    public static CreditAnalysisModel creditAnalysisModelNotApprovedFactory() {
        return CreditAnalysisModel.builder()
                .clientId(UUID.randomUUID())
                .requestedAmount(BigDecimal.valueOf(50000))
                .monthlyIncome(BigDecimal.valueOf(7500))
                .build();
    }

    public static CreditAnalysisModel creditAnalysisModelApprovedFactory() {
        return CreditAnalysisModel.builder()
                .clientId(UUID.randomUUID())
                .requestedAmount(BigDecimal.valueOf(7500))
                .monthlyIncome(BigDecimal.valueOf(50000))
                .build();
    }

    public static CreditAnalysisModel creditAnalysisModel
}
