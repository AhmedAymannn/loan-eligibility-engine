package com.EXbank.loan_eligibility_engine.domain.rules;

import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MaxActiveLoansRule implements EligibilityRule {

    private final int maxActiveLoans;

    public MaxActiveLoansRule(@Value("${eligibility.loan.max-active-loans}") int maxActiveLoans) {
        this.maxActiveLoans = maxActiveLoans;
    }

    @Override
    public RuleResult evaluate(EvaluationContext context) {

        if (context.activeLoans() > maxActiveLoans) {
            return new RuleResult(
                    "MaxActiveLoansRule",
                    false,
                    "MAX_LOANS_EXCEEDED",
                    String.format("Applicant currently has %d active loans. Maximum allowed is %d.",
                            context.activeLoans(), maxActiveLoans)
            );

        }
    return new RuleResult(
            "MaxActiveLoansRule",
            true,
            "ACTIVE_LOANS_OK",
            "Active loan count is within allowable threshold."
    );
    }

}
