package com.EXbank.loan_eligibility_engine.application.service;

import com.EXbank.loan_eligibility_engine.api.dto.request.EligibilityEvaluationRequestDTO;
import com.EXbank.loan_eligibility_engine.api.dto.response.EligibilityEvaluationResponseDTO;
import com.EXbank.loan_eligibility_engine.domain.engine.mapper.EvaluationContextMapper;
import com.EXbank.loan_eligibility_engine.domain.engine.DecisionEngine;
import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
public class LoanEligibilityServiceImpl implements LoanEligibilityService{

    private final EvaluationContextMapper mapper;
    private final DecisionEngine decisionEngine;
    private final PolicyServiceImp policyServiceImp ;

    public LoanEligibilityServiceImpl(EvaluationContextMapper mapper, DecisionEngine decisionEngine, PolicyServiceImp policyServiceImp) {
        this.mapper = mapper;
        this.decisionEngine = decisionEngine;
        this.policyServiceImp = policyServiceImp;
    }


    @Override
    public EligibilityEvaluationResponseDTO evaluateEligibility(EligibilityEvaluationRequestDTO requestDTO) {
        // 1. Map DTO to Immutable EvaluationContext (normalizes currency & generates UUID)
        EvaluationContext context = mapper.toDomainContext(requestDTO);
        //  call the service and pass the values to evaluate
        RuleValues values = policyServiceImp.getRuleValues(requestDTO.productCode());
        EvaluationResult evaluationResult = decisionEngine.evaluateAll(context , values);
        List <RuleResult> ruleResultList = evaluationResult.auditLog();

        return new EligibilityEvaluationResponseDTO(
        context.applicationId(),
        evaluationResult.verdict(),
        Instant.now(),
        ruleResultList
);
    }



}
