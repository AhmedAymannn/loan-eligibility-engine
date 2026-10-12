package com.EXbank.loan_eligibility_engine.domain.model.ruleContext;

public record RuleResult(
        String ruleName,
        boolean passed,
        String reasonCode,
        String message
) {
}
