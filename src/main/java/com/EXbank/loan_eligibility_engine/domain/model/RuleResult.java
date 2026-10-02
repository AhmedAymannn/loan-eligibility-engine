package com.EXbank.loan_eligibility_engine.domain.model;

public record RuleResult(
        String ruleName,
        boolean passed,
        String reasonCode,
        String message
) {
    public static RuleResult pass(String ruleName, String message) {
        return new RuleResult(ruleName, true, "PASS", message);
    }

    public static RuleResult fail(String ruleName, String reasonCode, String message) {
        return new RuleResult(ruleName, false, reasonCode, message);
    }
}
