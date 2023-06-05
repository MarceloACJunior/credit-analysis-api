package com.jazztech.creditanalysis.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "CREDIT_ANALYSIS")
@Immutable
public class CreditAnalysisEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;
    @Column(name = "client_id")
    UUID clientId;
    @Column(name = "approved")
    Boolean approved;
    @Column(name = "approved_limit")
    BigDecimal approvedLimit;
    @Column(name = "requested_amount")
    BigDecimal requestedAmount;
    @Column(name = "monthly_income")
    BigDecimal monthlyIncome;
    @Column(name = "withdraw")
    BigDecimal withdraw;
    @Column(name = "annual_interest")
    Double annualInterest;
    @CreationTimestamp
    @Column(name = "date")
    LocalDateTime date;

    @Builder(toBuilder = true)
<<<<<<< HEAD
    public CreditAnalysisEntity(UUID id, UUID clientId, Boolean approved, BigDecimal approvedLimit, BigDecimal requestedAmount, BigDecimal monthlyIncome, BigDecimal withdraw, Double annualInterest, LocalDateTime date) {
=======
    public CreditAnalysisEntity(UUID id, UUID clientId, Boolean approved, BigDecimal approvedLimit, BigDecimal requestedAmount,
                                BigDecimal monthlyIncome, BigDecimal withdraw, Double annualInterest, LocalDateTime date) {
>>>>>>> feature/credit-analise
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
