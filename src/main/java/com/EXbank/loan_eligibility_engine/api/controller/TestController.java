package com.EXbank.loan_eligibility_engine.api.controller;

import com.EXbank.loan_eligibility_engine.application.service.PolicyServiceImp;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final PolicyServiceImp policyServiceImp;

    public TestController(PolicyServiceImp policyServiceImp) {
        this.policyServiceImp = policyServiceImp;
    }
    @GetMapping("/api/v1/eligibility/test/values/{productCode}")
    public ResponseEntity<RuleValues> getRuleValues (@PathVariable String productCode){

        RuleValues ruleValues = policyServiceImp.getRuleValues(productCode);
        return ResponseEntity.ok(ruleValues);
    }
}
