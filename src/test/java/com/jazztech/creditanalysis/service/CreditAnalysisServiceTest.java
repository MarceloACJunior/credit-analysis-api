package com.jazztech.creditanalysis.service;

import com.jazztech.creditanalysis.apiclient.ClientApiClient;
import com.jazztech.creditanalysis.mapper.CreditAnalysisMapper;
import com.jazztech.creditanalysis.mapper.CreditAnalysisMapperImpl;
import com.jazztech.creditanalysis.repository.CreditAnalysisRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

class CreditAnalysisServiceTest {
    @Mock
    private ClientApiClient clientApiClient;
    @Mock
    private CreditAnalysisRepository creditAnalysisRepository;
    @Spy
    private CreditAnalysisMapper creditAnalysisMapper = new CreditAnalysisMapperImpl();

    @InjectMocks
    CreditAnalysisService creditAnalysisService;

    @Captor

    @Test
    void creditAnalysisRequest() {
    }

    @Test
    void getAllCreditAnalysis() {
    }

    @Test
    void getCreditAnalysisById() {
    }
}