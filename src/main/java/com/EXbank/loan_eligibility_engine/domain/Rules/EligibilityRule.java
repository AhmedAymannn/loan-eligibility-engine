package com.EXbank.loan_eligibility_engine.domain.rules;
import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.RuleValues;

public interface EligibilityRule {

    public RuleResult evaluate (EvaluationContext context , RuleValues values ) ;
}
