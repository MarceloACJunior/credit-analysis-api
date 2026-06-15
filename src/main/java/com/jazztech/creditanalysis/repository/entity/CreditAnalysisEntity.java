package com.jazztech.creditanalysis.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "credit_analysis")
public class CreditAnalysisEntity {
    @Id
    UUID id;
    UUID clientId;
    Boolean approved;
    BigDecimal approvedLimit;
    BigDecimal requestedAmount;
    BigDecimal monthlyIncome;
    BigDecimal withdraw;
    Double annualInterest;
    LocalDateTime date;

    @Builder(toBuilder = true)
    public CreditAnalysisEntity(UUID id, UUID clientId, Boolean approved, BigDecimal approvedLimit, BigDecimal requestedAmount, BigDecimal monthlyIncome, BigDecimal withdraw, Double annualInterest, LocalDateTime date) {
        this.id = id;
        this.clientId = clientId;
        this.approved = approved;
        this.approvedLimit = approvedLimit;
        this.requestedAmount = requestedAmount;
        this.monthlyIncome = monthlyIncome;
        this.withdraw = withdraw;
        this.annualInterest = annualInterest;
        this.date = date;
    }

    private CreditAnalysisEntity() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public BigDecimal getApprovedLimit() {
        return approvedLimit;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public BigDecimal getWithdraw() {
        return withdraw;
    }

    public Double getAnnualInterest() {
        return annualInterest;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
