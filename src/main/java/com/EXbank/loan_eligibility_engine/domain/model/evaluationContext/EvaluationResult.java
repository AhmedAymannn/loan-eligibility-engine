package com.EXbank.loan_eligibility_engine.domain.model.evaluationContext;

import com.EXbank.loan_eligibility_engine.domain.enums.DecisionVerdict;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleResult;

import java.util.List;

public record EvaluationResult(
        DecisionVerdict verdict,
        String applicationId,
        List<RuleResult> auditLog
) {
    public EvaluationResult {
        auditLog = List.copyOf(auditLog);
    }
}