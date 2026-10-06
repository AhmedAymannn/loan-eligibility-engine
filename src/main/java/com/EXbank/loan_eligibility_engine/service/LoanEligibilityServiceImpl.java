package com.EXbank.loan_eligibility_engine.service;


import com.EXbank.loan_eligibility_engine.api.dto.request.EligibilityEvaluationRequestDTO;
import com.EXbank.loan_eligibility_engine.api.dto.response.EligibilityEvaluationResponseDTO;

import com.EXbank.loan_eligibility_engine.domain.model.DecisionEngine;
import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.EvaluationResult;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class LoanEligibilityServiceImpl {

    private final EvaluationContextMapper mapper;
    private final DecisionEngine decisionEngine;

    public LoanEligibilityServiceImpl(EvaluationContextMapper mapper, DecisionEngine decisionEngine) {
        this.mapper = mapper;
        this.decisionEngine = decisionEngine;
    }


    public EligibilityEvaluationResponseDTO evaluateEligibility(EligibilityEvaluationRequestDTO requestDTO) {
        // 1. Map DTO to Immutable EvaluationContext (normalizes currency & generates UUID)
        EvaluationContext context = mapper.toDomainContext(requestDTO);
        EvaluationResult evaluationResult = decisionEngine.evaluateAll(context);
        List <RuleResult> ruleResultList = evaluationResult.auditLog();

        return new EligibilityEvaluationResponseDTO(
        context.applicationId(),
        evaluationResult.verdict(),
        Instant.now(),
        ruleResultList
);

    }

}
