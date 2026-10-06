package com.EXbank.loan_eligibility_engine.application.service;

import com.EXbank.loan_eligibility_engine.api.dto.request.EligibilityEvaluationRequestDTO;
import com.EXbank.loan_eligibility_engine.api.dto.response.EligibilityEvaluationResponseDTO;

public interface LoanEligibilityService {

    EligibilityEvaluationResponseDTO evaluateEligibility(EligibilityEvaluationRequestDTO requestDTO);
}