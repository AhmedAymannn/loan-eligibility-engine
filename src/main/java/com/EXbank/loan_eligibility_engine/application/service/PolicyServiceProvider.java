package com.EXbank.loan_eligibility_engine.application.service;

import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;

public interface PolicyServiceProvider {

    public RuleValues getRuleValues(String productCode);

}
