package com.EXbank.loan_eligibility_engine.domain.rules;

import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;
import org.springframework.stereotype.Component;

@Component
public class MaxActiveLoansRule implements EligibilityRule {

    private static final String NAME = "MaxActiveLoansRule";

    @Override
    public RuleResult evaluate(EvaluationContext context, RuleValues values) {

        if (context.activeLoans() > values.maxActiveLoans()) {
            return new RuleResult(NAME, false, "MAX_LOANS_EXCEEDED",
                    String.format("Applicant currently has %d active loans. Maximum allowed is %d.",
                            context.activeLoans(), values.maxActiveLoans()));
        }

        return new RuleResult(NAME, true, "ACTIVE_LOANS_OK",
                "Active loan count is within allowable threshold.");
    }
}
