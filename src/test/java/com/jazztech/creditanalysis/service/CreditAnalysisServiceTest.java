package com.jazztech.creditanalysis.service;

import com.jazztech.creditanalysis.apiclient.ClientApiClient;
import com.jazztech.creditanalysis.apiclient.ClientDto.ClientDto;
import com.jazztech.creditanalysis.handler.exceptions.ClientNotFoundException;
import com.jazztech.creditanalysis.mapper.CreditAnalysisMapper;
import com.jazztech.creditanalysis.mapper.CreditAnalysisMapperImpl;
import com.jazztech.creditanalysis.model.CreditAnalysisModel;
import com.jazztech.creditanalysis.repository.CreditAnalysisRepository;
import com.jazztech.creditanalysis.repository.entity.CreditAnalysisEntity;
import feign.FeignException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.jazztech.creditanalysis.service.Factory.*;
import static com.jazztech.creditanalysis.service.Factory.creditAnalysisRequestFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CreditAnalysisServiceTest {
    @Mock
    private ClientApiClient clientApiClient;
    @Mock
    private CreditAnalysisRepository creditAnalysisRepository;
    @Spy
    private CreditAnalysisMapper creditAnalysisMapper = new CreditAnalysisMapperImpl();
    @Captor
    private ArgumentCaptor<UUID> clientID;

    @InjectMocks
    private CreditAnalysisService creditAnalysisService;

    @Test
    void should_not_approve_credit_if_requested_amount_is_greater_than_monthly_income() {
        final CreditAnalysisModel creditAnalysisModel = creditAnalysisModelNotApprovedFactory();
        final CreditAnalysisEntity creditAnalysisEntity = creditAnalysisService.checkConditionals(creditAnalysisModel);

        assertEquals(false, creditAnalysisEntity.getApproved());
    }

    @Test
    void should_approve_credit_if_requested_amount_is_less_than_monthly_income() {
        final CreditAnalysisModel creditAnalysisModel = creditAnalysisModelApprovedFactory();
        final CreditAnalysisEntity creditAnalysisEntity = creditAnalysisService.checkConditionals(creditAnalysisModel);

        assertEquals(true, creditAnalysisEntity.getApproved());
    }

    @Test
    void should_return_CreditAnalysisModel_when_clientId_exists(){
        when(clientApiClient.getClientById(clientID.capture())).thenReturn(new ClientDto(UUID.fromString("12341234-1234-1234-1234-123412341234")));
        final CreditAnalysisModel creditAnalysisModel = creditAnalysisService.checkIfClientExists(creditAnalysisRequestFactory());
        assertEquals(clientID.getValue(), creditAnalysisModel.clientId());
    }

    @Test
    void should_throw_ClientNotFoundException_when_clientId_does_not_exist(){
        when(clientApiClient.getClientById(clientID.capture())).thenThrow(FeignException.class);
        ClientNotFoundException exception = assertThrows(ClientNotFoundException.class, () -> creditAnalysisService.checkIfClientExists(creditAnalysisRequestFactory()));
        assertEquals("Client not found by id %s".formatted(clientID.getValue()), exception.getMessage());
    }

    @Test
    void should_consider_50_thousand_as_the_maximum_amount_in_the_credit_calculation(){

    }
}