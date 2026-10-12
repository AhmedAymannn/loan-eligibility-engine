package com.EXbank.loan_eligibility_engine.domain.rules;
import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;

public interface EligibilityRule {

    public RuleResult evaluate (EvaluationContext context , RuleValues values ) ;
}
