package com.jazztech.creditanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jazztech.creditanalysis.apiclient.ClientApiClient;
import com.jazztech.creditanalysis.mapper.CreditAnalysisMapper;
import com.jazztech.creditanalysis.mapper.CreditAnalysisMapperImpl;
import com.jazztech.creditanalysis.model.CreditAnalysisModel;
import com.jazztech.creditanalysis.repository.CreditAnalysisRepository;
import com.jazztech.creditanalysis.repository.entity.CreditAnalysisEntity;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CreditAnalysisServiceTest {
    @Mock
    private ClientApiClient clientApiClient;
    @Mock
    private CreditAnalysisRepository creditAnalysisRepository;
    @Spy
    private CreditAnalysisMapper creditAnalysisMapper = new CreditAnalysisMapperImpl();

    @InjectMocks
    private CreditAnalysisService creditAnalysisService;

    @Test
    void should_not_approve_credit_if_requested_amount_is_greater_than_monthly_income() {
        final CreditAnalysisModel creditAnalysisModel = creditAnalysisModelNotApprovedFactory();
        final CreditAnalysisEntity creditAnalysisEntity = creditAnalysisService.checkConditionals(creditAnalysisModel);

        assertEquals(false, creditAnalysisEntity.getApproved());
    }

    public CreditAnalysisModel creditAnalysisModelNotApprovedFactory() {
        return CreditAnalysisModel.builder()
                .clientId(UUID.randomUUID())
                .requestedAmount(BigDecimal.valueOf(7500))
                .monthlyIncome(BigDecimal.valueOf(50000))
                .build();

    }
}