package com.EXbank.loan_eligibility_engine.api.controller;

import com.EXbank.loan_eligibility_engine.api.dto.request.EligibilityEvaluationRequestDTO;
import com.EXbank.loan_eligibility_engine.api.dto.response.EligibilityEvaluationResponseDTO;
import com.EXbank.loan_eligibility_engine.application.service.LoanEligibilityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/eligibility")
public class EligibilityEvaluationController {

    private final LoanEligibilityService eligibilityService;

    public EligibilityEvaluationController(LoanEligibilityService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }
    @PostMapping
    ResponseEntity<EligibilityEvaluationResponseDTO> evaluate (@Valid @RequestBody EligibilityEvaluationRequestDTO requestDTO){

        EligibilityEvaluationResponseDTO response = eligibilityService.evaluateEligibility(requestDTO);
        return ResponseEntity.ok(response);


    }

}
