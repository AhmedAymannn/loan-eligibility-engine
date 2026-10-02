package com.EXbank.loan_eligibility_engine.api.dto.response;


/**
 * Detailed audit log entry for an individual rule executed during evaluation.
 */
public record ExecutedRuleLogDTO(
        String ruleName,
        boolean passed,
        String reasonCode,
        String message
) {}