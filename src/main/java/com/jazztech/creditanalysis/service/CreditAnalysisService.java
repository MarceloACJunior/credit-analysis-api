package com.jazztech.creditanalysis.service;

import com.jazztech.creditanalysis.apiclient.ClientApiClient;
import com.jazztech.creditanalysis.apiclient.clientdto.ClientDto;
import com.jazztech.creditanalysis.controller.request.CreditAnalysisRequest;
import com.jazztech.creditanalysis.controller.response.CreditAnalysisResponse;
import com.jazztech.creditanalysis.handler.exceptions.ClientNotFoundException;
import com.jazztech.creditanalysis.mapper.CreditAnalysisMapper;
import com.jazztech.creditanalysis.model.CreditAnalysisModel;
import com.jazztech.creditanalysis.repository.CreditAnalysisRepository;
import com.jazztech.creditanalysis.repository.entity.CreditAnalysisEntity;
import feign.FeignException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditAnalysisService {
    private final ClientApiClient clientApiClient;
    private final CreditAnalysisMapper creditAnalysisMapper;
    private final CreditAnalysisRepository creditAnalysisRepository;

    public static final Double ANNUAL_INTEREST = 0.15;
    public static final BigDecimal MAX_MONTHLY_INCOME = BigDecimal.valueOf(50_000);

    public CreditAnalysisResponse creditAnalysisRequest(CreditAnalysisRequest creditAnalysisRequest) {
        final CreditAnalysisModel creditAnalysisModel = checkIfClientExists(creditAnalysisRequest);
        final CreditAnalysisEntity creditAnalysisEntity = checkConditionals(creditAnalysisModel);
        final CreditAnalysisEntity creditAnalysisSaved = creditAnalysisRepository.save(creditAnalysisEntity);
        return creditAnalysisMapper.responseFromEntity(creditAnalysisSaved);
    }

    public CreditAnalysisModel checkIfClientExists(CreditAnalysisRequest creditAnalysisRequest) {
        try {
            clientApiClient.getClientById(creditAnalysisRequest.clientId());
        } catch (FeignException fe) {
            throw new ClientNotFoundException("Client not found by id %s".formatted(creditAnalysisRequest.clientId()));
        }
        return creditAnalysisMapper.modelFromRequest(creditAnalysisRequest);
    }

    public CreditAnalysisEntity checkConditionals(CreditAnalysisModel creditAnalysisModel) {

        final BigDecimal requestedAmountVar = creditAnalysisModel.requestedAmount();
        final BigDecimal monthlyIncomeVar = creditAnalysisModel.monthlyIncome();
        final int requestedAmountIsGreaterThanMonthlyIncome = requestedAmountVar.compareTo(monthlyIncomeVar);

        if (requestedAmountIsGreaterThanMonthlyIncome > 0) {
            final CreditAnalysisModel creditAnalysisModelUpdated = CreditAnalysisModel.builder()
                    .clientId(creditAnalysisModel.clientId())
                    .approved(false)
                    .approvedLimit(BigDecimal.ZERO)
                    .requestedAmount(requestedAmountVar.setScale(2, RoundingMode.HALF_UP))
                    .monthlyIncome(monthlyIncomeVar.setScale(2, RoundingMode.HALF_UP))
                    .withdraw(BigDecimal.ZERO)
                    .annualInterest(0D)
                    .build();
            return creditAnalysisMapper.entityFromModel(creditAnalysisModelUpdated);
        } else {
            final BigDecimal approvedLimitVar = checkApprovedLimit(monthlyIncomeVar, requestedAmountVar);
            final BigDecimal withdrawLimitVar = checkWithdrawLimit(approvedLimitVar);
            final CreditAnalysisModel creditAnalysisModelUpdated = CreditAnalysisModel.builder()
                    .clientId(creditAnalysisModel.clientId())
                    .approved(true)
                    .approvedLimit(approvedLimitVar.setScale(2, RoundingMode.HALF_UP))
                    .requestedAmount(requestedAmountVar.setScale(2, RoundingMode.HALF_UP))
                    .monthlyIncome(monthlyIncomeVar.setScale(2, RoundingMode.HALF_UP))
                    .withdraw(withdrawLimitVar.setScale(2, RoundingMode.HALF_UP))
                    .annualInterest(ANNUAL_INTEREST)
                    .build();
            return creditAnalysisMapper.entityFromModel(creditAnalysisModelUpdated);
        }
    }

    public BigDecimal checkApprovedLimit(BigDecimal monthlyIncome, BigDecimal requestedAmount) {
        // Criar constantes para valores constantes
        BigDecimal consideredValue = monthlyIncome;
        if (monthlyIncome.compareTo(MAX_MONTHLY_INCOME) > 0) {
            consideredValue = MAX_MONTHLY_INCOME;
        }

        final BigDecimal approvedLimit;
        final BigDecimal fiftyPercentOfConsideredValue = consideredValue.divide(BigDecimal.valueOf(2));
        final int requestedAmountIsGreaterThanHalfOfConsideredValue = requestedAmount.compareTo(fiftyPercentOfConsideredValue);
        if (requestedAmountIsGreaterThanHalfOfConsideredValue > 0) {
            final BigDecimal fifteenPercentOfConsideredValue = BigDecimal.valueOf(0.15);
            approvedLimit = consideredValue.multiply(fifteenPercentOfConsideredValue);
        } else {
            final BigDecimal thirtyPercentOfConsideredValue = BigDecimal.valueOf(0.30);
            approvedLimit = consideredValue.multiply(thirtyPercentOfConsideredValue);
        }
        return approvedLimit;
    }

    public BigDecimal checkWithdrawLimit(BigDecimal approvedLimitVar) {
        final BigDecimal withdrawLimitVar = BigDecimal.valueOf(0.10);
        return approvedLimitVar.multiply(withdrawLimitVar);
    }

    public CreditAnalysisResponse getCreditAnalysisById(UUID creditAnalysisId) {
        final CreditAnalysisEntity creditAnalysisEntity = creditAnalysisRepository.findById(creditAnalysisId)
                .orElseThrow(() -> new ClientNotFoundException("Credit Analysis not found by id %s".formatted(creditAnalysisId)));
        return creditAnalysisMapper.responseFromEntity(creditAnalysisEntity);
    }

    public List<CreditAnalysisResponse> getAllCreditAnalysis() {
        final List<CreditAnalysisEntity> creditAnalysisEntities;
        creditAnalysisEntities = creditAnalysisRepository.findAll();
        return creditAnalysisEntities.stream().map(creditAnalysisMapper::responseFromEntity).toList();
    }

    public List<CreditAnalysisResponse> getCreditAnalysisByClientId(UUID creditAnalysisClientId) {
        final List<CreditAnalysisEntity> creditAnalysisEntities;
        try {
            creditAnalysisEntities = creditAnalysisRepository.findByClientId(creditAnalysisClientId);
        } catch (FeignException fe) {
            throw new ClientNotFoundException("Client not found by ID %s".formatted(creditAnalysisClientId));
        }
        return creditAnalysisEntities.stream().map(creditAnalysisMapper::responseFromEntity).toList();
    }

    public List<CreditAnalysisResponse> getCreditAnalysisByCPF(String clientCPF) {
        final List<ClientDto> clientDto = clientApiClient.getClientByCPF(clientCPF);
        if (clientDto.isEmpty()) {
            throw new ClientNotFoundException("Client not found by id %s".formatted(clientCPF));
        }
        return getCreditAnalysisByClientId(clientDto.get(0).id());
    }
}
