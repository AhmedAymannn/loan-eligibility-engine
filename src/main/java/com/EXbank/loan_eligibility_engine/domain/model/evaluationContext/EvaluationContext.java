package com.EXbank.loan_eligibility_engine.domain.model.evaluationContext;

import java.math.BigDecimal;

public record EvaluationContext(
        String applicationId,
        String nationalId,
        int applicantAge,
        int creditScore,
        BigDecimal monthlyNetSalary,
        BigDecimal existingMonthlyDebt,
        BigDecimal requestedAmount,
        int requestedTenureMonths,
        int activeLoans
) {
}