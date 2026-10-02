package com.EXbank.loan_eligibility_engine.domain.Rules;

import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;



public interface EligibilityRule {

    public RuleResult evaluate (EvaluationContext context) ;
}
