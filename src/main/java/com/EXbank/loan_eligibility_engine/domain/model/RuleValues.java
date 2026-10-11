package com.EXbank.loan_eligibility_engine.domain.model;

import java.math.BigDecimal;

public record RuleValues(
         int minCreditScore,
         int minAge,
         BigDecimal minMonthlyIncome

) {
}
