package com.EXbank.loan_eligibility_engine.domain.model.ruleContext;

import java.math.BigDecimal;
import java.util.Objects;

public record RuleValues(
        int minAge,
        int maxMaturityAge,
        int minCreditScore,
        BigDecimal minMonthlyIncome,
        BigDecimal minResidualIncome,
        BigDecimal maxDtiPercent,
        BigDecimal maxIncomeMultiple,
        int maxActiveLoans,
        int minTenureMonths,
        int maxTenureMonths,
        BigDecimal minAmount,
        BigDecimal maxAmount,
        BigDecimal annualInterestRate,
        int tierPremiumScore,
        BigDecimal tierStandardMaxAmount
) {
    public RuleValues {
        Objects.requireNonNull(minMonthlyIncome, "minMonthlyIncome");
        Objects.requireNonNull(minResidualIncome, "minResidualIncome");
        Objects.requireNonNull(maxDtiPercent, "maxDtiPercent");
        Objects.requireNonNull(maxIncomeMultiple, "maxIncomeMultiple");
        Objects.requireNonNull(minAmount, "minAmount");
        Objects.requireNonNull(maxAmount, "maxAmount");
        Objects.requireNonNull(annualInterestRate, "annualInterestRate");
        Objects.requireNonNull(tierStandardMaxAmount, "tierStandardMaxAmount");
    }

}