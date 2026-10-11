package com.EXbank.loan_eligibility_engine.domain.rules;

import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.RuleValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component

public class CreditScoreRule implements EligibilityRule {

    private static final String NAME = "CreditScoreRule";

    @Override
    public RuleResult evaluate(EvaluationContext context, RuleValues values) {

        if (context.creditScore() < values.minCreditScore()) {
            return new RuleResult(NAME, false, "ERR_LOW_CREDIT_SCORE",
                    "Applicant credit score (" + context.creditScore()
                            + ") is below minimum required score of " + values.minCreditScore());
        }

        return new RuleResult(NAME, true, "CREDIT_SCORE_OK",
                "Applicant meets the minimum credit score requirement.");
    }
}
