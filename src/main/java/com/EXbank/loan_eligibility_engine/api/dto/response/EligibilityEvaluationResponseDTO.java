package com.EXbank.loan_eligibility_engine.api.dto.response;


import com.EXbank.loan_eligibility_engine.domain.enums.DecisionVerdict;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;

import java.time.Instant;
import java.util.List;

/**
 * Outbound API payload containing the final decision verdict and complete audit trail.
 */
public record EligibilityEvaluationResponseDTO(
        String applicationId,
        DecisionVerdict verdict, // "APPROVED", "DECLINED", "REFER", "COUNTER_OFFER"
        Instant evaluatedAt,
        List<RuleResult> ruleResultList
) {}