package com.EXbank.loan_eligibility_engine.domain.rules;

import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class CreditScoreRule implements EligibilityRule {

    private final int minCreditScore ;

    public CreditScoreRule(@Value("${eligibility.rules.min-credit-score}") int minCreditScore) {
        this.minCreditScore = minCreditScore;
    }
    @Override
    public RuleResult evaluate(EvaluationContext context) {
        if (context.getCreditScore() < minCreditScore) {
            return new RuleResult(
                    "CreditScoreRule",
                    false,
                    "ERR_LOW_CREDIT_SCORE",
                    "Applicant credit score (" + context.getCreditScore() + ") is below minimum required score of " + minCreditScore
            );
        }

        return new RuleResult(
                "CreditScoreRule",
                true,
                "CREDIT_SCORE_OK",
                "Applicant meets the minimum credit score requirement."
        );
    }

}

