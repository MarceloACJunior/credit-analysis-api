package com.jazztech.creditanalysis.model;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

public record CreditAnalysisModel(
        @NotBlank(message = "clientId cannot be null")
        UUID clientId,
        @NotBlank(message = "approved cannot be null")
        Boolean approved,
        BigDecimal approvedLimit,
        @NotBlank(message = "requestedAmount cannot be null")
        BigDecimal requestedAmount,
        @NotBlank(message = "monthlyIncome cannot be null")
        BigDecimal monthlyIncome,
        BigDecimal withdraw,
        Double annualInterest
) {

    @Builder(toBuilder = true)
    public CreditAnalysisModel(UUID clientId, Boolean approved, BigDecimal approvedLimit, BigDecimal requestedAmount,
                               BigDecimal monthlyIncome, BigDecimal withdraw, Double annualInterest) {
        this.clientId = clientId;
        this.approved = approved;
        this.approvedLimit = approvedLimit;
        this.requestedAmount = requestedAmount;
        this.monthlyIncome = monthlyIncome;
        this.withdraw = withdraw;
        this.annualInterest = annualInterest;
    }

    public CreditAnalysisModel updateCreditModel(boolean approved) {
        return this.toBuilder()
                .clientId(this.clientId).build();
    }
}
