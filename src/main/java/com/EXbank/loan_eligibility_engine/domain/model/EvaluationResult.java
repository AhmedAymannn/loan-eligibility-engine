package com.EXbank.loan_eligibility_engine.domain.model;

import com.EXbank.loan_eligibility_engine.domain.enums.DecisionVerdict;

import java.util.List;

public record EvaluationResult(
        DecisionVerdict verdict,
        String applicationId,
        List<RuleResult> auditLog
) {
    // Compact constructor to guarantee immutability of the collection
    public EvaluationResult {
        auditLog = List.copyOf(auditLog);
    }
}