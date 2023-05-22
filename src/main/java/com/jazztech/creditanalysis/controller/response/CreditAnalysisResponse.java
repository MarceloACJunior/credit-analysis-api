package com.jazztech.creditanalysis.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreditAnalysisResponse(UUID id, Boolean approved, BigDecimal approvedLimit, BigDecimal withdraw, Double annualInterest, UUID clientId,
                                     LocalDateTime date) {
}
