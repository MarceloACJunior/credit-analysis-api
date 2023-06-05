package com.jazztech.creditanalysis.model;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record CreditAnalysisModel(
        @NotBlank(message = "clientId cannot be null") UUID clientId,
        @NotBlank(message = "approved cannot be null") Boolean approved, BigDecimal approvedLimit,
        @NotBlank(message = "requestedAmount cannot be null") BigDecimal requestedAmount,
        @NotBlank(message = "monthlyIncome cannot be null") BigDecimal monthlyIncome, BigDecimal withdraw,
        Double annualInterest
) {

}
