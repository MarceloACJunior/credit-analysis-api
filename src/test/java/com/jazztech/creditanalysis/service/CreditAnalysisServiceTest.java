package com.jazztech.creditanalysis.service;

import static com.jazztech.creditanalysis.service.Factory.creditAnalysisEntityFactory;
import static com.jazztech.creditanalysis.service.Factory.creditAnalysisModelApprovedFactory;
import static com.jazztech.creditanalysis.service.Factory.creditAnalysisModelGreaterThanFiftyPercent;
import static com.jazztech.creditanalysis.service.Factory.creditAnalysisModelLessThanFiftyPercent;
import static com.jazztech.creditanalysis.service.Factory.creditAnalysisModelNotApprovedFactory;
import static com.jazztech.creditanalysis.service.Factory.creditAnalysisRequestFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.jazztech.creditanalysis.apiclient.ClientApiClient;
import com.jazztech.creditanalysis.apiclient.clientdto.ClientDto;
import com.jazztech.creditanalysis.controller.response.CreditAnalysisResponse;
import com.jazztech.creditanalysis.handler.exceptions.ClientNotFoundException;
import com.jazztech.creditanalysis.mapper.CreditAnalysisMapper;
import com.jazztech.creditanalysis.mapper.CreditAnalysisMapperImpl;
import com.jazztech.creditanalysis.model.CreditAnalysisModel;
import com.jazztech.creditanalysis.repository.CreditAnalysisRepository;
import com.jazztech.creditanalysis.repository.entity.CreditAnalysisEntity;
import feign.FeignException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
    @Captor
    private ArgumentCaptor<UUID> clientID;
    @Captor
    private ArgumentCaptor<UUID> analysisID;

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
    void should_return_CreditAnalysisModel_when_clientId_exists() {
        when(clientApiClient.getClientById(clientID.capture())).thenReturn(new ClientDto(UUID.fromString("12341234-1234-1234-1234-123412341234")));
        final CreditAnalysisModel creditAnalysisModel = creditAnalysisService.checkIfClientExists(creditAnalysisRequestFactory());
        assertEquals(clientID.getValue(), creditAnalysisModel.clientId());
    }

    @Test
    void should_throw_ClientNotFoundException_when_clientId_does_not_exist() {
        when(clientApiClient.getClientById(clientID.capture())).thenThrow(FeignException.class);
        ClientNotFoundException exception = assertThrows(ClientNotFoundException.class,
                () -> creditAnalysisService.checkIfClientExists(creditAnalysisRequestFactory()));
        assertEquals("Client not found by id %s".formatted(clientID.getValue()), exception.getMessage());
    }

    @Test
    void should_approve_seven_thousand_and_five_hundred_credit_if_requested_amount_is_greater_than_half_of_fifty_thousand() {
        final BigDecimal approvedLimit = creditAnalysisService.checkApprovedLimit(creditAnalysisModelGreaterThanFiftyPercent().monthlyIncome(),
                creditAnalysisModelGreaterThanFiftyPercent().requestedAmount());
        assertEquals(BigDecimal.valueOf(7500.0), approvedLimit.setScale(1));
    }

    @Test
    void should_approve_fifteen_thousand_credit_if_requested_amount_is_less_than_half_of_fifty_thousand() {
        final BigDecimal approvedLimit = creditAnalysisService.checkApprovedLimit(creditAnalysisModelLessThanFiftyPercent().monthlyIncome(),
                creditAnalysisModelLessThanFiftyPercent().requestedAmount());
        assertEquals(BigDecimal.valueOf(15000.0), approvedLimit.setScale(1));
    }

    @Test
    void should_return_a_credit_analysis_if_it_exists_by_id() {
        when(creditAnalysisRepository.findById(analysisID.capture())).thenReturn(Optional.ofNullable(creditAnalysisEntityFactory()));
        final CreditAnalysisResponse creditAnalysisById = creditAnalysisService.getCreditAnalysisById(creditAnalysisEntityFactory().getId());
        assertEquals(creditAnalysisById.id(), analysisID.getValue());
    }

    @Test
    void should_throw_ClientNotFoundException_if_it_does_not_exist_by_id() {
        // O mock deve retornar um optional empty
        when(creditAnalysisRepository.findById(analysisID.capture())).thenThrow(ClientNotFoundException.class);
        ClientNotFoundException exception = assertThrows(ClientNotFoundException.class,
                () -> creditAnalysisService.getCreditAnalysisById(UUID.randomUUID()));
    }
}