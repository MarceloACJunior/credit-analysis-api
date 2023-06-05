package com.jazztech.creditanalysis.controller.request;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreditAnalysisRequest(UUID clientId, BigDecimal monthlyIncome, BigDecimal requestedAmount) {
}
